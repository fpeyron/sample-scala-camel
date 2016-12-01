package fr.sysf.sample.routebuilder

import com.netflix.discovery.EurekaClient
import com.sun.jndi.toolkit.url.Uri
import fr.sysf.sample.client.FusionCustomerApiClientConstant
import fr.sysf.sample.service.FusionApiServiceConstant
import org.apache.camel.component.cxf.common.message.CxfConstants
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.apache.camel.{Exchange, Processor}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.context.annotation.ImportResource
import org.springframework.stereotype.Component

/**
  * @author florent peyron
  *         02/05/2016
  */
//@Component
//@ImportResource(Array("classpath:spring/api-cxf-client.xml"))
class HotelRoutebuilder extends ScalaRouteBuilder(new DefaultCamelContext()) {

  private object external extends HotelRoutebuilderConstant

  private object internal extends FusionApiServiceConstant

  private object client extends FusionCustomerApiClientConstant

  @Autowired
  private val discoveryClient: DiscoveryClient = null

  @Autowired
  private val eurekaClient: EurekaClient = null

  @Autowired
  private val loadBalancer: LoadBalancerClient = null

  external.customers_post ==> {
    id(internal.customers_post_id)

    // Validation input
    -->("bean-validator://x")

    setHeader(CxfConstants.OPERATION_NAME, "fusionCustomerPut")
    process(new Processor {
      override def process(e: Exchange): Unit = {

        val instance = eurekaClient.getNextServerFromEureka("FUSION-AUDIT", false)
        val url = instance.getHomePageUrl() + ""
        //val instances = discoveryClient.getInstances("FUSION-AUDIT")
        //val url = if (instances.isEmpty) new Uri("http://127.0.0.1") else instances.get(0).getUri
        e.setProperty(Exchange.DESTINATION_OVERRIDE_URL, url)
        e.getIn.setHeader(Exchange.DESTINATION_OVERRIDE_URL, url)
        //e.in[String]
      }
    })
    //attempt {
    -->("cxfrs:bean:hotelClient?httpClientAPI=false&throwExceptionOnFailure=false")
    //removeHeaders("*")
    /*
    }.handle(classOf[Exception]) {
      process(new Processor {
        override def process(exchange: Exchange): Unit = {
          exchange.in
        }
      })
      */
    //case e: Exception => { m: Message => m.setBody("general processing error")
    //   } //>=> failWith(e)
    //  }
    //      .handle(classOf[ConnectException]) {
    //      transform(e => {
    //        e
    //      })
    //      removeHeader(Exchange.EXCEPTION_CAUGHT)
    //      removeHeader(Exchange.EXCEPTION_HANDLED)
    //    }

  }

  external.cxfclient ==> {
    id(external.cxfclient_id)
    //errorHandler(defaultErrorHandler.redeliveryDelay(3000).maximumRedeliveries(300))
    //handle(classOf[Exception]).useOriginalMessage.redeliveryDelay(3000).maximumRedeliveries(300).retryAttemptedLogLevel(LoggingLevel.WARN).logRetryStackTrace(false)
    transform(e => {

      /*
      val rule = new AvailabilityFilteringRule();
      val list = new DiscoveryEnabledNIWSServerList("SAMPLE-SCALA-MONGO-REST")
      val filter = new ZoneAffinityServerListFilter[DiscoveryEnabledServer]()
      val lb = LoadBalancerBuilder.newBuilder()
      .withDynamicServerList(list)
      .withRule(rule)
      .withServerListFilter(filter)
      .buildDynamicServerListLoadBalancer()
      val server = lb.chooseServer()
      val url = "http://"+server.getHostPort
      */
      val instances = discoveryClient.getInstances("FUSION-AUDIT")

      val url = if (instances.isEmpty) new Uri("http://127.0.0.1") else instances.get(0).getUri
      e.setProperty(Exchange.DESTINATION_OVERRIDE_URL, url)
      e.getIn.setHeader(Exchange.DESTINATION_OVERRIDE_URL, url)
      //exchange.setProperty(Exchange.DESTINATION_OVERRIDE_URL+"2", loadBalancer.choose("SAMPLE-SCALA-MONGO-REST").getUri)
      e.in[String]
    })
    //    attempt {

    -->("cxfrs:bean:hotelClient?httpClientAPI=false&throwExceptionOnFailure=false")
    transform(new Processor {
      override def process(exchange: Exchange): Unit = {
        exchange
      }
    })
    //    transform(e => e.in[Hotel].copy(url = e.getProperty(Exchange.DESTINATION_OVERRIDE_URL, classOf[String])))
    removeHeaders("*")
    //  }
    // handle(classOf[Exception]) {
    //   case e: Exception => { m: Message => m.setBody("general processing error")
    //   } //>=> failWith(e)
    //}

    //      .handle(classOf[ConnectException]) {
    //      transform(e => {
    //        e
    //      })
    //      removeHeader(Exchange.EXCEPTION_CAUGHT)
    //      removeHeader(Exchange.EXCEPTION_HANDLED)
    //    }
  }
}


trait HotelRoutebuilderConstant {

  final val customers_post = "direct:" + internal.customers_post_id
  final val customers_get = "direct:" + internal.customers_get_id

  final val cxfclient_id = "cxfclient"
  final val cxfclient = "direct:" + cxfclient_id

  private object internal extends FusionApiServiceConstant

}
