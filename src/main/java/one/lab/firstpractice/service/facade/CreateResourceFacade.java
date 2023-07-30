package one.lab.firstpractice.service.facade;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.model.dto.request.CreateUserRequest;
import one.lab.firstpractice.model.dto.request.NewsRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateResourceFacade {

    private final UserService userService;
    private final NewsService newsService;

    public CreatedResponse createUser(Authentication authentication, CreateUserRequest createUserRequest){
       return userService.createUser(authentication, createUserRequest);
    }

    public CreatedResponse createNews(NewsRequest newsRequest, Authentication authentication){
        return newsService.create(newsRequest, authentication);
    }

}
