package com.kotiki.core.services;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.models.Color;

public class CatService {
    public void renameCat(Cat cat, String name) { cat.setName(name); }

    public void friendCats(Cat left, Cat right) {
        left.addFriend(right);
        right.addFriend(left);
    }

    public void unfriendCats(Cat left, Cat right) {
        left.removeFriend(right);
        right.removeFriend(left);
    }

    public void paintCat(Cat cat, Color color) { cat.setColor(color); }
}
