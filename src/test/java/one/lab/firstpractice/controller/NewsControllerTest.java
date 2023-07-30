package one.lab.firstpractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.lab.firstpractice.model.dto.request.NewsRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.facade.CreateResourceFacade;
import one.lab.firstpractice.service.facade.ReadResourceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NewsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NewsService newsService;

    @Mock
    private CreateResourceFacade createResourceFacade;

    @Mock
    private ReadResourceFacade readResourceFacade;

    @InjectMocks
    private NewsController newsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newsController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void testCreate_SuccessfulCreation() throws Exception {
        NewsRequest newsRequest =
                new NewsRequest("Title", "Content", "Sport");

        NewsResponse newsResponse = NewsResponse.builder()
                .title("Title")
                .content("Content")
                .likes(0)
                .publishedAt(LocalDateTime.now())
                .author(new UserResponse(1L, "johnDoe", "John", "Doe"))
                .topic("Sport")
                .build();

        CreatedResponse createdResponse =
                new CreatedResponse(HttpStatus.CREATED, HttpStatus.CREATED.value(), LocalDateTime.now(), newsResponse);


        when(createResourceFacade.createNews(any(NewsRequest.class), any(Authentication.class))).thenReturn(createdResponse);

        mockMvc.perform(post("/news/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newsRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void testGetById() throws Exception {
        Long newsId = 1L;
        NewsResponse newsResponse = createMockNewsResponse();
        when(readResourceFacade.getNewsById(newsId)).thenReturn(newsResponse);

        mockMvc.perform(get("/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test News"))
                .andExpect(jsonPath("$.content").value("This is a test news item."));

        verify(readResourceFacade, times(1)).getNewsById(newsId);
    }

    @Test
    void testGetAllByAuthor() throws Exception {
        String authorUsername = "johndoe";
        Page<NewsResponse> newsResponsePage = createMockNewsResponsePage();
        when(readResourceFacade.getNewsByAuthorUsername(authorUsername)).thenReturn(newsResponsePage);

        mockMvc.perform(get("/news/by-author")
                        .param("author", authorUsername)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].title").value("Test News 1"))
                .andExpect(jsonPath("$.content[0].content").value("This is a test news item 1."))
                .andExpect(jsonPath("$.content[1].title").value("Test News 2"))
                .andExpect(jsonPath("$.content[1].content").value("This is a test news item 2."));

        verify(readResourceFacade, times(1)).getNewsByAuthorUsername(authorUsername);
    }

    @Test
    void testGetByTitle() throws Exception {
        String newsTitle = "Test News";
        NewsResponse newsResponse = createMockNewsResponse();
        when(readResourceFacade.getNewsByTitle(newsTitle)).thenReturn(newsResponse);

        mockMvc.perform(get("/news/title")
                        .param("title", newsTitle)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test News"))
                .andExpect(jsonPath("$.content").value("This is a test news item."));

        verify(readResourceFacade, times(1)).getNewsByTitle(newsTitle);
    }

    @Test
    void testGetAll() throws Exception {
        Page<NewsResponse> newsResponsePage = createMockNewsResponsePage();
        when(readResourceFacade.getAllNews()).thenReturn(newsResponsePage);

        mockMvc.perform(get("/news/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].title").value("Test News 1")) // Assuming NewsResponse has a "title" field
                .andExpect(jsonPath("$.content[0].content").value("This is a test news item 1.")) // Assuming NewsResponse has a "content" field
                .andExpect(jsonPath("$.content[1].title").value("Test News 2"))
                .andExpect(jsonPath("$.content[1].content").value("This is a test news item 2."));

        verify(readResourceFacade, times(1)).getAllNews();
    }


    @Test
    void testGetAllByTopic() throws Exception {
        String topicName = "Sports";
        Page<NewsResponse> newsResponsePage = createMockNewsResponsePage();
        when(readResourceFacade.getNewsByTopicName(topicName)).thenReturn(newsResponsePage);

        mockMvc.perform(get("/news/by-topic-name")
                        .param("topic", topicName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].title").value("Test News 1"))
                .andExpect(jsonPath("$.content[0].content").value("This is a test news item 1."))
                .andExpect(jsonPath("$.content[1].title").value("Test News 2"))
                .andExpect(jsonPath("$.content[1].content").value("This is a test news item 2."));

        verify(readResourceFacade, times(1)).getNewsByTopicName(topicName);
    }

    @Test
    void testGetNewsWithMostLikes() throws Exception {
        NewsResponse newsWithMostLikes = createMockNewsResponse();
        when(readResourceFacade.getNewsByMostLikes()).thenReturn(newsWithMostLikes);

        mockMvc.perform(get("/news/with-most-likes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test News")) // Assuming NewsResponse has a "title" field
                .andExpect(jsonPath("$.content").value("This is a test news item.")); // Assuming NewsResponse has a "content" field

        verify(readResourceFacade, times(1)).getNewsByMostLikes();
    }

    private NewsResponse createMockNewsResponse() {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTitle("Test News");
        newsResponse.setContent("This is a test news item.");

        return newsResponse;
    }

    private Page<NewsResponse> createMockNewsResponsePage() {
        NewsResponse newsResponse1 = new NewsResponse();
        newsResponse1.setTitle("Test News 1");
        newsResponse1.setContent("This is a test news item 1.");

        NewsResponse newsResponse2 = new NewsResponse();
        newsResponse2.setTitle("Test News 2");
        newsResponse2.setContent("This is a test news item 2.");

        return new PageImpl<>(List.of(newsResponse1, newsResponse2));
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}

