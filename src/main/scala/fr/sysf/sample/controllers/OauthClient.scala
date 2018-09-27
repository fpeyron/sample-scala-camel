package fr.sysf.sample.controllers

import javax.ws.rs._
import javax.ws.rs.core.Response

import fr.sysf.sample.models.OauthCreateUser
import org.apache.cxf.jaxrs.ext.PATCH
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller

/**
  * Created by Florent Peyron on 14/09/2016.
  */
@Controller
@Consumes(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
@Produces(Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
class OauthClient {

  @POST
  @Path("/users")
  def oauth_createUser(user: OauthCreateUser): Response = null


  @PATCH
  @Path("/users/{userId}")
  def oauth_updateUser(
                        @PathParam("userId") userId: String,
                        user: OauthCreateUser
                      ): Response = null

  @GET
  @Path("/users/{userId}")
  def oauth_getUser_id(
                        @PathParam("userId") userId: String
                      ): Response = null
}

trait OauthClientConstant {
  final val createUser_id = "oauth_createUser"
  final val updateUser_id = "oauth_updateUser"
  final val getUser_id = "oauth_getUser_id"
}

