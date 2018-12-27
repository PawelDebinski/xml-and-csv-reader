package pl.pawel.fileloader.exceptions;

public class InvalidFileFormatException extends RuntimeException {

    public InvalidFileFormatException() {
    }

    public InvalidFileFormatException(String message) {
        super(message);
    }
}
