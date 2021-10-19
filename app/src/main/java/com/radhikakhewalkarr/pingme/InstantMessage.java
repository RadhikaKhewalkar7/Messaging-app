package com.radhikakhewalkarr.pingme;

public class InstantMessage {
    private String message;
    private String author;


    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

//    blank constructor has to be created here bcoz in firebase we have to store date
    public InstantMessage() {

    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
