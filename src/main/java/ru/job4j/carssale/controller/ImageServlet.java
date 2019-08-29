package ru.job4j.carssale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carssale.persistence.CarController;
import ru.job4j.carssale.persistence.memory.MemoryCarStoreController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImageServlet extends HttpServlet {

    private final CarController controller = CarController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        var pw = resp.getWriter();
        var carId = req.getParameter("id");
        var image = controller.getImg(Integer.parseInt(carId));
        var objectMapper = new ObjectMapper();
        var arrayJson = objectMapper.createArrayNode();
        var node = objectMapper.createObjectNode();
        var arrayNode = objectMapper.createArrayNode();
        node.put("id", image);
        var innerNode = objectMapper.createObjectNode();
        arrayNode.add(innerNode);
        arrayJson.add(node);
        pw.append(objectMapper.writeValueAsString(arrayJson));
        pw.flush();
    }
}
