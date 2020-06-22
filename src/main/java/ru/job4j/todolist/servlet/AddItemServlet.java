package ru.job4j.todolist.servlet;

import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Item item = new Item(req.getParameter("desc"));
        item.setCreated(LocalDateTime.now());
        item.setUser((User) req.getSession().getAttribute("user"));
        HSQLStore.instOf().create(item);
    }
}