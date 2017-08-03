package ewing;

import ewing.application.Result;
import ewing.common.JsonConverter;
import ewing.entity.User;
import ewing.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApp.class)
public class AdminControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    private MockMvc mvc;

    private User createUser() {
        User user = new User();
        user.setName("123");
        user.setPassword("123");
        return userService.addUser(user);
    }

    private void cleanData(User user) {
        userService.deleteUser(user.getUserId());
    }

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void saveTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/user/addUser")
                .param("name", "小凡")
                .param("password", "碧瑶")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").isNotEmpty())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Result object = JsonConverter.toObject(json, Result.class);
        Map user = (Map) object.getData();
        userService.deleteUser((String) user.get("userId"));
    }

    @Test
    public void getTest() throws Exception {
        User user = createUser();

        mvc.perform(MockMvcRequestBuilders
                .get("/user/getUser")
                .param("id", user.getUserId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name")
                        .value("123"));

        cleanData(user);
    }

    @Test
    public void deleteTest() throws Exception {
        User user = createUser();

        mvc.perform(MockMvcRequestBuilders
                .get("/user/deleteUser")
                .param("id", user.getUserId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        cleanData(user);
    }

}