package ru.job4j.cars.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.FilterDB;
import ru.job4j.cars.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetPostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FilterDB> filters = new ArrayList<>();

        if (Boolean.parseBoolean(req.getParameter("withPhoto"))) {
            filters.add(new FilterDB("withPhoto"));
        }

        if (Boolean.parseBoolean(req.getParameter("onlyToday"))) {
            filters.add(new FilterDB("onlyToday"));
        }

        int id = Integer.parseInt(req.getParameter("markId"));
        if (id != -1) {
            FilterDB filter = new FilterDB("withMarkId");
            filter.setParam("mark_id", id);
            filters.add(filter);
        }

        List<Post> posts = HSQLStore.instOf().findWithFilter(Post.class, filters);
        posts.sort(Comparator.comparing(Post::getCreated));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(posts);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}
