package com.kotiki.microservices.shared.messages.owners;
import java.io.Serializable;

public record GetUserCatsWithBreed(Long UserId, String Breed) implements Serializable {
    public static final String NAME = "GetUserCatsWithBreed";
}
