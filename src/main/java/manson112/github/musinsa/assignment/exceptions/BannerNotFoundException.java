package manson112.github.musinsa.assignment.exceptions;

public class BannerNotFoundException extends RuntimeException {

    public BannerNotFoundException(Long bannerId) {
        super("Could not find Banner for " + bannerId);
    }

    public BannerNotFoundException(String message) {
        super(message);
    }

    public BannerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
