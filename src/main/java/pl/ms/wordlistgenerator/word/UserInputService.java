package pl.ms.wordlistgenerator.word;

import org.springframework.stereotype.Service;
import pl.ms.wordlistgenerator.entry.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserInputService {
    private final List<String> wordTypes;

    public UserInputService() {
        wordTypes = Arrays.asList("verb", "noun", "adjective", "adverb", "other");
    }

    public List<String> getWordTypes() {
        return wordTypes;
    }

    public List<Entry> transformInputIntoSeparateWords(UserInput userInput) {
        String[] split = userInput.getInitialInput().split(System.lineSeparator());
        List<String> splitInput = Arrays.stream(split).toList();
        List<Entry> initialEntries = new ArrayList<>();
        for (String initialWord : splitInput) {
            initialEntries.add(new Entry(new UserWord(initialWord)));
        }
        return initialEntries;
    }
}
