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

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        JsonObject json = new JsonObject();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = HSQLStore.instOf().findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            req.getSession().setAttribute("user", user);
            json.addProperty("result", true);
        } else {
            json.addProperty("msg", "Неверный email или пароль");
            json.addProperty("result", false);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}