package pl.wieik.ti.ti2023lab5.model;

public class HammingResponse {
    String message;
    int error_loc;
    int[] response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError_loc() {
        return error_loc;
    }

    public void setError_loc(int error_loc) {
        this.error_loc = error_loc;
    }

    public int[] getResponse() {
        return response;
    }

    public void setResponse(int[] response) {
        this.response = response;
    }
}
