package fr.sysf.sample.routes

import java.net.HttpURLConnection

import fr.sysf.sample.controllers.{CsCustomerController, Roles}
import org.apache.camel.component.cxf.common.message.CxfConstants
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.apache.camel.{CamelContext, Exchange}
import org.primeframework.jwt.domain.JWT
import org.primeframework.jwt.rsa.RSAVerifier
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
@Component
class CxfRouteBuilder(@Autowired val camelContext: CamelContext = null) extends ScalaRouteBuilder(camelContext) {

  private object internal extends CxfRouteInternalConstant

  @Value("${oauth.pubKey}")
  private val oauthPubKey: String = null

  private def verifier = RSAVerifier.newVerifier(oauthPubKey)

  handle[org.primeframework.jwt.domain.JWTException] {}
    .handled(true)
    .transform(e => e.getProperty(""""{ "errorCode": 403, "status":"FORBIDDEN", "message":"invalid token JWT" }"""))
    .removeHeaders("*")
    .setHeader(Exchange.HTTP_RESPONSE_CODE, HttpURLConnection.HTTP_FORBIDDEN)

  handle[org.primeframework.jwt.domain.JWTExpiredException] {}
    .handled(true)
    .transform(e => e.getProperty(""""{ "errorCode": 403, "status":"FORBIDDEN", "message":"token JWT expired" }"""))
    .removeHeaders("*")
    .setHeader(Exchange.HTTP_RESPONSE_CODE, HttpURLConnection.HTTP_FORBIDDEN)

  handle[HandledException] {}
    .handled(true)
    .transform { e =>
      val exception = e.getProperty(Exchange.EXCEPTION_CAUGHT, classOf[HandledException])
      s""""{ "errorCode": ${exception.httpResponseCode}", "message":"${exception.message}" }"""
    }
    .removeHeaders("*")
    .setHeader(Exchange.HTTP_RESPONSE_CODE, HttpURLConnection.HTTP_INTERNAL_ERROR)

  internal.cxfCall ==> {
    id(internal.cxfCall_id)

    // Check Security
    process { e =>

      // check existing method
      if (!controllerMethods.contains(e.getIn.getHeader(CxfConstants.OPERATION_NAME, classOf[String]))) {
        throw new ApiMethodNotFoundException(s"method not found : ${e.getIn.getHeader("CamelHttpMethod")} ${e.getIn.getHeader("CamelHttpUri")}")
      }

      // check info token
      val jwtOption = Option(e.getIn.getHeader("Authorization", classOf[String])).map(_.replaceFirst("Bearer ", "")).map(JWT.getDecoder.decode(_, verifier))

      val authCustomerIdOption = jwtOption.flatMap(j => Option(j.claims.get("http://danon/customerId")).map(_.toString))
      val authUserIdOption = jwtOption.map(_.uniqueId)
      val authRole = jwtOption.flatMap(j => Option(j.claims.get("http://danon/roles")).map(_.toString)).getOrElse("PUBLIC")


      // check role with method
      val methodDetail = controller(e.getIn.getHeader(CxfConstants.OPERATION_NAME, classOf[String]))
      if (! Option(methodDetail.getAnnotation(classOf[Roles]).value()).getOrElse(Array("PUBLIC")).contains(authRole)) {
        throw new ApiMethodNotAuthorizedException(s"uri not allowed with ${authRole.toLowerCase()} access : ${e.getIn.getHeader("CamelHttpMethod")} ${e.getIn.getHeader("CamelHttpUri")}")
      }

      // check customer id / email
      val customerId = Option(e.getIn.getHeader("customerId", classOf[String]))
      if (customerId.isDefined && customerId.get != "me"
        && authCustomerIdOption.isDefined && customerId != authCustomerIdOption) {
        throw new ApiMethodNotAuthorizedException(s"uri not allowed for you : ${e.getIn.getHeader("CamelHttpMethod")} ${e.getIn.getHeader("CamelHttpUri")}")
      }

      // Enrich with Auth info
      authCustomerIdOption.foreach{c =>
        e.getIn.setHeader("customerId", c)
        e.setProperty("customerId", c)
      }
      authUserIdOption.foreach(e.setProperty("userId", _))
    }


    // Redirect to target route
    recipients(e => s"direct:${e.getIn.getHeader(CxfConstants.OPERATION_NAME)}")
  }

  private val controllerMethods = classOf[CsCustomerController].getMethods.map(e => e.getName -> e.getDeclaredAnnotations).toMap
  private val controller = classOf[CsCustomerController].getMethods.map(e => e.getName -> e).toMap
}

trait CxfRouteInternalConstant {

  val cxfCall_id = "cxfServer"
  val cxfCall = "cxfrs:bean:apiService?bindingStyle=SimpleConsumer"
}

object AuthenticationType extends Enumeration {
  type AuthenticationType = Value
  val CUSTOMER, PARTNER, ANONYMOUS = Value
}

class HandledException(val message: String, val httpResponseCode: Int = 500) extends RuntimeException

class ApiMethodNotFoundException(message: String, httpResponseCode: Int = 404) extends HandledException(message, httpResponseCode)

class ApiMethodNotAuthorizedException(message: String, httpResponseCode: Int = 403) extends HandledException(message, httpResponseCode)