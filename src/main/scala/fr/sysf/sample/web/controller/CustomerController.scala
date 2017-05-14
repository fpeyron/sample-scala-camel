package fr.sysf.sample.web.controller

import java.net.HttpURLConnection
import javax.ws.rs._
import javax.ws.rs.core.Response

import fr.sysf.sample.model.Customer
import io.swagger.annotations._
import org.apache.cxf.jaxrs.ext.PATCH
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller


/**
  * @author florent peyron
  *         02/05/2016
  */
@Controller
@Path("/customers")
@Consumes(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Produces(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Api(value = "api Customer", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class CustomerController {

  @POST
  @Path("/")
  @ApiOperation(value = "CustomerCreate")
  @ApiResponses(value = Array(
    new ApiResponse(code = HttpURLConnection.HTTP_CREATED, response = classOf[Customer], message = "Success create"),
    new ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, response = classOf[Error], message = "Bad request"),
    new ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, response = classOf[Error], message = "Internal error")
  ))
  def customers_post(
                      body: Customer
                    ): Response = null

  @GET
  @Path("/{id}")
  @ApiOperation(value = "CustomerGet")
  @ApiResponses(value = Array(
    new ApiResponse(code = HttpURLConnection.HTTP_OK, response = classOf[Customer], message = "Found record"),
    new ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, response = classOf[Error], message = "Account not Found"),
    new ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, response = classOf[Error], message = "Internal error")
  ))
  def customers_get(
                     @ApiParam(defaultValue = "0000-AAAA") @PathParam("id") id: String
                   ): Response = null

  @PUT
  @Path("/{id}")
  @ApiOperation(value = "CustomerPut")
  @ApiResponses(value = Array(
    new ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, response = classOf[Customer], message = "Account updated"),
    new ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, response = classOf[Error], message = "Account not Found"),
    new ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, response = classOf[Error], message = "Internal error")
  ))
  def customers_put(
                     @ApiParam(defaultValue = "0") @PathParam("id") id: String,
                     body: Customer
                   ): Response = null

  @PATCH
  @Path("/{id}")
  @ApiOperation(value = "patch from Hash", code = 203)
  def customers_patch(
                       @ApiParam(defaultValue = "0") @PathParam("id") id: String,
                       body: String
                     ): Response = null

  @DELETE
  @Path("/{id}")
  @ApiOperation(value = "set from Hash", code = 203)
  def customers_del(
                     @ApiParam(defaultValue = "0") @PathParam("id") id: String
                   ): Response = null

}

trait CustomerApiServiceConstant {

  final val customers_post_id = "customers_post"
  final val customers_get_id = "customers_get"
  final val customers_put_id = "customers_put"
  final val customers_patch_id = "customers_patch"
  final val customers_del_id = "customers_del"
}
