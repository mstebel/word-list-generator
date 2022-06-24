package pl.ms.wordlistgenerator.wordlist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ms.wordlistgenerator.entry.Entry;
import pl.ms.wordlistgenerator.entry.EntryRepository;

import java.util.Optional;

@Controller
class WordListController {
    private WordListRepository wordListRepository;
    private EntryRepository entryRepository;

    WordListController(WordListRepository wordListRepository, EntryRepository entryRepository) {
        this.wordListRepository = wordListRepository;
        this.entryRepository = entryRepository;
    }

    @GetMapping("wordList")
    public String showWordList(Model model, @RequestParam Long id) {
        Optional<WordList> byId = wordListRepository.findById(id);
        WordList wordList = byId.orElseThrow(IllegalArgumentException::new);
        model.addAttribute("wordList", wordList);
        return "wordList";
    }

    @GetMapping("/wordList/{listId}/entry/delete")
    public String deleteEntry(Model model, @RequestParam Long id, @PathVariable Long listId) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entryDb = byId.orElseThrow(IllegalArgumentException::new);
        entryRepository.delete(entryDb);
        Optional<WordList> optionalList = wordListRepository.findById(listId);
        WordList wordList = optionalList.orElseThrow(IllegalArgumentException::new);
        model.addAttribute("wordList", wordList);
        return "redirect:/wordList?id=" + wordList.getId();
    }

    @PostMapping("/saveList/{id}")
    public String saveList(@PathVariable Long id, Model model) {
        Optional<WordList> byId = wordListRepository.findById(id);
        WordList wordList = byId.orElseThrow(IllegalArgumentException::new);
        model.addAttribute("wordList", wordList);
        return "redirect:/";
    }
}

