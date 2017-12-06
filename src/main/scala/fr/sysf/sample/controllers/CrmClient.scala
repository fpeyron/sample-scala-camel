package fr.sysf.sample.controllers

import javax.ws.rs._

import fr.sysf.sample.models.{CrmCreateCustomer, CrmCreateCustomerResponse, CrmCustomer}
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller

/**
  * Created by Florent Peyron on 14/09/2016.
  */
@Controller
@Consumes(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Produces(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
class CrmClient {

  @POST
  @Path("/customers")
  def crm_createCustomer(
                          customer: CrmCreateCustomer
                        ): CrmCreateCustomerResponse = null

  @GET
  @Path("/customers/{customerId}")
  def crm_getCustomer(
                       @PathParam("customerId") customerId: String
                     ): CrmCustomer = null
}

trait CrmClientConstant {
  final val createCustomer_id = "crm_createCustomer"
  final val getCustomer_id = "crm_getCustomer"
}

