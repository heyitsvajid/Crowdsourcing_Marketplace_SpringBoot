import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancer.Controller.ProjectController;
import com.freelancer.Entity.Project;
import com.freelancer.Entity.User;
import com.freelancer.Main;
import com.freelancer.Repository.ProjectRepository;
import com.freelancer.Service.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=Main.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

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
    private ProjectRepository projectRepository;
    @MockBean
    private ObjectService objectService;



    @Test
    public void testGetAllProject() throws Exception {
        mvc.perform(get("/allProjects").contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void testGetProjectById() throws Exception {

        Project project = new Project();
        project.setId(Long.parseLong(5+""));
        mvc.perform(post("/getProjectById").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)).
                        accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400));

    }

}