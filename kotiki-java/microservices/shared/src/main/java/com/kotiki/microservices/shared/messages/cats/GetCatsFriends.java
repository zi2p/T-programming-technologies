package com.kotiki.microservices.shared.messages.cats;
import java.io.Serializable;

public record GetCatsFriends(Long Id) implements Serializable {
    public static final String NAME = "GetCatsFriends";
}
