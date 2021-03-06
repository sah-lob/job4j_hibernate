package ru.job4j.carssale.persistence.memory;

import ru.job4j.carssale.models.Car;
import ru.job4j.carssale.models.CarFilter;
import ru.job4j.carssale.models.Image;
import ru.job4j.carssale.models.Person;
import ru.job4j.carssale.persistence.interfaces.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryCarStoreController implements CarStoreController {

    private final static MemoryCarStoreController MEMORY_CAR_STORE_CONTROLLER = new MemoryCarStoreController();

    public static MemoryCarStoreController getInstance() {
        return MEMORY_CAR_STORE_CONTROLLER;
    }


    private BrandStore brandStore = MemoryBrandStore.getInstance();
    private CarsStore carsStore = MemoryCarsStore.getInstance();
    private ImageStore imageStore = MemoryImageStore.getInstance();
    private UsersStore usersStore = MemoryUsersStore.getInstance();
    private AtomicInteger id = new AtomicInteger(0);


    @Override
    public void addData(Car car, String img) {
        car.setId(id.getAndIncrement());
        addCar(car);
        addImg(new Image(car.getId(), img));
        addBrand(car.getBrand(), car.getModel());
    }

    // BrandStore

    @Override
    public List<String> getBrands() {
        return brandStore.getBrands();
    }

    @Override
    public List<String> getModels(String brand) {
        return brandStore.getModels(brand);
    }

    @Override
    public void addBrand(String brand, String model) {
        brandStore.add(brand, model);
    }

    //ImageStore

    @Override
    public int imageSize() {
        return imageStore.size();
    }

    @Override
    public String getImg(int id) {
        return imageStore.getImg(id);
    }

    @Override
    public void addImg(Image image) {
        imageStore.addImg(image);
    }

    //CarsStore

    @Override
    public void addCar(Car car) {
        carsStore.add(car);
    }

    @Override
    public Car findCar(int page, int id) {
        return carsStore.findCar(page, id);
    }

    @Override
    public int carSize() {
        return carsStore.size();
    }

    @Override
    public List<Car> carsFindToPage(int page) {
        return carsStore.findToPage(page);
    }

    @Override
    public List<Car> carsParamFindPage(CarFilter carFilter) {
        return carsStore.paramFindPage(carFilter);
    }

    @Override
     public int carsGetParamFindPageSize(CarFilter carFilter) {
        return carsStore.getParamFindPageSize(carFilter);
     }

    //UsersStore

    @Override
    public boolean userIsExists(String login) {
        return usersStore.isExists(login);
    }

    @Override
    public void addPerson(Person person) {
        usersStore.add(person);
    }

    @Override
    public boolean validatePerson(Person person) {
        return usersStore.validatePerson(person);
    }

    @Override
    public Person getPerson(String login) {
        return usersStore.getPerson(login);
    }

    @Override
    public boolean editPerson(String login, String fio, String number) {
        return usersStore.editPerson(login, fio, number);
    }
}
