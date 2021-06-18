/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.service;

import com.dev.com.dto.DigoHttpExceptionDto;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@AllArgsConstructor
public class DigoHttpException extends RuntimeException {

    private DigoHttpExceptionDto digoHttpExceptionDto = new DigoHttpExceptionDto();
    private String[] arguments;
    private int statusCode = HttpServletResponse.SC_BAD_REQUEST;
    

    public DigoHttpException(int code) {
        getDigoHttpExceptionDto().setCode(code);
    }
    
    public DigoHttpException(int code, int statusCode) {
        getDigoHttpExceptionDto().setCode(code);
        setStatusCode(statusCode);
    }
    
    public DigoHttpException(int code, String[] arguments) {
        getDigoHttpExceptionDto().setCode(code);
        setArguments(arguments);
    }
    
    public DigoHttpException(int code, String[] arguments, int statusCode) {
        getDigoHttpExceptionDto().setCode(code);
        setArguments(arguments);
        setStatusCode(statusCode);
    }
}
