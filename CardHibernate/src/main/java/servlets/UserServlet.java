package servlets;

import DAO.DAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        ObjectMapper objectMapper = new ObjectMapper();
        if (login != null && password != null) {
            try {
                User user = (User) DAO.getObjectByParams(new String[]{"login", "password"}, new Object[]{login, password}, User.class);
                if (user != null) {
                    String s = objectMapper.writeValueAsString(user);
                    DAO.closeOpenedSession();
                    resp.getWriter().println(s);
                }
                else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can't find user!");
                }
            } catch (Exception e) {
                resp.setStatus(400);
                resp.getWriter().println("Can't find user!");
            }
        } else {
            resp.setStatus(400);
            resp.getWriter().println("Enter user parameters!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ObjectMapper objectMapper = new ObjectMapper();
        if (id != null) {
            try {
                User user = (User) DAO.getObjectById(Long.parseLong(id), User.class);
                if (user != null) {
                    String s = objectMapper.writeValueAsString(user);
                    DAO.closeOpenedSession();
                    DAO.deleteObjectById(Long.parseLong(id), User.class);
                    resp.getWriter().println(s);
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can't find user with id " + id);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(400);
                resp.getWriter().println("Incorrect format of id!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.setStatus(400);
            resp.getWriter().println("Enter user parameters!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ObjectMapper objectMapper = new ObjectMapper();
        if (id != null) {
            try {
                User user = (User) DAO.getObjectById(Long.parseLong(id), User.class);
                if (user != null) {
                    String s = objectMapper.writeValueAsString(user);
                    DAO.closeOpenedSession();
                    resp.getWriter().println(s);
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can't find user with id " + id);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(400);
                resp.getWriter().println("Incorrect format of id!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.setStatus(400);
            resp.getWriter().println("Enter user parameters!");
        }
    }
}

