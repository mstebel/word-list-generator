package pl.ms.wordlistgenerator.word;

import javax.persistence.*;

@Entity
public class UserWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String wordType;
    private String name;
    @Column(columnDefinition = "varchar(700)")
    private String definition;

    public UserWord(Long id, String wordType, String name, String definition) {
        this.id = id;
        this.wordType = wordType;
        this.name = name;
        this.definition = definition;
    }

    public UserWord() {
    }

    public UserWord(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
