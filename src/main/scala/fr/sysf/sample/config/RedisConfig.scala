package fr.sysf.sample.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate

/**
  * @author florent
  *         02/05/2016
  */
@Configuration
class RedisConfig {

  @Autowired
  private val stringRedisTemplate: StringRedisTemplate = null

  def redisTemplate: StringRedisTemplate = {
    stringRedisTemplate
  }

  def redisConnectionFactory = {
    stringRedisTemplate.getConnectionFactory
  }

}
