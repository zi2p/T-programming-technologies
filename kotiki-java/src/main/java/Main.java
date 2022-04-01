import daos.CatDao;
import daos.OwnerDao;
import entities.Cat;
import entities.Owner;
import models.Color;
import services.CatService;
import services.InfrastructureOwnerService;
import services.InfrastrutureCatService;
import services.OwnerService;
import tools.SessionFactoryBuilder;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try {
            var url = args[0];
            var username = args[1];
            var password = args[2];
            var dialect = args[3];
            var driver = args[4];

            var sessionFactoryBuilder = new SessionFactoryBuilder();
            var sessionFactory = sessionFactoryBuilder
                    .build(url, username, password, dialect, driver);

            var catService = new CatService();
            var ownerService = new OwnerService();

            var catDao = new CatDao(Cat.class, sessionFactory);
            var ownerDao = new OwnerDao(Owner.class, sessionFactory);

            var infrastructureCatService = new InfrastrutureCatService(catService, catDao);
            var infrastructureOwnerService = new InfrastructureOwnerService(
                    ownerService, catDao, ownerDao);

            var date = LocalDateTime.of(2018, 12, 12, 0, 0, 0);
            var catOne = new Cat("Барсик", date, "Бродяга", Color.WHITE);
            var catTwo = new Cat("Сизый", date, "Симпотяга", Color.BLUE);

            var owner = new Owner("Кошачий барон", date);

            infrastructureOwnerService.addToDatabase(owner);
            infrastructureCatService.addToDatabase(catOne);
            infrastructureCatService.addToDatabase(catTwo);

            infrastructureCatService.friendCats(catOne, catTwo);
            infrastructureOwnerService.addCat(owner, catOne);
        } catch (Exception e) {
            System.out.println();
        }
    }
}
