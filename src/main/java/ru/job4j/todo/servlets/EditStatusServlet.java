package ru.job4j.todo.servlets;

import ru.job4j.todo.persistent.DBStore;
import ru.job4j.todo.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditStatusServlet extends HttpServlet {

    private final Store store = DBStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Integer.parseInt(req.getParameter("itemId"));
        var done = Boolean.valueOf(req.getParameter("itemStatus"));
        store.resetStatus(id, done);
    }
}
