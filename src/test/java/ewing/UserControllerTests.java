package ewing;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartApp.class)
public class UserControllerTests {

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

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void saveTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/user/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"小凡\",\"password\":\"碧瑶\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }

    @Test
    public void getTest() throws Exception {
        User user = createUser();

        mvc.perform(MockMvcRequestBuilders
                .get("/user/getUser")
                .param("userId", String.valueOf(user.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name")
                        .value("123"));
    }

    @Test
    public void deleteTest() throws Exception {
        User user = createUser();

        mvc.perform(MockMvcRequestBuilders
                .get("/user/deleteUser")
                .param("userId", String.valueOf(user.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}