package one.lab.firstpractice.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import one.lab.firstpractice.model.dto.request.CreateUserRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.service.UserService;
import one.lab.firstpractice.service.facade.CreateResourceFacade;
import one.lab.firstpractice.service.facade.ReadResourceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateResourceFacade createResourceFacade;

    @Mock
    private ReadResourceFacade readResourceFacade;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("johndoe");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");
        request.setPasswordConfirmation("password123");


        UserResponse userResponse = new UserResponse(1L, "johndoe", "John", "Doe");
        CreatedResponse createdResponse = new CreatedResponse(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                LocalDateTime.now(),
                userResponse);
        when(createResourceFacade.createUser(any(Authentication.class), any(CreateUserRequest.class))).thenReturn(createdResponse);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAll() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        String sortField = "username";
        Page<UserResponse> userPage = createMockUserPage();

        when(readResourceFacade.getAllUsers(any(), any(), any())).thenReturn(userPage);

        mockMvc.perform(get("/user/fetch-all")
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize))
                        .param("sort", sortField)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].username").value("johndoe"));

        verify(readResourceFacade, times(1)).getAllUsers(any(), any(), any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAdmins() throws Exception {
        Page<UserResponse> adminsPage = createMockAdminsPage();

        when(readResourceFacade.getAllAdmins()).thenReturn(adminsPage);

        mockMvc.perform(get("/user/fetch-admins")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].username").value("admin1"));

        verify(readResourceFacade, times(1)).getAllAdmins();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAuthors_Successful() throws Exception {
        Page<UserResponse> authorsPage = createMockAuthorsPage();

        when(readResourceFacade.getAllAuthors()).thenReturn(authorsPage);

        mockMvc.perform(get("/user/fetch-authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].username").value("author1"));

        verify(readResourceFacade, times(1)).getAllAuthors();
    }

    @Test
    void testGetCurrentUser_Successful() throws Exception {
        UserResponse currentUserResponse = createMockCurrentUserResponse(); // Create a mock UserResponse for the current user

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("johndoe");

        when(readResourceFacade.getCurrentUser("johndoe")).thenReturn(currentUserResponse);

        mockMvc.perform(get("/user/profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("johndoe"));
        verify(readResourceFacade, times(1)).getCurrentUser("johndoe");
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private Page<UserResponse> createMockUserPage() {
        List<UserResponse> userList = new ArrayList<>();
        userList.add(new UserResponse(1L, "johndoe", "John", "Doe"));

        return new PageImpl<>(userList);
    }

    private Page<UserResponse> createMockAdminsPage() {
        UserResponse admin1 = new UserResponse(1L, "admin1", "John", "Doe");
        UserResponse admin2 = new UserResponse(2L, "admin2", "John2", "Doe2");

        return new PageImpl<>(List.of(admin1, admin2));
    }

    private Page<UserResponse> createMockAuthorsPage() {
        UserResponse author1 = new UserResponse(1L, "author1", "John", "Doe");
        UserResponse author2 = new UserResponse(2L, "author2", "John", "Doe");

        return new PageImpl<>(List.of(author1, author2));
    }

    private UserResponse createMockCurrentUserResponse() {
        return new UserResponse(1L, "johndoe", "John", "Doe");
    }
}

