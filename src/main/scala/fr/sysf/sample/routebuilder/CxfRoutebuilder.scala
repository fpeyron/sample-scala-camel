package fr.sysf.sample.routebuilder

import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.springframework.context.annotation.ImportResource
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
@Component
@ImportResource(Array("classpath:spring/api-cxf-server.xml"))
class CxfRoutebuilder extends ScalaRouteBuilder(new DefaultCamelContext()) {

  "cxfrs:bean:v1ApiService?bindingStyle=SimpleConsumer" ==> {
    id("cxf-v1ApiService")

    recipients(simple("direct:${header.operationName}"))
  }

}
