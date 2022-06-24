package pl.ms.wordlistgenerator.sentence;

import javax.persistence.*;

@Entity
public class UserSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(700)")
    private String content;

    public UserSentence(String content) {
        this.content = content;
    }

    public UserSentence() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
