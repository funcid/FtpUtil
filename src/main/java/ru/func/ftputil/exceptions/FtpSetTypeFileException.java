package ru.func.ftputil.exceptions;

public class FtpSetTypeFileException extends Exception {

    public FtpSetTypeFileException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getCause().getMessage();
    }
}
