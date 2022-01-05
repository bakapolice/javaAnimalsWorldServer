package exceptions;

public class AttemptToFeedAnotherFood extends RuntimeException{
    public static final String DEFAULT_MESSAGE = "Нельзя накормить животное тем, что оно не ест!";
    public AttemptToFeedAnotherFood() {super(DEFAULT_MESSAGE);}
    public AttemptToFeedAnotherFood(String message) {super(message);}
}
