package fr.sysf.sample.model

import java.time.{LocalDate, LocalDateTime}

import io.swagger.annotations.ApiModelProperty

import scala.annotation.meta.beanGetter
import scala.beans.BeanProperty

/**
  * @author florent
  *         02/05/2016
  */
case class Customer(

                     //@JsonIgnore
                     ////@(NotBlank @beanGetter)
                     ////@(Email @beanGetter)

                     @(ApiModelProperty@beanGetter)(position = 0, example = "HTF_23232")
                     @BeanProperty id: String,

                     //@Length(min = 3, max = 70)
                     @BeanProperty
                     firstName: String,

                     //@Length(min = 3, max = 70)
                     @BeanProperty
                     lastName: String,

                     //@Email
                     @BeanProperty
                     email: String,

                     //@NotNull
                     @BeanProperty
                     birthDate: LocalDate,

                     @BeanProperty
                     creatingDate: LocalDateTime

                   )


