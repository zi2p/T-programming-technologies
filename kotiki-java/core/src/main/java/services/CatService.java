package services;
import entities.Cat;
import models.Color;

public class CatService {
    public void renameCat(Cat cat, String name) { cat.setName(name); }

    void friendCats(Cat left, Cat right) {
        left.addFriend(right);
        right.addFriend(left);
    }

    void unfriendCats(Cat left, Cat right) {
        left.removeFriend(right);
        right.removeFriend(left);
    }

    void paintCat(Cat cat, Color color) { cat.setColor(color); }
}
