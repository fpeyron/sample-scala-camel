package fr.sysf.sample.util

import javax.ws.rs.ext.{ContextResolver, Provider}

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.stereotype.Service

/**
  * @author florent peyron
  *         29/05/2016
  */
@Provider
@Service
class JacksonContextResolver extends ContextResolver[ObjectMapper] {


  private val mapper = new ObjectMapper()
  mapper.registerModule(new JavaTimeModule())
  mapper.setSerializationInclusion(Include.NON_EMPTY)
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  mapper.findAndRegisterModules()

  override def getContext(aClass: Class[_]): ObjectMapper = mapper
}
