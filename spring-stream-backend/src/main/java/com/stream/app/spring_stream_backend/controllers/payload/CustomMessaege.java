package com.stream.app.spring_stream_backend.controllers.payload;

public class CustomMessaege {
    private String message;
    private boolean success;

    // No-args constructor
    public CustomMessaege() {
    }

    // All-args constructor
    public CustomMessaege(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Builder-style static method
    public static CustomMessaegeBuilder builder() {
        return new CustomMessaegeBuilder();
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Static Builder Class
    public static class CustomMessaegeBuilder {
        private String message;
        private boolean success;

        public CustomMessaegeBuilder message(String message) {
            this.message = message;
            return this;
        }

        public CustomMessaegeBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public CustomMessaege build() {
            return new CustomMessaege(message, success);
        }
    }
}
