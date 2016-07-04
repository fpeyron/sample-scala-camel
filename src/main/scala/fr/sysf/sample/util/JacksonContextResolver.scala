package fr.sysf.sample.util

import javax.ws.rs.ext.{ContextResolver, Provider}

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.jsr310.JSR310Module

/**
  * @author florent
  *         29/05/2016
  */
@Provider
class JacksonContextResolver extends ContextResolver[ObjectMapper] {


  private final val mapper = new ObjectMapper()
  mapper.registerModule(new JSR310Module())
  mapper.setSerializationInclusion(Include.NON_EMPTY)
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

  override def getContext(aClass: Class[_]): ObjectMapper = mapper
}
