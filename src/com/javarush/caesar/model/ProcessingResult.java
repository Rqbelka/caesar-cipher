package com.javarush.caesar.model;

public class ProcessingResult {

    private final boolean success;
    private final String message;
    private final String input;
    private final String output;


    public ProcessingResult(boolean success, String message, String input, String output) {
        this.success = success;
        this.message = message;
        this.input = input;
        this.output = output;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "ProcessingResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", inputPreview='" + input + '\'' +
                ", outputPreview='" + output + '\'' +
                '}';
    }
}
