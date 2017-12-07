package fr.sysf.sample.configs

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import fr.sysf.sample.controllers.OauthClient
import org.apache.cxf.Bus
import org.apache.cxf.interceptor.{LoggingInInterceptor, LoggingOutInterceptor}
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.context.annotation.{Bean, Configuration, ImportResource}

import scala.collection.JavaConverters._

/**
  * @author florent peyron
  *         01/05/2016
  */
@Configuration
@ImportResource(Array("classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-conduit-context.xml"))
class OauthClientConfig {

  @Value("${oauth.url}")
  private val oauthUrl: String = null

  @Value("${oauth.authorization}")
  private val oauthAuthorization: String = null

  @Autowired private val bus: Bus = null

  @Autowired private val oauthClient: OauthClient = null


  @Bean def cxfOauthClient: JAXRSClientFactoryBean = {
    val endpoint = new JAXRSClientFactoryBean
    endpoint.setAddress(oauthUrl)
    endpoint.setHeaders(Map("Authorization" -> oauthAuthorization).asJava)
    endpoint.setBus(bus)
    endpoint.setServiceClass(oauthClient.getClass)
    endpoint.setInInterceptors(java.util.Arrays.asList(new LoggingInInterceptor))
    endpoint.setOutInterceptors(java.util.Arrays.asList(new LoggingOutInterceptor))
    endpoint.setProvider(cxfOauthjacksonJsonProvider)

    endpoint
  }

  @Bean def cxfOauthjacksonJsonProvider: JacksonJsonProvider = {
    val mapper = new ObjectMapper()
    mapper.registerModule(new JavaTimeModule())
    mapper.setSerializationInclusion(Include.NON_EMPTY)
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.findAndRegisterModules()
    val jacksonJsonProvider = new JacksonJsonProvider()
    jacksonJsonProvider.setMapper(mapper)

    jacksonJsonProvider
  }

}