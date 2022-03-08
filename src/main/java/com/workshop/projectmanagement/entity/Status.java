package com.workshop.projectmanagement.entity;

public enum Status {
    READY_FOR_DEV ("Ready for dev"),
    IN_DEVELOPMENT ("In Development"),
    READY_FOR_CODE_REVIEW ("Ready for Code Review"),
    IN_REVIEW ("In review"),
    DONE ("Done"),
    CLOSED ("Closed");

    private String status;
    Status(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
