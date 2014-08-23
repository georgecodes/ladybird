package com.elevenware.ladybird.entities;

import java.io.IOException;

public class UnmarshallingException extends RuntimeException {
    public UnmarshallingException(String message, IOException e) {
        super(message, e);
    }
}
