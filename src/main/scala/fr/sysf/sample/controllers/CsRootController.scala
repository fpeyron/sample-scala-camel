package fr.sysf.sample.controllers

import java.net.HttpURLConnection
import javax.ws.rs._
import javax.ws.rs.core.Response

import fr.sysf.sample.models.{CsCreateCustomer, CsCustomer}
import io.swagger.annotations._
import org.apache.cxf.jaxrs.ext.PATCH
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller


/**
  * @author florent peyron
  *         02/05/2016
  */
@Controller
class CsRootController extends CsCustomerController