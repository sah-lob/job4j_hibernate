package ru.job4j.carssale.persistence.db;

import org.junit.Test;
import ru.job4j.carssale.models.BrandAndModel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DBBrandStoreTest {

    private final DBBrandStore dbBrandStore = DBBrandStore.getInstance();

    @Test
    public void testAdd() {
        var brand = "ford";
        var model = "mondeo";
        dbBrandStore.add("ford", "focus");
        dbBrandStore.add("ford", "focus");
        dbBrandStore.add("ford", "focus");
        dbBrandStore.add("ford", "focus");
        dbBrandStore.add("mazda", model);
        dbBrandStore.add("ford", model);
        dbBrandStore.add("malta", model);
        assertThat(brand, is(dbBrandStore.getBrands().get(0)));
    }

    @Test
    public void testGetBrands() {
        var size = dbBrandStore.getBrands().size();
        dbBrandStore.add("testBrands", "test");
        assertThat(size + 1, is(dbBrandStore.getBrands().size()));
    }

    @Test
    public void testGetModels() {
        dbBrandStore.add("ford", "focus");
        dbBrandStore.add("ford", "mondeo");
        assertThat(2, is(dbBrandStore.getModels("ford").size()));

    }
}