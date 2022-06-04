package com.kotiki.microservices.shared.messages.owners;
import java.io.Serializable;

public record GetUserCats(Long UserId) implements Serializable {
    public static final String NAME = "GetUserCats";
}
