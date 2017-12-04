package fr.sysf.sample.routes

import java.time.{LocalDate, LocalDateTime}

import fr.sysf.sample.controllers.CustomerApiServiceConstant
import fr.sysf.sample.models.Customer
import org.apache.camel.CamelContext
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
@Component
class SampleRoute(@Autowired val camelContext: CamelContext = null) extends ScalaRouteBuilder(camelContext) {

  private object external extends SampleRouteConstant

  private object internal extends CustomerApiServiceConstant

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
    //inOnly
    id("active_test")
    transform { e =>
      e.in
    }
    removeHeaders("*")
  }

  external.customers_patch ==> {
    id(internal.customers_patch_id)

    -->("mock:test")
  }

  external.customers_del ==> {
    id(internal.customers_del_id)

    -->("mock:test")
  }

}


trait SampleRouteConstant {

  val customers_post = "direct:" + internal.customers_post_id
  val customers_get = "direct:" + internal.customers_get_id
  val customers_put = "direct:" + internal.customers_put_id
  val customers_patch = "direct:" + internal.customers_patch_id
  val customers_del = "direct:" + internal.customers_del_id

  private object internal extends SampleRouteInternalConstant

}

trait SampleRouteInternalConstant extends CustomerApiServiceConstant {

}
