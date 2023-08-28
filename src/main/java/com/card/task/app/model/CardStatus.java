package com.card.task.app.model;

public enum CardStatus {

    TODO("To do"),
    IN_PROGRESS("InProgress"),
    DONE("Done");

    private String status;

    CardStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
