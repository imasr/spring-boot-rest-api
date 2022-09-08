package com.springrest.springrest.ui.model.response;

public enum SuccessMessages {
    RECORD_CREATED("All given record has been sucessfully addded in db"),
    RECORD_DELETED("Record deleted sucessfully"),
    RECORD_UPDATED("Record updated sucessfully"),
    RECORD_FETCHED("Record fetched successfully");

    private String successMessage;

    SuccessMessages(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * @return the successMessage
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    /**
     * @param successMessage the successMessage to set
     */
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
