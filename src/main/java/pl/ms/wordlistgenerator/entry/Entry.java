package pl.ms.wordlistgenerator.entry;

import pl.ms.wordlistgenerator.sentence.UserSentence;
import pl.ms.wordlistgenerator.word.UserWord;
import pl.ms.wordlistgenerator.wordlist.WordList;

import javax.persistence.*;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private UserWord userWord;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private UserSentence userSentence;
    @ManyToOne
    @JoinColumn(name = "list_id")
    private WordList wordlist;

    public Entry(Long id, UserWord userWord, UserSentence userSentence, WordList wordlist) {
        this.id = id;
        this.userWord = userWord;
        this.userSentence = userSentence;
        this.wordlist = wordlist;
    }

    public Entry(UserWord word) {
        this.userWord = word;
    }

    public Entry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserWord getWord() {
        return userWord;
    }

    public void setWord(UserWord word) {
        this.userWord = word;
    }

    public UserWord getUserWord() {
        return userWord;
    }

    public void setUserWord(UserWord userWord) {
        this.userWord = userWord;
    }

    public UserSentence getUserSentence() {
        return userSentence;
    }

    public void setUserSentence(UserSentence sentence) {
        this.userSentence = sentence;
    }

    public WordList getWordlist() {
        return wordlist;
    }

    public void setWordlist(WordList wordlist) {
        this.wordlist = wordlist;
    }
}
