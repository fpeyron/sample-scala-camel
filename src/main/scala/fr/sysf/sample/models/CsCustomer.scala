package fr.sysf.sample.models


import java.time.{LocalDate, LocalDateTime}

import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.{Email, Length, NotEmpty}

import scala.annotation.meta.beanGetter
import scala.beans.BeanProperty

/**
  * @author florent peyron
  *         02/05/2016
  */
case class CsCustomer(

                       //@(JsonIgnore@beanGetter)
                       @(ApiModelProperty@beanGetter)(example = "HTF_23232")
                       @BeanProperty id: Option[String],

                       @(ApiModelProperty@beanGetter)(example = "Anna")
                       @(Length@beanGetter)(min = 3, max = 70) @(NotEmpty@beanGetter)
                       @BeanProperty firstname: Option[String],

                       @(ApiModelProperty@beanGetter)(example = "Blum")
                       @(Length@beanGetter)(min = 3, max = 70) @(NotEmpty@beanGetter)
                       @BeanProperty lastname: Option[String],

                       @(ApiModelProperty@beanGetter)(example = "anna.blum@yopmail.com")
                       @(Email@beanGetter)
                       @BeanProperty email: Option[String],

                       @(ApiModelProperty@beanGetter)(example = "1990-04-04", dataType = "date")
                       @BeanProperty birth_date: Option[LocalDate] = null,

                       @(ApiModelProperty@beanGetter)(example = "2018-01-01T10:12:12.121Z", dataType = "datetime")
                       @BeanProperty creating_date: Option[LocalDateTime] = null

                     )


case class CsCreateCustomer(

                             @(ApiModelProperty@beanGetter)(example = "Anna")
                             @(Length@beanGetter)(min = 3, max = 70)
                             @BeanProperty firstname: String,

                             @(ApiModelProperty@beanGetter)(example = "Blum")
                             @(Length@beanGetter)(min = 3, max = 70) @(NotEmpty@beanGetter)
                             @BeanProperty lastname: String,

                             @(ApiModelProperty@beanGetter)(example = "anna.blum@yopmail.com")
                             @(Email@beanGetter)@(NotEmpty@beanGetter)
                             @BeanProperty email: String,

                             @(ApiModelProperty@beanGetter)(example = "secret123")
                             @(Length@beanGetter)(min = 4, max = 12) @(NotEmpty@beanGetter)
                             @BeanProperty password: String,

                             @(ApiModelProperty@beanGetter)(example = "CA")
                             @(Length@beanGetter)(min = 2, max = 2) @(NotEmpty@beanGetter)
                             @BeanProperty countryCode: String,

                             @(ApiModelProperty@beanGetter)(example = "fr")
                             @(Length@beanGetter)(min = 2, max = 2) @(NotEmpty@beanGetter)
                             @BeanProperty languageCode: String,

                             @(ApiModelProperty@beanGetter)(example = "1990-01-14", dataType = "date")
                             @BeanProperty birth_date: Option[LocalDate] = None
                           )

