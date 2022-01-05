package exceptions;

public class AttemptToFeedCarrion extends RuntimeException{
    public static final String DEFAULT_MESSAGE = "Нельзя накормить хищника падалью!";
    public AttemptToFeedCarrion() {super(DEFAULT_MESSAGE);}
    public AttemptToFeedCarrion(String message) {super(message);}
}
