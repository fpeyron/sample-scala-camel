package fr.sysf.sample.routebuilder

import fr.sysf.sample.client.HotelApiClientConstant
import fr.sysf.sample.service.HotelApiServiceConstant
import org.apache.camel.component.cxf.common.message.CxfConstants
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.springframework.context.annotation.ImportResource
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
@Component
@ImportResource(Array("classpath:spring/api-cxf-client.xml"))
class HotelRoutebuilder extends ScalaRouteBuilder(new DefaultCamelContext()) {

  private object external extends HotelRoutebuilderConstant

  private object internal extends HotelApiServiceConstant

  private object client extends HotelApiClientConstant


  external.hotels_post ==> {
    id(internal.hotels_post_id)

    // Validation input
    -->("bean-validator://x")

    transform(_.in)
  }

  external.hotels_get ==> {
    id(internal.hotels_get_id)

    /*
    transform(exchange => {
      Hotel(
        id = exchange.getIn.getHeader("id", classOf[String]),
        name = "Paris Gare du Nord",
        address = "10 rue du Nord",
        zip = "75010",
        creatingDate = LocalDateTime.now(),
        updatingDate = LocalDateTime.now()
      )
    })
    */
    setHeader(CxfConstants.OPERATION_NAME, client.partner_hotels_get_id)
    removeHeaders("*", CxfConstants.OPERATION_NAME)
    -->("cxfrs:bean:hotelClient?httpClientAPI=false")
    removeHeader("*")
  }

}


trait HotelRoutebuilderConstant {

  final val hotels_post = "direct:" + internal.hotels_post_id
  final val hotels_get = "direct:" + internal.hotels_get_id

  private object internal extends HotelApiServiceConstant

}
