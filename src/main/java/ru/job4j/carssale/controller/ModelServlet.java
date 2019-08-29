package ru.job4j.carssale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.carssale.persistence.CarController;
import ru.job4j.carssale.persistence.memory.MemoryCarStoreController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeMap;

public class ModelServlet extends HttpServlet {

    private final CarController controller = CarController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        var pw = resp.getWriter();
        var brand = req.getParameter("br");
        if (!brand.equals("-1")) {
            var models = controller.getModels(brand);
            var modelMap = new TreeMap<Integer, String>();
            var i = 0;
            for (var model : models) {
                modelMap.put(i++, model);
            }
            var objectMapper = new ObjectMapper();
            var arrayJson = objectMapper.createArrayNode();
            for (var entry : modelMap.entrySet()) {
                var node = objectMapper.createObjectNode();
                var arrayNode = objectMapper.createArrayNode();
                node.put("id", entry.getKey());
                var innerNode = objectMapper.createObjectNode();
                var entry2 = entry.getValue();
                innerNode.put("model2", entry2);
                arrayNode.add(innerNode);
                node.set("model", innerNode);
                arrayJson.add(node);
            }
            pw.append(objectMapper.writeValueAsString(arrayJson));
            pw.flush();
        }
    }
}
