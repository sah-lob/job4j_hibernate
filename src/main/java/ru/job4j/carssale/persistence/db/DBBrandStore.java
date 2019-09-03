package ru.job4j.carssale.persistence.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.carssale.models.BrandAndModel;
import ru.job4j.carssale.persistence.interfaces.BrandStore;

import java.util.List;
import java.util.function.Function;

public class DBBrandStore implements BrandStore {


    private final static DBBrandStore DB_BRAND_STORE = new DBBrandStore();
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();


    public static DBBrandStore getInstance() {
        return DB_BRAND_STORE;
    }

    @Override
    public boolean delete(String brand, String model) {
        return false;
    }

    @Override
    public void add(String brand, String model) {
        var sql = "SELECT brand FROM brands WHERE brand = '" + brand + "' AND model = '" + model + "';";
        var list = this.tx(session -> session.createSQLQuery(sql).list());
        if (list.size() == 0) {
            var brandAndModel = new BrandAndModel(brand, model);
            this.tx(session -> session.save(brandAndModel));
        }
    }

    @Override
    public List<String> getBrands() {
        var sql = "SELECT DISTINCT brand FROM brands;";
        return this.tx(session -> session.createSQLQuery(sql).list());
    }

    @Override
    public List<String> getModels(String brand) {
        var sql = "SELECT DISTINCT model FROM brands WHERE brand = '" + brand + "';";
        return this.tx(session -> session.createSQLQuery(sql).list());
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
