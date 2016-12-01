package fr.sysf.sample.client

import javax.ws.rs._

import fr.sysf.sample.model.FusionCustomer
import org.springframework.http.MediaType


/**
  * @author florent peyron
  *         02/05/2016
  */
@Path("/customers")
@Consumes(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Produces(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
class FusionCustomerApiClient {

  @POST
  @Path("")
  def fusionCustomerPut(
                       customer: FusionCustomer
                      ): FusionCustomer = null

}

trait FusionCustomerApiClientConstant {

  final val fusion_customer_put_id = "fusionCustomerPut"
}
