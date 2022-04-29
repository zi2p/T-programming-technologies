package com.kotiki.infrastructure.services;
import com.kotiki.dataAccess.daos.CatDao;
import com.kotiki.dataAccess.daos.OwnerDao;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.entities.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kotiki.core.services.OwnerService;
import java.util.List;

@Service
public class InfrastructureOwnerService {
    private final OwnerService ownerService;
    private final CatDao catDao;
    private final OwnerDao ownerDao;

    @Autowired
    public InfrastructureOwnerService(OwnerService ownerService, CatDao catDao, OwnerDao ownerDao) {
        this.ownerService = ownerService;
        this.catDao = catDao;
        this.ownerDao = ownerDao;
    }

    public List<Owner> getAll() { return ownerDao.findAll(); }

    public Owner getById(Long id) { return ownerDao.getById(id); }

    public void addCat(Owner owner, Cat cat) {
        ownerService.addCat(owner, cat);
        ownerDao.save(owner);
        catDao.save(cat);
    }

    public void removeCat(Owner owner, Cat cat) {
        ownerService.removeCat(owner, cat);
        ownerDao.save(owner);
        catDao.save(cat);
    }

    public Owner addToDatabase(Owner owner) { return ownerDao.save(owner); }
}
