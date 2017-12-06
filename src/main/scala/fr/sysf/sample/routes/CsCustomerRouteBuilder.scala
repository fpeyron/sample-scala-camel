package fr.sysf.sample.routes

import fr.sysf.sample.controllers.CustomerApiServiceConstant
import fr.sysf.sample.models._
import org.apache.camel.CamelContext
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
@Component
class SampleRoute(@Autowired val camelContext: CamelContext = null) extends ScalaRouteBuilder(camelContext) {

  private object external extends SampleRouteConstant

  private object crm extends CrmRouteConstant

  private object oauth extends OauthRouteConstant

  private object internal extends CustomerApiServiceConstant

  external.customers_post ==> {
    id(internal.customers_post_id)

    // Validation input
    -->("bean-validator://x")
    setProperty("csCreateCustomer", _.in)


    // create User in Crm
    transform { e =>
      val csCreateCustomer = e.in[CsCreateCustomer]
      CrmCreateCustomer(
        email = Some(csCreateCustomer.email.toLowerCase),
        firstName = Some(csCreateCustomer.firstname),
        surname = Some(csCreateCustomer.lastname),
        countryCode = Some(csCreateCustomer.countryCode.toUpperCase),
        languageCode = Some(csCreateCustomer.languageCode.toLowerCase),
        gender = Some("F"),
        portalAccounts = List(
          CrmPortalAccount(
            portalBrandCode = Some("WW_DANON"),
            status = Some("active"),
            registrationDate = Some("2017-12-05T22:24:30")
          )
        )
      )
    }
    -->(crm.createCustomer)
    setProperty("customerId", _.in[CrmCreateCustomerResponse].customerId)

    // create User in OAuth
    transform { e =>
      val customerId = e.getProperty("customerId", classOf[String])
      val csCreateCustomer = e.getProperty("csCreateCustomer", classOf[CsCreateCustomer])

      OauthCreateUser(
        email = Some(csCreateCustomer.email.toLowerCase),
        password = Some(csCreateCustomer.password),
        family_name = Some(csCreateCustomer.lastname),
        given_name = Some(csCreateCustomer.firstname),
        nickname = Some(csCreateCustomer.firstname),
        app_metadata = Map(
          "danon/customerId" -> customerId,
          "danon/countryCode" -> csCreateCustomer.countryCode.toUpperCase
        )
      )
    }
    -->(oauth.createUser)

    transform("")
  }

  external.customers_get ==> {
    id(internal.customers_get_id)

    setProperty("customerId", _.getIn.getHeader("id"))
    -->(crm.getCustomer)

    transform { e =>
      val crmCustomer = e.in[CrmCustomer]
      CsCustomer(
        id = crmCustomer.customerId,
        firstname = crmCustomer.firstName,
        lastname = crmCustomer.surname,
        email = crmCustomer.email,
        birth_date = crmCustomer.dateOfBirth
      )
    }
  }

  external.customers_put ==> {
    id(internal.customers_put_id)
    //inOnly
    id("active_test")
    transform { e =>
      e.in
    }
    removeHeaders("*")
  }

  external.customers_patch ==> {
    id(internal.customers_patch_id)

    -->("mock:test")
  }

  external.customers_del ==> {
    id(internal.customers_del_id)

    -->("mock:test")
  }

}


trait SampleRouteConstant {

  val customers_post = s"direct:${internal.customers_post_id}"
  val customers_get = s"direct:${internal.customers_get_id}"
  val customers_put = s"direct:${internal.customers_put_id}"
  val customers_patch = s"direct:${internal.customers_patch_id}"
  val customers_del = s"direct:${internal.customers_del_id}"

  private object internal extends SampleRouteInternalConstant

}

trait SampleRouteInternalConstant extends CustomerApiServiceConstant {

}
