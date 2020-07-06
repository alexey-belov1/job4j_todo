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

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        ObjectNode node = new ObjectMapper().createObjectNode();
        HSQLStore store = HSQLStore.instOf();
        if (store.findUserByEmail(email) != null) {
            node.put("result", false);
            node.put("msg", "Данный email уже занят");
        } else {
            store.persist(new User(name, email, password));
            node.put("result", true);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(node);
        writer.flush();
    }
}