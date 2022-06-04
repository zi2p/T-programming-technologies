package com.kotiki.microservices.shared.messages.cats;
import com.kotiki.microservices.shared.models.CreateCatModel;
import java.io.Serializable;

public record CreateCat(CreateCatModel Model) implements Serializable {
    public static final String NAME = "CreateCat";
}
