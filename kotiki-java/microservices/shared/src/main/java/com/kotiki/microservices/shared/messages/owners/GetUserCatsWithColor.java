package com.kotiki.microservices.shared.messages.owners;
import com.kotiki.core.models.Color;
import java.io.Serializable;

public record GetUserCatsWithColor(Long UserId, Color Color) implements Serializable {
    public static final String NAME = "GetUserCatsWithColor";
}
