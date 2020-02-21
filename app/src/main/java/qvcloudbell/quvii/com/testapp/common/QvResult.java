package qvcloudbell.quvii.com.testapp.common;

public class QvResult<T> {
    private int code;

    private T Object;
    private String errorInfo = "";

    public boolean retSuccess() {
        return code == 0;
    }

    public QvResult(int code) {
        this.code = code;
    }

    public QvResult(int code, String errorInfo) {
        this.code = code;
        this.errorInfo = errorInfo;
    }

    public QvResult(T object) {
        Object = object;
    }

    public T getObject() {
        return Object;
    }

    public int getCode() {
        return code;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
