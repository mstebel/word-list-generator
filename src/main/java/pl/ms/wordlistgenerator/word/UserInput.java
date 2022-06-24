package pl.ms.wordlistgenerator.word;

import org.springframework.stereotype.Component;

@Component
class UserInput {
    private String initialInput;

    public UserInput(String initialInput) {
        this.initialInput = initialInput;
    }

    public UserInput() {
    }

    public String getInitialInput() {
        return initialInput;
    }

    public void setInitialInput(String initialInput) {
        this.initialInput = initialInput;
    }
}
