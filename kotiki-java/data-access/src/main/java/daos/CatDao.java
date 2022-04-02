package daos;
import entities.Cat;
import org.hibernate.SessionFactory;

public class CatDao extends AbstractDao<Cat> {
    public CatDao(Class<Cat> entityType, SessionFactory sessionFactory) { super(entityType, sessionFactory); }
}
