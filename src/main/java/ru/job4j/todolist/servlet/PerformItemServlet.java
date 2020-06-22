package ru.job4j.todolist.servlet;

import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class PerformItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        HSQLStore store = HSQLStore.instOf();
        Optional.ofNullable(store.findById(Item.class, id)).ifPresent(item -> {
            item.setDone(true);
            store.update(item);
        });
    }
}
