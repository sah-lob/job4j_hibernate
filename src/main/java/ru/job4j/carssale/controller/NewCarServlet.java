package ru.job4j.carssale.controller;

import ru.job4j.carssale.models.Car;
import ru.job4j.carssale.models.Person;
import ru.job4j.carssale.persistence.CarController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class NewCarServlet extends HttpServlet {

    private final CarController controller = CarController.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        var image = req.getParameter("string");
        var brand = req.getParameter("brand");
        var model = req.getParameter("model");
        var price = req.getParameter("price");
        var korobka = req.getParameter("korobka");
        var power = req.getParameter("power");
        var year = req.getParameter("year");
        var fio = req.getParameter("fio");
        var phone = req.getParameter("phone");
        image = image.replaceAll(" ", "+");
        var login = req.getSession().getAttribute("login");
        Person person = controller.getPerson(login.toString());
        controller.editPerson(login.toString(), fio, phone);
        var car = new Car(brand, model, price, korobka, power, year, login.toString());
//        controller.addPerson(person);
        controller.addData(car, image);
    }

}
