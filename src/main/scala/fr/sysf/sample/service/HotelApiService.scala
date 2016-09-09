package fr.sysf.sample.service

import java.net.HttpURLConnection
import javax.ws.rs._
import javax.ws.rs.core.Response

import fr.sysf.sample.model.Hotel
import io.swagger.annotations._
import org.springframework.http.MediaType


/**
  * @author florent peyron
  *         02/05/2016
  */
@Path("/hotels")
@Consumes(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Produces(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Api(value = "V1 - Hotels", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class HotelApiService {

  @POST
  @Path("/")
  @ApiOperation(value = "CustomerHotel")
  @ApiResponses(value = Array(
    new ApiResponse(code = HttpURLConnection.HTTP_CREATED, response = classOf[Hotel], message = "Success create"),
    new ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, response = classOf[Error], message = "Bad request"),
    new ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, response = classOf[Error], message = "Internal error")
  ))
  def hotels_post(
                   body: Hotel
                 ): Response = null

  @GET
  @Path("/{id}")
  @ApiOperation(value = "HotelGet")
  @ApiResponses(value = Array(
    new ApiResponse(code = HttpURLConnection.HTTP_OK, response = classOf[Hotel], message = "Found record"),
    new ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, response = classOf[Error], message = "Hotel not Found"),
    new ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, response = classOf[Error], message = "Internal error")
  ))
  def hotels_get(
                  @ApiParam(defaultValue = "57c0af8a412b3c049f38e6b4") @PathParam("id") id: String
                ): Response = null

}

trait HotelApiServiceConstant {

  final val hotels_post_id = "hotels_post"
  final val hotels_get_id = "hotels_get"
}
