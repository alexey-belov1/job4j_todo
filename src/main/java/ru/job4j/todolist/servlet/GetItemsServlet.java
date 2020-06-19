package ru.job4j.todolist.servlet;

import com.google.gson.Gson;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        boolean showAll = Boolean.parseBoolean(req.getParameter("showAll"));
        List<Item> items = HSQLStore.instOf().findAll();
        if (!showAll) {
            items = items.stream().filter(x -> !x.isDone()).collect(Collectors.toList());
        }
        items.sort(Comparator.comparingInt(Item::getId).reversed());
        String json = new Gson().toJson(items);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}