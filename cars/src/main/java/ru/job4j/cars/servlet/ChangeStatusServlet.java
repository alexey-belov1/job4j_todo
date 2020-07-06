package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean check = Boolean.parseBoolean(req.getParameter("check"));
        Post post = HSQLStore.instOf().findById(Post.class, id);
        post.setStatus(check);
        HSQLStore.instOf().update(post);
    }
}
