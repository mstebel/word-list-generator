package pl.ms.wordlistgenerator.wordlist;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ms.wordlistgenerator.wordlist.WordList;

public interface WordListRepository extends JpaRepository<WordList, Long> {

}
