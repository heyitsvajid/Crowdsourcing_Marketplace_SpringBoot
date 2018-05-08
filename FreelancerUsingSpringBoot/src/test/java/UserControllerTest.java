import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancer.Controller.ProjectController;
import com.freelancer.Controller.UserController;
import com.freelancer.Entity.User;
import com.freelancer.Main;
import com.freelancer.Repository.UserRepository;
import com.freelancer.Service.AttachmentsService;
import com.freelancer.Service.BidService;
import com.freelancer.Service.ProjectService;
import com.freelancer.Service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=Main.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private AttachmentsService attachmentsService;
    @MockBean
    private BidService bidService;
    @MockBean
    private ProjectService projectService;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private User user;

    @Test
    public void getAllUsers() throws Exception {
        mvc.perform(get("/all").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getProfile() throws Exception {
        mvc.perform(post("/getprofile").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(415));

    }
    @Test
    public void testLogin() throws Exception {

        User user = new User();
        user.setPassword("123456");
        user.setEmail("vajid9@gmail.com");
        mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)).
                        accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));

    }


}