package ru.func.ftputil.exceptions;

public class FtpLoginException extends Exception {

    public FtpLoginException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getCause().getMessage();
    }
}
