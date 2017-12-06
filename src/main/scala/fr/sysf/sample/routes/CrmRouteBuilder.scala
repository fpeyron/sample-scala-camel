package fr.sysf.sample.routes

import fr.sysf.sample.controllers.CrmClientConstant
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
class CrmRouteBuilder(@Autowired val camelContext: CamelContext = null) extends ScalaRouteBuilder(camelContext) {

  private object external extends CrmRouteConstant

  private object internal extends CrmClientConstant with CrmRouteInternalConstant

  external.createCustomer ==> {
    id(internal.createCustomer_id)

    setHeader(CxfConstants.OPERATION_NAME, internal.createCustomer_id)
    -->(internal.cxfCall)
  }

  external.getCustomer ==> {
    id(internal.getCustomer_id)


    transform(e => Array( e.getProperty("customerId")))
    setHeader(CxfConstants.OPERATION_NAME, internal.getCustomer_id)
    -->(internal.cxfCall)
  }


  internal.cxfCall ==> {
    routeId(internal.cxfCall_id)
    process(_.getIn.removeHeaders("*", CxfConstants.OPERATION_NAME))
    process(e =>
    e.in)
    -->("cxfrs:bean:cxfCrmClient?httpClientAPI=false")
    process(e =>
      e.in)
    process(_.getIn.removeHeaders("*"))
  }
}


trait CrmRouteConstant {

  val createCustomer = s"direct:${internal.createCustomer_id}"
  val getCustomer = s"direct:${internal.getCustomer_id}"

  private object internal extends CrmClientConstant with CrmRouteInternalConstant

}

trait CrmRouteInternalConstant {

  val cxfCall_id = "crm_cxfClient"
  val cxfCall = s"direct:$cxfCall_id"
}
