package ru.job4j.todolist.servlet;

import com.google.gson.JsonObject;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        JsonObject json = new JsonObject();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HSQLStore store = HSQLStore.instOf();
        if (store.findUserByEmail(email) != null) {
            json.addProperty("result", false);
            json.addProperty("msg", "Данный email уже занят");
        } else {
            store.create(new User(name, email, password));
            json.addProperty("result", true);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}