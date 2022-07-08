package com.springrest.springrest.ui.model.response;

public class Response {
    
    private Object data;
    private String message;
    
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    
}
