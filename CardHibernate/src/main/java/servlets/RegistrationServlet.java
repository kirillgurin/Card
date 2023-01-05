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
import java.util.Date;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        ObjectMapper objectMapper = new ObjectMapper();
        if (login != null && password != null && name != null){
            User user = new User(login, password, name, new Date());
            DAO.addObject(user);
            resp.getWriter().println(objectMapper.writeValueAsString(user));
        }
        else {
            resp.getWriter().println("Check input params");
        }
    }
}
