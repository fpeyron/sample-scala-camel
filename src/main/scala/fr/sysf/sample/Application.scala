package fr.sysf.sample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
class Application

object Application extends App {
  SpringApplication.run(classOf[Application], args: _*)
}