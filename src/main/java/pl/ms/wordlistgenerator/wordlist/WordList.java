package pl.ms.wordlistgenerator.wordlist;

import org.springframework.format.annotation.DateTimeFormat;
import pl.ms.wordlistgenerator.entry.Entry;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class WordList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "wordlist",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Entry> entries;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    private String name;

    public WordList(List<Entry> entries, LocalDate date, String name) {
        this.entries = entries;
        this.date = date;
        this.name = name;
    }

    public WordList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
