package fr.sysf.sample

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
  * Created by florent on 21/02/2016.
  */
@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Array(classOf[Application]))
class ApplicationCamelTest {

  @Autowired
  private val restTemplate: TestRestTemplate = null


  @Test
  def getHello() {

    // getAll before insert
    val response = restTemplate.getForEntity("/api/customers/0000-AAAA", classOf[String])
    Assertions.assertThat(response.getStatusCodeValue).isEqualTo(HttpStatus.OK.value())
    //Assertions.assertThat(response.getBody.getContent).isEmpty

  }
}
