package ru.job4j.cars.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.cars.model.*;
import ru.job4j.cars.store.HSQLStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AddPostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HSQLStore store = HSQLStore.instOf();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        Post post = new Post();
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String key = item.getFieldName();
                    int value = Integer.parseInt(item.getString());
                    switch (key) {
                        case "mark":
                            Mark mark = new Mark();
                            mark.setId(value);
                            post.setMark(mark);
                            break;
                        case "model":
                            Model model = new Model();
                            model.setId(value);
                            post.setModel(model);
                            break;
                        case "body":
                            Body body = new Body();
                            body.setId(value);
                            post.setBody(body);
                            break;
                        case "mileage":
                            post.setMileage(value);
                            break;
                        case "colour":
                            Colour colour = new Colour();
                            colour.setId(value);
                            post.setColour(colour);
                            break;
                        default:
                            break;
                    }
                } else {
                    Photo photo = new Photo();
                    photo.setName(item.getName());
                    store.persist(photo);
                    post.setPhoto(photo);
                    File folderId = new File("images" + File.separator + photo.getId());
                    if (folderId.exists()) {
                        for (File f : folderId.listFiles()) {
                            f.delete();
                        }
                    } else {
                        folderId.mkdir();
                    }
                    try (FileOutputStream out = new FileOutputStream(new File(folderId + File.separator + item.getName()))) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        post.setUser((User) req.getSession().getAttribute("user"));
        post.setCreated(LocalDateTime.now());
        post.setStatus(true);
        store.persist(post);
    }
}
