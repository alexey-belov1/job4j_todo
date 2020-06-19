package ru.job4j.todolist.servlet;

import ru.job4j.todolist.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        HSQLStore.instOf().delete(id);
    }
}