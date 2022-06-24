package pl.ms.wordlistgenerator.word;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ms.wordlistgenerator.word.Word;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByNameAndWordType(String name, String wordType);
}
