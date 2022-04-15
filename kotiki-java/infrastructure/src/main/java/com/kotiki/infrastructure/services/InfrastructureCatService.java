package com.kotiki.infrastructure.services;
import com.kotiki.dataAccess.daos.CatDao;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.models.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kotiki.core.services.CatService;
import java.util.List;

@Service
public class InfrastructureCatService {
    @Autowired
    private CatService catService;

    @Autowired
    private CatDao catDao;

    public List<Cat> getAll() { return catDao.findAll(); }

    public Cat getById(Long id) { return catDao.getById(id); }

    public void renameCat(Cat cat, String name) {
        catService.renameCat(cat, name);
        catDao.save(cat);
    }

    public void friendCats(Cat left, Cat right) {
        catService.friendCats(left, right);
        catDao.save(left);
        catDao.save(right);
    }

    public void unfriendCats(Cat left, Cat right) {
        catService.unfriendCats(left, right);
        catDao.save(left);
        catDao.save(right);
    }

    public void paintCat(Cat cat, Color color) {
        catService.paintCat(cat, color);
        catDao.save(cat);
    }

    public Cat addToDatabase(Cat cat) { return catDao.save(cat); }
}
