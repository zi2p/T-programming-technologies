package com.kotiki.microservices.shared.messages.cats;
import java.io.Serializable;

public record GetAllCats() implements Serializable {
    public static final String NAME = "GetAllCats";
}
