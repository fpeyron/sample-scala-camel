package fr.sysf.sample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.{ComponentScan, Configuration}

object Application extends App {
  SpringApplication.run(classOf[ApplicationConfig])
}

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableEurekaClient
private class ApplicationConfig