package com.kotiki.microservices.shared.models;
import com.kotiki.core.models.Color;
import java.io.Serializable;

public class CreateCatModel implements Serializable {

    private String catName;
    private String breed;
    private Color color;

    public String getCatName() { return catName; }

    public String getBreed() { return breed; }

    public Color getColor() { return color; }
}
