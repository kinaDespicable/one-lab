package one.lab.firstpractice.storage;

import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class NewsStorage {

    public static Long NEWS_SEQUENCE = 1L;

    private List<News> storage = new ArrayList<>();

    public void addNewsToStorage(News news) {
        storage.add(news);
    }

    public Optional<News> getById(Long id) {
        return storage.stream()
                .filter(news -> news.getId().equals(id))
                .findFirst();
    }

    public List<News> getAll() {
        return storage;
    }

    public Optional<News> getByTitle(String title) {
        return storage.stream()
                .filter(news -> news.getTitle().equals(title))
                .findFirst();
    }

    public Optional<News> getMostLikedNews() {
        return storage.stream()
                .max(Comparator.comparingLong(News::getLikes));
    }

    public List<News> getNewsByTopic(Topic topic) {
        return storage.stream()
                .filter(news -> news.getTopic().equals(topic))
                .toList();
    }

    public List<News> getNewsByAuthor(User author) {
        return storage.stream()
                .filter(news -> news.getAuthor().equals(author))
                .toList();
    }

    public Optional<News> getByEntity(News entity) {
        return storage.stream().filter(news -> news.equals(entity))
                .findFirst();
    }

}
