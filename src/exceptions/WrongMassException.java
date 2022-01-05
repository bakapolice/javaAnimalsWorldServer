package exceptions;

public class WrongMassException extends RuntimeException{
    public static final String DEFAULT_MESSAGE = "Нельзя создать животное с отрицательной или нулевой массой!!";
    public WrongMassException() {super(DEFAULT_MESSAGE);}
    public WrongMassException(String message) {super(message);}
}
