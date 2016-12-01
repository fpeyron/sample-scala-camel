package fr.sysf.sample.route

import java.time.{LocalDate, LocalDateTime}

import fr.sysf.sample.model.Customer
import fr.sysf.sample.service.CustomerApiServiceConstant
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.springframework.context.annotation.ImportResource
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
@Component
@ImportResource(Array("classpath:spring/api-cxf-sample.xml"))
class SampleRoute extends ScalaRouteBuilder(new DefaultCamelContext()) {

  private object external extends SampleRouteConstant

  private object internal extends CustomerApiServiceConstant

  "cxfrs:bean:v1ApiService?bindingStyle=SimpleConsumer" ==> {
    id("cxf-v1ApiService")

    recipients(simple("direct:${header.operationName}"))
  }

  external.customers_post ==> {
    id(internal.customers_post_id)

    // Validation input
    -->("bean-validator://x")

    transform(_.in)
  }

  external.customers_get ==> {
    id(internal.customers_get_id)

    transform(exchange => {
      Customer(
        id = exchange.getIn.getHeader("id", classOf[String]),
        firstName = "myfirstname",
        lastName = "mylastname",
        email = "myfirstname.mylastname@yopmail.com",
        birthDate = LocalDate.now(),
        creatingDate = LocalDateTime.now()
      )
    })
    //--> ("mock:test")
  }

  external.customers_put ==> {
    id(internal.customers_put_id)

    -->("mock:test")
  }

  external.customers_patch ==> {
    id(internal.customers_patch_id)

    -->("mock:test")
  }

  external.customers_del ==> {
    id(internal.customers_del_id)

    -->("mock:test")
  }

  /*
    choice {

      when(exchange =>  CustomerApiService.getValue.equals(exchange.getIn.getHeader(CxfConstants.OPERATION_NAME, classOf[String]))) {
        setProperty("id", _.getIn.getHeader("id", classOf[String]))
        removeHeaders("*")
        setHeader(RedisConstants.KEY, _.getProperty("id", classOf[String]))
        setHeader(RedisConstants.COMMAND, "EXISTS")
        -->("spring-redis://host:0000?redisTemplate=#redisTemplate")
        choice {
          when(! _.in[Boolean]) {
            setHeader(Exchange.HTTP_RESPONSE_CODE, 404)
            transform(exchange => String.format("element not found : %s", exchange.getIn.getHeader(RedisConstants.KEY, classOf[String])))
          }
          otherwise {
            setHeader(RedisConstants.COMMAND, "GET")
            -->("spring-redis://host:0000?redisTemplate=#redisTemplate")
            transform(_.in[Customer].lastName)
          }
        }
      }

      when(exchange =>  CustomerApiService.putValue.equals(exchange.getIn.getHeader(CxfConstants.OPERATION_NAME, classOf[String]))) {
        setProperty("id", _.getIn.getHeader("id", classOf[String]))
        removeHeaders("*")
        setHeader(RedisConstants.KEY, _.getProperty("id", classOf[String]))
          .transform(exchange => {
            Customer(id = exchange.getIn.getHeader("id", classOf[String]), lastName = exchange.in[String] )
          })
        setHeader(RedisConstants.VALUE, _.in[Customer])
        setHeader(RedisConstants.COMMAND, "SETEX")
        setHeader(RedisConstants.TIMEOUT, 10)
        -->("spring-redis://host:0000?redisTemplate=#redisTemplate")
        setHeader(Exchange.HTTP_RESPONSE_CODE, 204)
        transform("")
      }

      when(exchange =>  CustomerApiService.delValue.equals(exchange.getIn.getHeader(CxfConstants.OPERATION_NAME, classOf[String]))) {
        setProperty("id", _.getIn.getHeader("id", classOf[String]))
        removeHeaders("*")
        setHeader(RedisConstants.KEYS, _.getProperty("id", classOf[String]))
        setHeader(RedisConstants.COMMAND, "DEL")

        -->("spring-redis://host:0000?redisTemplate=#redisTemplate")
      }


      otherwise {
        transform( exchange => "OK" )
      }

    }
    */

}


trait SampleRouteConstant {

  final val customers_post = "direct:" + internal.customers_post_id
  final val customers_get = "direct:" + internal.customers_get_id
  final val customers_put = "direct:" + internal.customers_put_id
  final val customers_patch = "direct:" + internal.customers_patch_id
  final val customers_del = "direct:" + internal.customers_del_id
  private object internal extends CustomerApiServiceConstant

}