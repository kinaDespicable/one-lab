package one.lab.firstpractice.model.entity;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean admin;
    private Boolean author;
    private List<News> writtenNews = new ArrayList<>(); // One-to-many

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }


}
