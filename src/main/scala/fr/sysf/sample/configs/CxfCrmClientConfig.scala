package fr.sysf.sample.configs

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import fr.sysf.sample.controllers.CrmClient
import org.apache.cxf.Bus
import org.apache.cxf.interceptor.{LoggingInInterceptor, LoggingOutInterceptor}
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration, ImportResource}

import scala.collection.JavaConverters._

/**
  * @author florent peyron
  *         01/05/2016
  */
@Configuration
@ImportResource(Array("classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-conduit-context.xml"))
class CxfCrmClientConfig {

  @Autowired private val bus: Bus = null

  @Autowired private val crmClient: CrmClient = null

  @Bean def cxfCrmClient: JAXRSClientFactoryBean = {
    val endpoint = new JAXRSClientFactoryBean
    endpoint.setAddress("https://localhost:10443/api")
    //endpoint.setUsername("UATBETCCloudService")
    //endpoint.setPassword("GDRDEfg435fdgc$£%fgxcvb")
    endpoint.setHeaders(Map("Authorization" -> "Basic VXNlcm5hbWU6VUFUQkVUQ0Nsb3VkU2VydmljZTtQYXNzd29yZDpHRFJERWZnNDM1ZmRnYyTCoyVmZ3hjdmI=").asJava)
    endpoint.setBus(bus)
    endpoint.setServiceClass(crmClient.getClass)
    endpoint.setInInterceptors(java.util.Arrays.asList(new LoggingInInterceptor))
    endpoint.setOutInterceptors(java.util.Arrays.asList(new LoggingOutInterceptor))
    endpoint.setProvider(cxfCrmjacksonJsonProvider)

    endpoint
  }


  @Bean def cxfCrmjacksonJsonProvider: JacksonJsonProvider = {
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