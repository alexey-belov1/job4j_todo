package ru.job4j.cars.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cars.model.Colour;
import ru.job4j.cars.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class GetColoursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Colour> colours = HSQLStore.instOf().findAll(Colour.class);
        colours.sort(Comparator.comparing(Colour::getName));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(colours);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}
