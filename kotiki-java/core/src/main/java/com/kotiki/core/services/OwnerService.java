package com.kotiki.core.services;

import com.kotiki.core.entities.Cat;
import com.kotiki.core.entities.Owner;

public class OwnerService {
    public void addCat(Owner owner, Cat cat) {
        if (cat.getOwner() != null) {
            removeCat(cat.getOwner(), cat);
        }

        owner.addCat(cat);
        cat.setOwner(owner);
    }

    public void removeCat(Owner owner, Cat cat) {
        owner.removeCat(cat);
        cat.setOwner(null);
    }
}
