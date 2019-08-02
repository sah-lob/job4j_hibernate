package ru.job4j.todo.persistent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.todo.models.Item;
import java.util.List;


public class DBStore implements Store {

    private static DBStore dbStore = new DBStore();

    private SessionFactory factory = new Configuration().configure().buildSessionFactory();


    public static DBStore getInstance() {
        return dbStore;
    }

    @Override
    public void add(Item item) {
        var session = factory.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Item> findAll() {
        var session = factory.openSession();
        session.beginTransaction();
        var result = session.createQuery("from Item", Item.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void resetStatus(int id, boolean done) {
        var session = factory.openSession();
        session.beginTransaction();
        var item = session.get(Item.class, id);
        item.setDone(done);
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }
}
