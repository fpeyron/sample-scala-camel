package fr.sysf.sample.controllers

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing
import org.springframework.stereotype.Controller


/**
  * @author florent peyron
  *         02/05/2016
  */
@CrossOriginResourceSharing(allowAllOrigins = true, allowCredentials = true, maxAge = 600)
@Controller
class CsRootController extends CsCustomerController