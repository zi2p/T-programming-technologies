package daos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractDao<T extends Serializable> {
    private Class<T> entityType;
    protected SessionFactory sessionFactory;

    public AbstractDao(Class<T> entityType, SessionFactory sessionFactory) {
        this.entityType = entityType;
        this.sessionFactory = sessionFactory;
    }

    public void setEntityType(final Class<T> entityType) {
        this.entityType = entityType;
    }

    public T findOne(final long id) {
        return executeTransactionWithValue(s -> s.get(entityType, id));
    }

    public List<T> findAll() {
        return executeTransactionWithValue(s -> s
                .createQuery("from " + entityType.getName(), entityType)
                .list());
    }

    public T create(final T entity) {
        executeTransaction(s -> s.persist(entity));
        return entity;
    }

    public T update(final T entity) { return executeTransactionWithValue(s -> s.merge(entity)); }

    public void delete(final T entity) { executeTransaction(s -> s.remove(entity)); }

    public void deleteById(final long entityId) {
        executeTransaction(s -> {
            T entity = findOne(entityId);
            delete(entity);
        });
    }

    protected void executeTransaction(Consumer<Session> action) {
        var session = sessionFactory.getCurrentSession();

        var transaction = session.beginTransaction();
        action.accept(session);
        transaction.commit();
    }

    protected <R> R executeTransactionWithValue(Function<Session, R> function) {
        var session = sessionFactory.getCurrentSession();

        var transaction = session.beginTransaction();
        var value = function.apply(session);
        transaction.commit();

        return value;
    }
}