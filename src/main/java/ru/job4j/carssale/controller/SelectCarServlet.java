package ru.job4j.carssale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carssale.persistence.CarController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SelectCarServlet extends HttpServlet {

    private final CarController controller = CarController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        int page = Integer.parseInt(req.getParameter("page"));
        int id = Integer.parseInt(req.getParameter("id"));
        var car = controller.findCar(page, id);
        var pw = resp.getWriter();
        var person = controller.getPerson(car.getOwner());
        System.out.println(person.getLogin());
        System.out.println(person.getPhone());
        var objectMapper = new ObjectMapper();
        var arrayJson = objectMapper.createArrayNode();
        var node = objectMapper.createObjectNode();
        var arrayNode = objectMapper.createArrayNode();
        node.put("id", 0);
        var innerNode = objectMapper.createObjectNode();
        innerNode.put("id", car.getId());
        innerNode.put("brand", car.getBrand());
        innerNode.put("model", car.getModel());
        innerNode.put("price", car.getPrice());
        innerNode.put("mechanicGear", car.isMechanicGear());
        innerNode.put("power", car.getPower());
        innerNode.put("year", car.getYear());
        innerNode.put("owner", person.getFio());
        innerNode.put("phone", person.getPhone());
        arrayNode.add(innerNode);
        node.set("car", innerNode);
        arrayJson.add(node);
        pw.append(objectMapper.writeValueAsString(arrayJson));
        pw.flush();
    }
}
