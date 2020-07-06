package ru.job4j.cars.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cars.model.Mark;
import ru.job4j.cars.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class GetMarksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        List<Mark> marks = HSQLStore.instOf().findAll(Mark.class);
        marks.sort(Comparator.comparing(Mark::getName));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(marks);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}
