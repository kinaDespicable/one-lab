package one.lab.firstpractice.service.facade;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReadResourceFacade {

    private final UserService userService;
    private final NewsService newsService;

    public Page<UserResponse> getAllUsers(Optional<Integer> pageOptional,
                                          Optional<Integer> sizeOptional,
                                          Optional<String> sortOptional) {
        return userService.fetchAll(pageOptional, sizeOptional, sortOptional);
    }

    public UserResponse getUserById(Long id) {
        return userService.fetchById(id);
    }

    public UserResponse getCurrentUser(String username) {
        final String normalizedString = username.trim();
        return userService.fetchCurrentUser(normalizedString);
    }

    public Page<UserResponse> getAllAdmins() {
        return userService.fetchAllAdmins();
    }

    public Page<UserResponse> getAllAuthors() {
        return userService.fetchAllAuthors();
    }


    public NewsResponse getNewsById(Long id) {
        return newsService.fetchById(id);
    }

    public Page<NewsResponse> getAllNews() {
        return newsService.fetchAll();
    }

    public NewsResponse getNewsByTitle(String title) {
        final String normalizedString = title.trim();
        return newsService.fetchByTitle(normalizedString);
    }

    public Page<NewsResponse> getNewsByTopicName(String topicName) {
        final String normalizedString = topicName.trim();
        return newsService.fetchByTopicName(normalizedString);
    }

    public Page<NewsResponse> getNewsByAuthorUsername(String username) {
        final String normalizedString = username.trim();
        return newsService.fetchByAuthorUsername(normalizedString);
    }

    public NewsResponse getNewsByMostLikes() {
        return newsService.fetchNewsByMostLikes();
    }

}
