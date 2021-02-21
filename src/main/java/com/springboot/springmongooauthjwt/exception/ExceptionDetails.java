package com.springboot.springmongooauthjwt.exception;

import java.util.Date;

public class ExceptionDetails {

        private Date timestamp;
        private String message;
        private int status;
        private String error;
        private String path;

    public ExceptionDetails(Date timestamp, String message, int status, String error, String path) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
