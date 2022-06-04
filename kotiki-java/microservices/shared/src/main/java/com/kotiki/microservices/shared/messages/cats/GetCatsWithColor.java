package com.kotiki.microservices.shared.messages.cats;
import com.kotiki.core.models.Color;
import java.io.Serializable;

public record GetCatsWithColor(Color Color) implements Serializable {
    public static final String NAME = "GetCatsWithColor";
}
