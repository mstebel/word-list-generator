package pl.ms.wordlistgenerator.entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ms.wordlistgenerator.sentence.Sentence;
import pl.ms.wordlistgenerator.sentence.SentenceRepository;
import pl.ms.wordlistgenerator.word.UserInputService;
import pl.ms.wordlistgenerator.word.Word;
import pl.ms.wordlistgenerator.word.WordRepository;
import pl.ms.wordlistgenerator.wordlist.WordList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
class EntryController {
    private EntryRepository entryRepository;
    private UserInputService userInputService;
    private WordRepository wordRepository;
    private SentenceRepository sentenceRepository;

    public EntryController(EntryRepository entryRepository, UserInputService userInputService, WordRepository wordRepository, SentenceRepository sentenceRepository) {
        this.entryRepository = entryRepository;
        this.userInputService = userInputService;
        this.wordRepository = wordRepository;
        this.sentenceRepository = sentenceRepository;
    }

    @GetMapping("/wordType/set")
    public String addWordTypeForm(@RequestParam(required = false) Long id, Model model) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entry = byId.orElseThrow(IllegalArgumentException::new);
        model.addAttribute("entry", entry);
        model.addAttribute("wordTypes", userInputService.getWordTypes());
        return "addWordType";
    }

    @GetMapping("/definitions/{id}")
    public String definitionsForm(@PathVariable Long id, Model model) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entry = byId.orElseThrow(IllegalArgumentException::new);
        model.addAttribute("entry", entry);
        List<Word> words = wordRepository.findByNameAndWordType(entry.getWord().getName(), entry.getWord().getWordType());
        List<String> definitions = words.stream()
                .map(Word::getDefinition).collect(Collectors.toList());
        model.addAttribute("definitions", definitions);
        return "definitions";
    }

    @PostMapping("/definitions/{id}")
    public String findDefinitions(Model model, @PathVariable Long id, Entry entry) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entryDb = byId.orElseThrow(IllegalArgumentException::new);
        entryDb.getWord().setWordType(entry.getWord().getWordType());
        entryRepository.save(entryDb);
        model.addAttribute("entry", entryDb);
        return "redirect:/definitions/" + entry.getId();
    }

    @GetMapping("/sentences/{id}")
    public String sentencesForm(@PathVariable Long id, Model model) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entry = byId.orElseThrow(IllegalArgumentException::new);
        model.addAttribute("entry", entry);
        String wordName = String.format(" %s ", entry.getWord().getName());
        List<Sentence> sentences = sentenceRepository.findByContentContains(wordName);
        List<String> sentencesContent = sentences.stream()
                .map(Sentence::getContent).toList();
        model.addAttribute("sentences", sentencesContent);
        return "sentences";
    }


    @PostMapping("sentences/{id}")
    public String chooseSentences(Model model, @PathVariable Long id, Entry entry) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entryDb = byId.orElseThrow(IllegalArgumentException::new);
        entryDb.getWord().setDefinition(entry.getWord().getDefinition());
        entryRepository.save(entryDb);
        model.addAttribute("entry", entryDb);
        return "redirect:/sentences/" + entry.getId();
    }

    @GetMapping("/completeEntry/{id}")
    public String showCompleteEntry(Model model, @PathVariable Long id) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entry = byId.orElseThrow(IllegalArgumentException::new);
        WordList wordList = entry.getWordlist();
        model.addAttribute("entry", entry);
        model.addAttribute("wordList", wordList);
        return "singleEntryEdit";

    }

    @PostMapping("completeEntry/{id}")
    public String saveCompleteEntry(Model model, @PathVariable Long id, Entry entry) {
        Optional<Entry> byId = entryRepository.findById(id);
        Entry entryDb = byId.orElseThrow(IllegalArgumentException::new);
        entryDb.setUserSentence(entry.getUserSentence());
        entryRepository.save(entryDb);
        return "redirect:/completeEntry/" + entry.getId();
    }
}
