package fr.sysf.sample

import org.junit.runner.RunWith
import org.junit.{Before, Ignore, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext

/**
  * Created by florent on 21/02/2016.
  */
@Ignore
@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(classes = Array(classOf[BootConfig]))
@WebAppConfiguration
class ApplicationCamelTest {

  @Autowired
  private val webApplicationContext: WebApplicationContext = null


  private var mockMvc: MockMvc = null

  @Before
  def setup {
    mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  def getHello {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sample/elements", "100")).andExpect(MockMvcResultMatchers.status().isNotFound)
    //mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sample/elements","100")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    //mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sample/elements/100")).andExpect(MockMvcResultMatchers.status().isFound)
  }
}
