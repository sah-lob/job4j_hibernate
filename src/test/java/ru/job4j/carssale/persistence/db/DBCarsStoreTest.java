package ru.job4j.carssale.persistence.db;

import org.junit.Test;
import ru.job4j.carssale.models.Car;

import static java.lang.Math.ceil;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DBCarsStoreTest {

    DBCarsStore dbCarsStore = DBCarsStore.getInstance();

    @Test
    public void testAdd() {
        var price = 12;
        var car = new Car("ford", "mondeo", price, true, 21, 1990, "Alexander");
        dbCarsStore.add(car);
        assertThat(price, is(dbCarsStore.findCar(1, dbCarsStore.size()).getPrice()));
    }

    @Test
    public void testFindCar() {
        var price = 13;
        var car = new Car("ford", "mondeo", price, true, 21, 1990, "Alexander");
        dbCarsStore.add(car);
        assertThat(price, is(dbCarsStore.findCar(1, dbCarsStore.size()).getPrice()));
    }

    @Test
    public void testSize() {
        var size = dbCarsStore.size();
        var car = new Car("ford", "mondeo", 234, true, 21, 1990, "Alexander");
        dbCarsStore.add(car);
        assertThat(size + 1, is(dbCarsStore.size()));
    }

    @Test
    public void testFindToPage() {
        var price = 1995;
        dbCarsStore.add(new Car("ford", "mondeo", price, true, 21, 1990, "Alexander"));
        var page = (int) Math.ceil((double) dbCarsStore.size() / (double) 10);
        System.out.println(page);
        var list = dbCarsStore.findToPage(page);
        assertThat(price, is(list.get(0).getPrice()));

    }
}