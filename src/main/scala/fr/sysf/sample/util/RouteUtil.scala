package fr.sysf.sample.util

/**
  * Created by florent on 26/05/2017.
  */
object RouteUtil {

  def amqSync(queueName: String):String = {
    s"activemq:$queueName?messageConverter=#jacksonJmsMessageConverter&disableReplyTo=false&asyncConsumer=false"
  }

  def amqAsync(queueName: String):String = {
    s"activemq:$queueName?messageConverter=#jacksonJmsMessageConverter&disableReplyTo=true&asyncConsumer=true"
  }
}
