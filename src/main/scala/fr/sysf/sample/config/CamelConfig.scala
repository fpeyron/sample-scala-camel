package fr.sysf.sample.config

import org.apache.camel.CamelContext
import org.apache.camel.component.properties.PropertiesComponent
import org.apache.camel.processor.interceptor.Tracer
import org.apache.camel.spring.SpringCamelContext
import org.apache.camel.spring.boot.CamelContextConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * @author florent peyron
  *         01/05/2016
  */
@Configuration
class CamelConfig {

  @Value("${logging.trace.enabled:false}")
  private val tracingEnabled: Boolean = false


  @Bean
  def contextConfig = new CamelContextConfiguration {

    override def beforeApplicationStart(camelContext: CamelContext) = {

      // set context name
      val springCamelContext = camelContext.asInstanceOf[SpringCamelContext]
      springCamelContext.setName("camelBusContext")

      // override property by system properties
      val propertiesComponent = camelContext.getComponent("properties", classOf[PropertiesComponent])
      propertiesComponent.setSystemPropertiesMode(2)

      // see if trace logging is turned on
      camelContext.setTracing(tracingEnabled)

      // set StreamCaching to up
      camelContext.setStreamCaching(true)

    }

    override def afterApplicationStart(camelContext: CamelContext): Unit = {}
  }

  @Bean
  def camelTracer = {
    val tracer = new Tracer
    tracer.setTraceExceptions(true)
    tracer.setTraceInterceptors(true)
    tracer.setLogName("com.sysf.bus.trace")
    tracer
  }

}