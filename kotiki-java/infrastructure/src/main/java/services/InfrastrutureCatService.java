package services;
import daos.CatDao;
import entities.Cat;
import models.Color;

public class InfrastrutureCatService {
    private final CatService catService;
    private final CatDao catDao;

    public InfrastrutureCatService(CatService catService, CatDao catDao) {
        this.catService = catService;
        this.catDao = catDao;
    }

    public void renameCat(Cat cat, String name) {
        catService.renameCat(cat, name);
        catDao.update(cat);
    }

    public void friendCats(Cat left, Cat right) {
        catService.friendCats(left, right);
        catDao.update(left);
        catDao.update(right);
    }

    public void unfriendCats(Cat left, Cat right) {
        catService.unfriendCats(left, right);
        catDao.update(left);
        catDao.update(right);
    }

    public void paintCat(Cat cat, Color color) {
        catService.paintCat(cat, color);
        catDao.update(cat);
    }

    public Cat addToDatabase(Cat cat) { return catDao.create(cat); }
}
