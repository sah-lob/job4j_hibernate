package ru.job4j.carssale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carssale.models.Person;
import ru.job4j.carssale.persistence.CarController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    private final CarController controller = CarController.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var sb = new StringBuilder();
        try (var reader = req.getReader()) {
            if (reader != null) {
                sb.append(reader.readLine());
            }
        }
        var mapper = new ObjectMapper();
        var person = mapper.readValue(sb.toString(), Person.class);
        if (person.getDescription().equals("N")) {
            if (controller.userIsExists(person.getLogin())) {
                var s = "Данный логин уже зарегистрирован.";
                resp.getWriter().write(s);
            } else {
                controller.addPerson(person);
                req.getSession().setAttribute("login", person.getLogin());
            }
        } else {
            if (controller.validatePerson(person)) {
                req.getSession().setAttribute("login", person.getLogin());
            } else if (!controller.userIsExists(person.getLogin())) {
                var s = "Такого логина нет!";
                resp.getWriter().write(s);
            } else {
                var s = "Неправильный пароль.";
                resp.getWriter().write(s);
            }
        }
    }
}