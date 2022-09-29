package manson112.github.musinsa.assignment.exceptions;

public class MenuNotFoundException extends RuntimeException {

    public MenuNotFoundException(Long menuId) {
        this("Could not find Menu for " + menuId);
    }

    public MenuNotFoundException(String message) {
        super(message);
    }

    public MenuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
