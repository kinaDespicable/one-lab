package one.lab.firstpractice.repository;

import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "SELECT * FROM news WHERE title = ?", nativeQuery = true)
    Optional<News> findByTitle(String title);

    Page<News> findAllByTopic(Pageable pageable, Topic topic);

    Page<News> findAllByAuthor(Pageable pageable, User user);

    @Query(value = "SELECT * FROM news ORDER BY likes DESC LIMIT 1", nativeQuery = true)
    Optional<News> findNewsWithMostLikes();

    Integer countNewsByTopic(Topic topic);

}
