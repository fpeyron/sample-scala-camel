package fr.sysf.sample.routes

import fr.sysf.sample.controllers.OauthClientConstant
import org.apache.camel.CamelContext
import org.apache.camel.component.cxf.common.message.CxfConstants
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
@Component
class OauthRouteBuilder(@Autowired val camelContext: CamelContext = null) extends ScalaRouteBuilder(camelContext) {

  private object external extends OauthRouteConstant

  private object internal extends OauthClientConstant with OauthRouteInternalConstant

  external.createUser ==> {
    id(internal.createUser_id)

    setHeader(CxfConstants.OPERATION_NAME, internal.createUser_id)
    -->(internal.cxfCall)
  }

  external.getUser ==> {
    id(internal.getUser_id)


    transform(e => Array(e.getProperty("userId")))
    setHeader(CxfConstants.OPERATION_NAME, internal.getUser_id)
    -->(internal.cxfCall)
  }


  external.updateUser ==> {
    id(internal.updateUser_id)


    transform(e => Array(e.getProperty("userId"), e.in))
    setHeader(CxfConstants.OPERATION_NAME, internal.updateUser_id)
    -->(internal.cxfCall)
  }


  internal.cxfCall ==> {
    routeId(internal.cxfCall_id)
    process(_.getIn.removeHeaders("*", CxfConstants.OPERATION_NAME))
    process(e =>
      e.in)
    -->("cxfrs:bean:cxfOauthClient?httpClientAPI=false")
    process(_.getIn.removeHeaders("*"))
  }
}


trait OauthRouteConstant {

  val createUser = s"direct:${internal.createUser_id}"
  val updateUser = s"direct:${internal.updateUser_id}"
  val getUser = s"direct:${internal.getUser_id}"

  private object internal extends OauthClientConstant with OauthRouteInternalConstant

}

trait OauthRouteInternalConstant {

  val cxfCall_id = "oauth_cxfClient"
  val cxfCall = s"direct:$cxfCall_id"
}
