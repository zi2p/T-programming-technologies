package daos;
import entities.Owner;
import org.hibernate.SessionFactory;

public class OwnerDao extends AbstractDao<Owner> {
    public OwnerDao(Class<Owner> entityType, SessionFactory sessionFactory) { super(entityType, sessionFactory); }
}
