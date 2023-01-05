package servlets;

import DAO.DAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Category;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String id = req.getParameter("id");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (name != null && id != null) {
                User user = (User) DAO.getObjectById(Long.parseLong(id), User.class);
                DAO.closeOpenedSession();
                if (user != null) {
                    Category category = new Category(name, user);
                    DAO.addObject(category);
                    String s = objectMapper.writeValueAsString(category);
                    resp.getWriter().println(s);
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can't find user with id " + id);
                }
            } else {
                resp.setStatus(400);
                resp.getWriter().println("Not all arguments intered");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            resp.getWriter().println("Incorrect format of id");
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().println("Duplicate entry for name of category " + name);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCategory = req.getParameter("id_category");
        String idUser = req.getParameter("id_user");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (idCategory != null && idUser == null) {
                Category category = (Category) DAO.getObjectById(Long.parseLong(idCategory), Category.class);
                DAO.closeOpenedSession();
                if (category != null) {
                    String s = objectMapper.writeValueAsString(category);
                    resp.getWriter().print(s);
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can not find category with id = " + idCategory);
                }
            } else if (idCategory == null && idUser != null) {
                User user = (User) DAO.getObjectById(Long.parseLong(idUser), User.class);
                if (user != null){
                    List<Category> list = user.getCategories();
                    if (list.size() == 0) {
                        resp.setStatus(400);
                        resp.getWriter().println("Can not find categories for user with id" + idUser);
                    } else {
                        String s = objectMapper.writeValueAsString(list);
                        resp.getWriter().println(s);
                        DAO.closeOpenedSession();
                    }
                }
                else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can't find user with id " + idUser);
                }
            }
            else {
                resp.setStatus(400);
                resp.getWriter().println("Enter parameters");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println("Incorrect format of id!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCategory = req.getParameter("id_category");
        String name = req.getParameter("name");
        String id = req.getParameter("id_user");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (idCategory != null && name != null && id != null){
                Category category = (Category) DAO.getObjectById(Long.parseLong(idCategory), Category.class);
                DAO.closeOpenedSession();
                if (category != null){
                    category.setName(name);
                    DAO.updateObject(category);
                    Category category1 = (Category) DAO.getObjectById(Long.parseLong(idCategory), Category.class);
                    String s = objectMapper.writeValueAsString(category1);
                    resp.getWriter().println(s);
                    DAO.closeOpenedSession();
                }
                else {
                    resp.setStatus(400);
                    resp.getWriter().println("Category with id = " + idCategory + " not found!");
                }
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println("Incorrect format of id!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id_category");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (id!= null){
                Category category = (Category) DAO.getObjectById(Long.parseLong(id), Category.class);
                DAO.closeOpenedSession();
                if (category != null){
                    DAO.deleteObject(category);
                    resp.getWriter().println(objectMapper.writeValueAsString(category));
                }
                else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can not find category with id = " + id);
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            resp.getWriter().println("Incorrect format of id!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
