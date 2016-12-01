package fr.sysf.sample.service

import java.net.HttpURLConnection
import javax.ws.rs._
import javax.ws.rs.core.Response

import fr.sysf.sample.model.FusionCustomer
import io.swagger.annotations._
import org.springframework.http.MediaType


/**
  * @author florent peyron
  *         02/05/2016
  */
@Path("/fusion")
@Consumes(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Produces(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Api(value = "V1 - Hotels", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class FusionApiService {

  @POST
  @Path("/customers")
  @ApiOperation(value = "CustomerHotel")
  @ApiResponses(value = Array(
    new ApiResponse(code = HttpURLConnection.HTTP_CREATED, response = classOf[FusionCustomer], message = "Success create"),
    new ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, response = classOf[Error], message = "Bad request"),
    new ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, response = classOf[Error], message = "Internal error")
  ))
  def fusion_customers_post(
                   body: FusionCustomer
                 ): Response = null

  @GET
  @Path("/customers/{id}")
  @ApiOperation(value = "HotelGet")
  @ApiResponses(value = Array(
    new ApiResponse(code = HttpURLConnection.HTTP_OK, response = classOf[FusionCustomer], message = "Found record"),
    new ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, response = classOf[Error], message = "Hotel not Found"),
    new ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, response = classOf[Error], message = "Internal error")
  ))
  def fusion_customers_get(
                  @ApiParam(defaultValue = "57c0af8a412b3c049f38e6b4") @PathParam("id") id: String
                ): Response = null

}

trait FusionApiServiceConstant {

  final val customers_post_id = "fusion_customers_post"
  final val customers_get_id = "fusion_customers_get"
}
