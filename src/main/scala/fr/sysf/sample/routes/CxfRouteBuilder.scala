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
class CxfRouteBuilder(@Autowired val camelContext: CamelContext = null) extends ScalaRouteBuilder(camelContext) {

  "cxfrs:bean:apiService?bindingStyle=SimpleConsumer" ==> {
    id("cxf-apiService")

    recipients(simple("direct:${header.operationName}"))
  }
}
