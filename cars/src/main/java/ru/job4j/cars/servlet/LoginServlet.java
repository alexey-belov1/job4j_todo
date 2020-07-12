package ru.job4j.cars.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        ObjectNode node = new ObjectMapper().createObjectNode();
        User user = HSQLStore.instOf().findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            req.getSession().setAttribute("user", user);
            node.put("result", true);
        } else {
            node.put("result", false);
            node.put("msg", "Неверный email или пароль");
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(node);
        writer.flush();
    }
}
