package pl.ms.wordlistgenerator.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ms.wordlistgenerator.wordlist.WordList;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
