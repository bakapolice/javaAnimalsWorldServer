package exceptions;

public class AttemptToFeedDeadAnimal extends RuntimeException{
    public static final String DEFAULT_MESSAGE = "Нельзя накормить мертвое животное!";
    public AttemptToFeedDeadAnimal() {super(DEFAULT_MESSAGE);}
    public AttemptToFeedDeadAnimal(String message) {super(message);}
}
