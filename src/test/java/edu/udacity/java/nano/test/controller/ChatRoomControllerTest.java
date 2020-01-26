package edu.udacity.java.nano.test.controller;

import edu.udacity.java.nano.WebSocketChatApplication;
import edu.udacity.java.nano.chat.ChatRoomController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebSocketChatApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatRoomControllerTest {

    @Resource
    private ChatRoomController chatRoomController;

    private MockMvc mockMvc;

    @InjectMocks
    private ChatRoomController mockChatRoomController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mockChatRoomController).build();
    }

    @Test
    public void testCanary() {
        Assert.assertTrue(true);
    }

    @Test
    public void testLogin() {
        Assert.assertEquals("redirect:/chat/testuser", chatRoomController.login("testuser"));
    }

    @Test
    public void testChat() {
        Assert.assertEquals("chat", chatRoomController.chat("testuser"));
    }

    @Test
    public void testLoginSuccessRedirect() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/login?username=testuser1")
        )
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/chat/testuser1"));

    }

    @Test
    public void testViewNameChatAddNewUser() throws Exception {

      mockMvc.perform(
                MockMvcRequestBuilders.get("/chat/testuser1").accept(MediaType.ALL)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("chat"))
                ;
    }

}
