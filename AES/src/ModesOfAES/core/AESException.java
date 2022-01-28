package ModesOfAES;

public class AESException extends RuntimeException {
    public AESException(String message) {
        super(message);
    }

    public AESException(String message, Throwable cause) {
        super(message, cause);
    }
} 