package ru.job4j.todo.persistent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.todo.models.Item;
import java.util.List;
import java.util.function.Function;


public class DBStore implements Store {

    private final static DBStore DB_STORE = new DBStore();
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();


    public static DBStore getInstance() {
        return DB_STORE;
    }

    @Override
    public void add(Item item) {
        this.tx(session -> session.save(item));
    }

    @Override
    public List<Item> findAll() {
      return this.tx(session -> session.createQuery("from  Item ", Item.class).list());
    }

    @Override
    public void resetStatus(int id, boolean done) {
        this.tx(session -> {
            session.beginTransaction();
            Item item = session.get(Item.class, id);
            item.setDone(done);
            session.update(item);
            return true;
        });
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
