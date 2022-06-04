package com.kotiki.microservices.shared.messages.owners;
import java.io.Serializable;

public record GetOwnerWithId(Long Id) implements Serializable {
    public static final String NAME = "GetOwnerWithId";
}
