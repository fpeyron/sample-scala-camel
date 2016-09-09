package fr.sysf.sample.model

import java.time.LocalDateTime

import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.{Length, NotEmpty}

import scala.annotation.meta.beanGetter
import scala.beans.BeanProperty

/**
  * @author Florent Peyron
  *         10/09/2016
  */
case class Hotel(

                  //@(JsonIgnore@beanGetter)
                  @(ApiModelProperty@beanGetter)(example = "57c0af8a412b3c049f38e6b4")
                  @BeanProperty id: String,

                  @(ApiModelProperty@beanGetter)(example = "Blum")
                  @(Length@beanGetter)(min = 3, max = 70) @(NotEmpty@beanGetter)
                  @BeanProperty
                  name: String,

                  @(ApiModelProperty@beanGetter)(example = "10 rue des plantes")
                  @BeanProperty
                  address: String,

                  @(ApiModelProperty@beanGetter)(example = "75010")
                  @(Length@beanGetter)(min = 5, max = 5) @(NotEmpty@beanGetter)
                  @BeanProperty
                  zip: String,

                  @BeanProperty
                  creatingDate: java.time.Instant = null,

                  @BeanProperty
                  updatingDate: java.time.Instant = null

                )
