package ru.job4j.carssale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carssale.models.Car;
import ru.job4j.carssale.models.CarFilter;
import ru.job4j.carssale.persistence.CarController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonServlet extends HttpServlet {

    private final CarController controller = CarController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        var pw = resp.getWriter();
        var page = Integer.valueOf(req.getParameter("page"));
        var carId = req.getParameter("carID");
        boolean filterCarID = (carId.equals("undefined") || carId.equals(""));
        //  Если нужен тестовый лист из машин.
        //  controller.testList(22);
        List<Car> cars;
        var newID = -2;
        CarFilter carFilter = null;
        if (!filterCarID) {
            List<String> params = new ArrayList<>();
            params.add(req.getParameter("page"));
            params.add(req.getParameter("carID"));
            params.add(req.getParameter("brand"));
            params.add(req.getParameter("model"));
            params.add(req.getParameter("priceFrom"));
            params.add(req.getParameter("priceTo"));
            params.add(req.getParameter("korobka"));
            params.add(req.getParameter("powerFrom"));
            params.add(req.getParameter("powerTo"));
            params.add(req.getParameter("yearFrom"));
            params.add(req.getParameter("yearTo"));


            carFilter = new CarFilter(params);
            cars = controller.carsParamFindPage(carFilter);

            if (!cars.isEmpty()) {
                newID = cars.get(cars.size() - 1).getId();
            }
        } else {
            cars = controller.carsFindToPage(page);
        }

        var objectMapper = new ObjectMapper();
        var arrayJson = objectMapper.createArrayNode();
        for (var entry : cars) {
            var node = objectMapper.createObjectNode();
            var arrayNode = objectMapper.createArrayNode();
            node.put("id", entry.getId());
                var innerNode = objectMapper.createObjectNode();
                var entry2 = entry;
                innerNode.put("id", entry2.getId());
                innerNode.put("brand", entry2.getBrand());
                innerNode.put("model", entry2.getModel());
                innerNode.put("price", entry2.getPrice());
                innerNode.put("mechanicGear", entry2.isMechanicGear());
                innerNode.put("power", entry2.getPower());
                innerNode.put("year", entry2.getYear());
                arrayNode.add(innerNode);
            node.set("car", innerNode);
            if (!filterCarID) {
                node.put("len", controller.carsGetParamFindPageSize(carFilter));
            } else {
                node.put("len", controller.carSize());
            }
            node.put("newId", newID);
            arrayJson.add(node);
        }
        pw.append(objectMapper.writeValueAsString(arrayJson));
        pw.flush();
    }
}
