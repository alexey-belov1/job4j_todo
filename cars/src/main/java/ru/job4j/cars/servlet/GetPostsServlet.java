package ru.job4j.cars.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.store.HSQLStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class GetPostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        List<Post> posts = HSQLStore.instOf().findAll(Post.class);
        posts.sort(Comparator.comparing(Post::getCreated));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(posts);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}
