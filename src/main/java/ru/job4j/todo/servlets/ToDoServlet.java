package ru.job4j.todo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.todo.persistent.DBStore;
import ru.job4j.todo.models.Item;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ToDoServlet extends HttpServlet {

    private DBStore store = DBStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var list = store.findAll();
        var objectMapper = new ObjectMapper();
        var toJson = objectMapper.writeValueAsString(list);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        var writer = new PrintWriter(resp.getOutputStream());
        writer.print(toJson);
        writer.flush();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        var desc = req.getParameter("description");
        var item = new Item(desc, new Date(System.currentTimeMillis()), false);
        store.add(item);
        resp.sendRedirect("index.html");
    }
}
