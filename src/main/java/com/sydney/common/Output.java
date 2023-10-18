package com.sydney.common;

public class Output {

    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";
    private String code;
    private String message;
    private Object data;

    public static Output success(){
        Output output = new Output();
        output.setCode(SUCCESS);
        return output;
    }

    public static Output success(Object data){
        Output output = new Output();
        output.setCode(SUCCESS);
        output.setData(data);
        return output;
    }

    public static Output error(String message){
        Output output = new Output();
        output.setCode(ERROR);
        output.setMessage(message);
        return output;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
