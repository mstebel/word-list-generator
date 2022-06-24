package pl.ms.wordlistgenerator.word;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ms.wordlistgenerator.entry.Entry;
import pl.ms.wordlistgenerator.entry.EntryRepository;
import pl.ms.wordlistgenerator.wordlist.WordList;
import pl.ms.wordlistgenerator.wordlist.WordListRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
class WordController {
    private UserInputService userInputService;
    private UserInput userInput;
    private WordRepository wordRepository;
    private EntryRepository entryRepository;
    private WordListRepository wordListRepository;

    public WordController(UserInputService userInputService, UserInput userInput, WordRepository wordRepository, EntryRepository entryRepository, WordListRepository wordListRepository) {
        this.userInputService = userInputService;
        this.userInput = userInput;
        this.wordRepository = wordRepository;
        this.entryRepository = entryRepository;
        this.wordListRepository = wordListRepository;
    }

    @GetMapping("/addlist")
    public String addList(Model model) {
        model.addAttribute("userInput", new UserInput());
        model.addAttribute("wordList", new WordList());
        return "userInput";
    }

    @PostMapping("/wordlist")
    public String getInitialInput(UserInput userInput, Model model, WordList wordList) {
        List<Entry> userEntries = userInputService.transformInputIntoSeparateWords(userInput);
        wordList.setDate(LocalDate.now());
        wordList.setEntries(userEntries);
        wordListRepository.save(wordList);
        saveEntriesInDb(wordList, userEntries);
        model.addAttribute("userEntries", userEntries);
        model.addAttribute("wordTypes", userInputService.getWordTypes());
        return "redirect:/entriesDataForm/" + wordList.getId();
    }

    private void saveEntriesInDb(WordList wordList, List<Entry> userEntries) {
        for (Entry userEntry : userEntries) {
            userEntry.setWordlist(wordList);
            entryRepository.save(userEntry);
        }
    }

    @GetMapping("/entriesDataForm/{id}")
    public String entryDataForm(@PathVariable Long id, Model model) {
        Optional<WordList> byId = wordListRepository.findById(id);
        WordList wordList = byId.orElseThrow(IllegalArgumentException::new);
        model.addAttribute("userEntries", wordList.getEntries());
        return "entryDataForm";
    }


}
