package exceptions;

public class AttemptToKillDead extends RuntimeException{
    public static final String DEFAULT_MESSAGE = "Нельзя убить мертвое животное!";
    public AttemptToKillDead() {super(DEFAULT_MESSAGE);}
    public AttemptToKillDead(String message) {super(message);}
}
