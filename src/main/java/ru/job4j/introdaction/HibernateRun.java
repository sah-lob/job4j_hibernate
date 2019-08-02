package ru.job4j.introdaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.introdaction.models.User;
import java.sql.Timestamp;

public class HibernateRun {
    public static void main(String[] args) {
        var sessionFactory = new Configuration().configure().buildSessionFactory();
        var session = sessionFactory.openSession();
        session.beginTransaction();
        var user = new User();
        user.setName("test name");
        user.setExpired(new Timestamp(System.currentTimeMillis()));
        session.saveOrUpdate(user);
        var user2 = session.get(User.class, user.getId());
        System.out.println(user2);
        user.setName("First_Updated");
        session.saveOrUpdate(user);
        session.delete(user);
        var res = session.createQuery("from User", User.class).list();
        System.out.println(res.size());
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
