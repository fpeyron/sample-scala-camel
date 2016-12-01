package fr.sysf.sample.client

import javax.ws.rs._

import fr.sysf.sample.model.FusionCustomer
import org.springframework.http.MediaType


/**
  * @author florent peyron
  *         02/05/2016
  */
@Path("/hotels")
@Consumes(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Produces(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
class HotelApiClient {

  @GET
  @Path("/{id}")
  def partnerHotelsGet(
                        @PathParam("id") id: String
                      ): FusionCustomer = null

}

trait HotelApiClientConstant {

  final val partner_hotels_get_id = "partnerHotelsGet"
}
