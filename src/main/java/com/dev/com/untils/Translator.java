/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.untils;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 *
 * @author Admin
 */
public class Translator {

    private Short currentLocaleId;
    private MessageSource source;

    public void setMessageSource(MessageSource source) {
        this.source = source;
    }

    public String toLocale(String name) {
        return source.getMessage(name, null, getCurrentLocale());
    }

    public String toLocale(String name, String[] arguments) {
        return source.getMessage(name, arguments, getCurrentLocale());
    }

    public void setCurrentLocaleId(Short currentLocaleId) {
        this.currentLocaleId = currentLocaleId;
    }

    public Short getCurrentLocaleId() {
        LocaleContextHolder.getLocale();
        return currentLocaleId;
    }

    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
