package one.lab.firstpractice.controller;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.annotation.LoggableRequest;
import one.lab.firstpractice.model.dto.request.NewsRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.service.facade.CreateResourceFacade;
import one.lab.firstpractice.service.facade.ReadResourceFacade;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final ReadResourceFacade readResourceFacade;
    private final CreateResourceFacade createResourceFacade;

    @LoggableRequest
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    public ResponseEntity<CreatedResponse> create(@RequestBody NewsRequest newsRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(createResourceFacade.createNews(newsRequest, authentication), headers, HttpStatus.CREATED);
    }

    @LoggableRequest
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NewsResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(readResourceFacade.getNewsById(id), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/by-author")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<NewsResponse>> getAllByAuthor(@RequestParam(name = "author") String author) {
        return new ResponseEntity<>(readResourceFacade.getNewsByAuthorUsername(author), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/title")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NewsResponse> getByTitle(@RequestParam("title") String title) {
        return new ResponseEntity<>(readResourceFacade.getNewsByTitle(title), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<NewsResponse>> getAll() {
        return new ResponseEntity<>(readResourceFacade.getAllNews(), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/by-topic-name")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<NewsResponse>> getAllByTopic(@RequestParam(name = "topic") String topic) {
        return new ResponseEntity<>(readResourceFacade.getNewsByTopicName(topic), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/with-most-likes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NewsResponse> getNewsWithMostLikes() {
       return new ResponseEntity<>(readResourceFacade.getNewsByMostLikes(), HttpStatus.OK);
    }

}
