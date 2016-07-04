package fr.sysf.sample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{ComponentScan, Configuration}

object Application extends App {
  SpringApplication.run(classOf[BootConfig])
}

@Configuration
@EnableAutoConfiguration
@ComponentScan
private class BootConfig