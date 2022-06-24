package pl.ms.wordlistgenerator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.ms.wordlistgenerator.wordlist.WordList;
import pl.ms.wordlistgenerator.wordlist.WordListRepository;

import java.util.List;

@Controller
class HomeController {
    private WordListRepository wordListRepository;

    HomeController(WordListRepository wordListRepository) {
        this.wordListRepository = wordListRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<WordList> allLists = wordListRepository.findAll();
        model.addAttribute("wordLists", allLists);
        return "home";
    }
}
