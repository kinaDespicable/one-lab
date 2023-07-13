package one.lab.firstpractice.repository;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.exception.ResourceNotFoundException;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.storage.NewsStorage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsRepository {

    private final NewsStorage newsStorage;

    public News save(News entity) {
        newsStorage.addNewsToStorage(entity);
        return newsStorage.getByEntity(entity)
                .orElseThrow(() -> new ResourceNotFoundException("Save error."));
    }

    public Optional<News> findById(Long id) {
        return newsStorage.getById(id);
    }

    public List<News> findAll() {
        return newsStorage.getAll();
    }

    public Optional<News> findByTitle(String title) {
        return newsStorage.getByTitle(title);
    }

    public Optional<News> findNewsByMostLikes() {
        return newsStorage.getMostLikedNews();
    }

    public List<News> findNewsByTopic(Topic topic) {
        return newsStorage.getNewsByTopic(topic);
    }

    public List<News> findNewsByAuthor(User author){
        return newsStorage.getNewsByAuthor(author);
    }

}
