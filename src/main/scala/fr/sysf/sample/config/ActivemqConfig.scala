package fr.sysf.sample.config

import javax.jms.ConnectionFactory

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.activemq.camel.component.{ActiveMQComponent, ActiveMQConfiguration}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.support.converter.{MappingJackson2MessageConverter, MessageType}

/**
  * Created by florent on 05/05/2017.
  */
@EnableJms
@Configuration
class ActivemqConfig {

  @Autowired
  val jmsConnectionFactory: ConnectionFactory = null


  @Bean // Serialize message content to json using TextMessage
  def jacksonJmsMessageConverter: MappingJackson2MessageConverter = {
    val converter = new MappingJackson2MessageConverter
    converter.setTargetType(MessageType.TEXT)
    converter.setObjectMapper(jacksonJmsMapper)
    converter.setTypeIdPropertyName("_type")
    converter
  }

  @Bean def jacksonJmsMapper: ObjectMapper = {
    val mapper = new ObjectMapper()
    mapper.registerModule(new JavaTimeModule())
    mapper.setSerializationInclusion(Include.NON_EMPTY)
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    mapper.findAndRegisterModules()
    mapper
  }

  @Bean
  def activemq: ActiveMQComponent = {
    val activeMQComponent = new ActiveMQConfiguration()
    activeMQComponent.setCacheLevelName("CACHE_NONE")
    activeMQComponent.setConcurrentConsumers(1)
    activeMQComponent.setConnectionFactory(jmsConnectionFactory) //connectionFactory" ref="pooledConnectionFactory"/>
    activeMQComponent.setMaxConcurrentConsumers(4)
    //transacted" value="true"/>
    //<property name="transactionManager" ref="jmsTransactionManagerProcess"/>
    activeMQComponent.setUseMessageIDAsCorrelationID(true)

    new ActiveMQComponent(activeMQComponent)
  }
}
