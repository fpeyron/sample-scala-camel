package fr.sysf.sample.config

import com.google.common.collect.Sets
import org.springframework.context.annotation.{Bean, Configuration}
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/**
  * Created by florent on 08/05/2017.
  */
@Configuration
@EnableSwagger2
class SwaggerConfig {

  @Bean def adminApiDoc: Docket = new Docket(DocumentationType.SWAGGER_2)
    .groupName("admin")
    .apiInfo(apiInfo)
    .select()
    .build()
    .protocols(Sets.newHashSet("http", "https"))

  @Bean def apiInfo: ApiInfo = new ApiInfoBuilder()
    .title("Sysf Services")
    .description("Spring REST Webservice")
    .license("Apache License Version 2.0")
    .version("2.0")
    .build


}

