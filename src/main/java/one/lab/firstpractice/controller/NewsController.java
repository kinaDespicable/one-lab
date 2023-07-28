package one.lab.firstpractice.controller;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.annotation.LoggableRequest;
import one.lab.firstpractice.model.dto.request.NewsRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @LoggableRequest
    @PostMapping("/create")
    public ResponseEntity<CreatedResponse> create(@RequestBody NewsRequest newsRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(newsService.create(newsRequest, authentication), HttpStatus.CREATED);
    }

    @LoggableRequest
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.fetchById(id), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/by-author")
    public ResponseEntity<Page<NewsResponse>> getAllByAuthor(@RequestParam(name = "author") String author) {
        return new ResponseEntity<>(newsService.fetchByAuthorUsername(author), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/title")
    public ResponseEntity<NewsResponse> getByTitle(@RequestParam("title") String title) {
        return new ResponseEntity<>(newsService.fetchByTitle(title), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/all")
    public ResponseEntity<Page<NewsResponse>> getAll() {
        return new ResponseEntity<>(newsService.fetchAll(), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/by-topic-name")
    public ResponseEntity<Page<NewsResponse>> getAllByTopic(@RequestParam(name = "topic") String topic) {
        return new ResponseEntity<>(newsService.fetchByTopicName(topic), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/with-most-likes")
    public ResponseEntity<NewsResponse> getNewsWithMostLikes() {
       return new ResponseEntity<>(newsService.fetchNewsByMostLikes(), HttpStatus.OK);
    }

}
