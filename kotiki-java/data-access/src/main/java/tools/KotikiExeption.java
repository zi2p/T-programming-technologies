package tools;

public class KotikiExeption extends RuntimeException {

    private Integer errorCode;

    public KotikiExeption(String message) { super(message); }

    public KotikiExeption(String message, Throwable cause) { super(message, cause); }

    public Integer getErrorCode() { return errorCode; }
}
