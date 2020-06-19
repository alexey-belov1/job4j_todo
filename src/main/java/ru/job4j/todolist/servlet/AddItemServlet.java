package ru.job4j.todolist.servlet;

import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String desc = req.getParameter("desc");
        Item item = new Item(desc);
        item.setCreated(LocalDateTime.now());
        HSQLStore.instOf().create(item);
    }
}