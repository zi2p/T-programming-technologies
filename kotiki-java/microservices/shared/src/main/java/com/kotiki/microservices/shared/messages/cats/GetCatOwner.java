package com.kotiki.microservices.shared.messages.cats;
import java.io.Serializable;

public record GetCatOwner(Long Id) implements Serializable {
    public static final String NAME = "GetCatOwner";
}
