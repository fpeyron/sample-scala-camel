package fr.sysf.sample.model


import java.time.{LocalDate, LocalDateTime}

import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.{Email, Length, NotEmpty}

import scala.annotation.meta.beanGetter
import scala.beans.BeanProperty

/**
  * @author florent peyron
  *         02/05/2016
  */
case class Customer(

                     //@(JsonIgnore@beanGetter)
                     @(ApiModelProperty@beanGetter)(example = "HTF_23232")
                     @BeanProperty id: String,

                     @(ApiModelProperty@beanGetter)(example = "Anna")
                     @(Length@beanGetter)(min = 3, max = 70) @(NotEmpty@beanGetter)
                     @BeanProperty
                     firstName: String,

                     @(ApiModelProperty@beanGetter)(example = "Blum")
                     @(Length@beanGetter)(min = 3, max = 70) @(NotEmpty@beanGetter)
                     @BeanProperty
                     lastName: String,

                     @(ApiModelProperty@beanGetter)(example = "anna.blum@yopmail.com")
                     @(Email@beanGetter)
                     @BeanProperty
                     email: String,

                     @BeanProperty
                     birthDate: LocalDate = null,

                     @BeanProperty
                     creatingDate: LocalDateTime = null

                   )


