package fr.sysf.sample.config

import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation.{Bean, Configuration, ImportResource}

/**
  * @author florent
  *         01/05/2016
  */
@Configuration
@ImportResource(Array("classpath:META-INF/cxf/cxf.xml"))
class CxfConfig {

  @Bean
  def servletRegistrationBean = new ServletRegistrationBean(new CXFServlet, "/api/*")

}