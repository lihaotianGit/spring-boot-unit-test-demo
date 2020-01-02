package cn.hunter.spring.exception;

public class NoSuchDataException extends RuntimeException {

    public NoSuchDataException(String message) {
        super(message);
    }

}
