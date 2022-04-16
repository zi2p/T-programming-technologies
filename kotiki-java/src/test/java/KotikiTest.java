import com.kotiki.dataAccess.daos.CatDao;
import  com.kotiki.dataAccess.daos.OwnerDao;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.entities.Owner;
import com.kotiki.core.models.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.kotiki.core.services.CatService;
import com.kotiki.infrastructure.services.InfrastructureOwnerService;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import org.mockito.*;
import com.kotiki.core.services.OwnerService;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KotikiTest {
    private InfrastructureOwnerService infrastructureOwnerService;
    private InfrastructureCatService infrastructureCatService;
    private CatDao catDao;
    private OwnerDao ownerDao;

    @BeforeEach
    public void setup() {
        catDao = Mockito.mock(CatDao.class);
        when(catDao.save(any(Cat.class))).thenAnswer(i -> i.getArguments()[0]);

        ownerDao = Mockito.mock(OwnerDao.class);
        when(ownerDao.save(any(Owner.class))).thenAnswer(i -> i.getArguments()[0]);

        infrastructureOwnerService = new InfrastructureOwnerService(new OwnerService(), catDao, ownerDao);
        infrastructureCatService = new InfrastructureCatService(new CatService(), catDao);
    }

    @Test
    public void addCatToOwner_OwnerCatsContainsCat() {
        LocalDateTime date1 = LocalDateTime.of(2001, 12, 12, 11, 0, 3);
        LocalDateTime date2 = LocalDateTime.of(2020, 1, 3, 4, 12, 0);
        Owner owner = new Owner("Виктор", date1);
        Cat cat = new Cat("Федя", date2, "Дворняга", Color.ORANGE);

        infrastructureOwnerService.addCat(owner, cat);

        verify(ownerDao, times(1)).save(owner);
        verify(catDao, times(1)).save(cat);

        assertTrue(owner.getCats().contains(cat));
    }

    @Test
    public void makeCatsFriends_BothCatsUpdated() {
        LocalDateTime date1 = LocalDateTime.of(2019, 12, 12, 11, 0, 3);
        LocalDateTime date2 = LocalDateTime.of(2020, 1, 3, 4, 12, 0);
        Cat cat1 = new Cat("Виктор", date1, "Дворняга", Color.BLACK);
        Cat cat2 = new Cat("Федя", date2, "Дворняга", Color.ORANGE);

        infrastructureCatService.friendCats(cat1, cat2);

        verify(catDao, times(1)).save(cat1);
        verify(catDao, times(1)).save(cat2);

        assertTrue(cat1.getFriends().contains(cat2));
    }
}
