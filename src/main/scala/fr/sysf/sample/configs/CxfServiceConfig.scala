package fr.sysf.sample.configs

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import fr.sysf.sample.controllers.CustomerController
import org.apache.cxf.Bus
import org.apache.cxf.endpoint.Server
import org.apache.cxf.interceptor.{LoggingInInterceptor, LoggingOutInterceptor}
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean
import org.apache.cxf.jaxrs.swagger.Swagger2Feature
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.{Bean, Configuration, ImportResource}

/**
  * @author florent peyron
  *         01/05/2016
  */
@Configuration
@ImportResource(Array("classpath:META-INF/cxf/cxf.xml"))
class CxfServiceConfig {

  @Autowired private val bus: Bus = null

  @Autowired private val customerController: CustomerController = null

  @Bean def servletRegistrationBean = new ServletRegistrationBean(new CXFServlet, "/api/*")

  @Bean def apiService: JAXRSServerFactoryBean = {
    val endpoint = new JAXRSServerFactoryBean
    endpoint.setAddress("/")
    endpoint.setBus(bus)
    endpoint.setServiceBeans(java.util.Arrays.asList(customerController))
    endpoint.setInInterceptors(java.util.Arrays.asList(new LoggingInInterceptor))
    endpoint.setOutInterceptors(java.util.Arrays.asList(new LoggingOutInterceptor))
    endpoint.setProvider(jacksonJsonProvider)
    endpoint.setFeatures(java.util.Arrays.asList(swagger2Feature))

    endpoint
  }

  @Bean def jacksonJsonProvider: JacksonJsonProvider = {
    val mapper = new ObjectMapper()
    mapper.registerModule(new JavaTimeModule())
    mapper.setSerializationInclusion(Include.NON_EMPTY)
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    mapper.findAndRegisterModules()
    val jacksonJsonProvider = new JacksonJsonProvider()
    jacksonJsonProvider.setMapper(mapper)

    jacksonJsonProvider
  }

  @Bean def apiDocsService: Server = {
    val endpoint = new JAXRSServerFactoryBean
    endpoint.setAddress("/docs")
    endpoint.setServiceBeans(java.util.Arrays.asList(customerController))
    endpoint.setFeatures(java.util.Arrays.asList(swagger2Feature))
    endpoint.create
  }

  @Bean def swagger2Feature: Swagger2Feature = {
    val swagger2Feature = new Swagger2Feature
    swagger2Feature.setVersion("1.0")
    swagger2Feature.setTitle("API BUS SAMPLE")
    swagger2Feature.setBasePath("/api")
    //swagger2Feature.setRunAsFilter(false)
    swagger2Feature
  }
}