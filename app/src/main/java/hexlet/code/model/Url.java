package hexlet.code.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }

    public Url(Long id, String name, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
}
