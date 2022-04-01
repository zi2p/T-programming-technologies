package services;
import daos.CatDao;
import daos.OwnerDao;
import entities.Cat;
import entities.Owner;

public class InfrastructureOwnerService {
    private final OwnerService ownerService;
    private final CatDao catDao;
    private final OwnerDao ownerDao;

    public InfrastructureOwnerService(OwnerService ownerService, CatDao catDao, OwnerDao ownerDao) {
        this.ownerService = ownerService;
        this.catDao = catDao;
        this.ownerDao = ownerDao;
    }

    public void addCat(Owner owner, Cat cat) {
        ownerService.addCat(owner, cat);

        ownerDao.update(owner);
        catDao.update(cat);
    }

    public void removeCat(Owner owner, Cat cat) {
        ownerService.removeCat(owner, cat);

        ownerDao.update(owner);
        catDao.update(cat);
    }

    public Owner addToDatabase(Owner owner) { return ownerDao.create(owner); }
}
