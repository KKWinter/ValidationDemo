package com.jumpraw.mraid.cache;

public class RedirectsException extends Exception {
    private String newUrl;

    public RedirectsException(String newUrl) {
        this.newUrl = newUrl;
    }

    public String getNewUrl() {
        return newUrl;
    }
}
