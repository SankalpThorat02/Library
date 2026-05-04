package com.sankalp.library_api.dtos;

public class SessionUpdate {
    private Long sessionId;
    private Long pageNumber;

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public Long getPageNumber() { return pageNumber; }
    public void setPageNumber(Long pageNumber) { this.pageNumber = pageNumber; }
}
