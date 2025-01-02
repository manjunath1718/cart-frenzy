package com.dcw.cartfrenzy.exception;

import java.time.LocalDateTime;


public class ErrorDetails {

    private String statusCode;

    private String msg;

    private LocalDateTime when;

    public ErrorDetails(String statusCode, String msg, LocalDateTime when) {
        this.statusCode = statusCode.toString();
        this.msg = msg;
        this.when = LocalDateTime.now();
    }


}
