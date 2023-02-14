package ru.practicum_services.qa_scooter.responses.courier;

public class CourierResponse {
    private boolean ok;
    private int code;
    private String message;
    private int id;

    public CourierResponse(boolean ok) {
        this.ok = ok;
    }

    public CourierResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CourierResponse() {
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
