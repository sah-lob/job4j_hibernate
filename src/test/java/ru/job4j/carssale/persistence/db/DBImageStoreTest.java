package ru.job4j.carssale.persistence.db;
import org.junit.Test;
import ru.job4j.carssale.models.Image;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DBImageStoreTest {

    private final DBImageStore dbImageStore = DBImageStore.getInstance();

    @Test
    public void testAdd() {
        int carId = 3;
        String img = "new Image";
        Image image = new Image(carId, img);
        dbImageStore.addImg(image);
        assertThat(img, is(dbImageStore.getImg(carId)));
    }

    @Test
    public void testSize() {
        int carId = 4;
        int size = dbImageStore.size();
        String img = "new Image";
        Image image = new Image(carId, img);
        dbImageStore.addImg(image);
        assertThat(size + 1, is(dbImageStore.size()));
    }

}
