package com.kotiki.microservices.shared.messages.owners;
import java.io.Serializable;

public record GetAllOwners() implements Serializable {
    public static final String NAME = "GetAllOwners";
}
