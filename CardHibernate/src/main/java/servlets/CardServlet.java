package servlets;

import DAO.DAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Card;
import model.Category;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/card")
public class CardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCategory = req.getParameter("id_category");
        String idUser = req.getParameter("id_user");
        String idCard = req.getParameter("id_card");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (idCategory == null && idUser == null && idCard != null) {
                Card card = (Card) DAO.getObjectById(Long.parseLong(idCard), Card.class);
                DAO.closeOpenedSession();
                if (card != null) {
                    String s = objectMapper.writeValueAsString(card);
                    resp.getWriter().print(s);
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can not find card with id = " + idCard);
                }
            } else if (idCategory != null && idUser == null && idCard == null) {
                Category category = (Category) DAO.getObjectById(Long.parseLong(idCategory), Category.class);
                if (category != null){
                    List<Card> list = category.getCards();
                    if (list.size() == 0) {
                        resp.setStatus(400);
                        resp.getWriter().println("Can not find cards with id Category = " + idCategory);
                    } else {
                        String s = objectMapper.writeValueAsString(list);
                        resp.getWriter().println(s);
                        DAO.closeOpenedSession();
                    }
                }
                else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can't find category " + idCategory);
                }
            } else if (idCategory == null && idUser != null && idCard == null) {
                User user = (User) DAO.getObjectById(Long.parseLong(idUser), User.class);
                if (user != null){
                    List<Category> list = user.getCategories();
                    List<Card> cards = new ArrayList<>();
                    for (Category a : list) {
                        if (a.getCards() != null){
                            cards.addAll(a.getCards());
                        }
                    }
                    String s = objectMapper.writeValueAsString(cards);
                    resp.getWriter().println(s);
                    DAO.closeOpenedSession();
                }
                else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can not find user with id = " + idUser);
                }
            }
            else {
                resp.setStatus(400);
                resp.getWriter().println("Not all parameters added");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println("Incorrect format of id!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String question = req.getParameter("question");
            String answer = req.getParameter("answer");
            String categoryId = req.getParameter("category_id");
            if (question != null && answer != null && categoryId != null) {
                Category category = (Category) DAO.getObjectById(Long.parseLong(categoryId), Category.class);
                DAO.closeOpenedSession();
                if (category != null) {
                    Card card = new Card(question, answer, new Date(), category);
                    DAO.addObject(card);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String s = objectMapper.writeValueAsString(card);
                    resp.getWriter().println(s);
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("Category with id =" + categoryId + " does not exist!");
                }
            }
            else {
                resp.setStatus(400);
                resp.getWriter().println("Not all parameters added");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            resp.getWriter().println("Incorrect input format");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e){
            resp.setStatus(400);
            resp.getWriter().println("Entity already exists");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String question = req.getParameter("question");
            String answer = req.getParameter("answer");
            String id = req.getParameter("id");
            ObjectMapper objectMapper = new ObjectMapper();
            if (question != null && answer != null && id != null) {
                Card card = (Card) DAO.getObjectById(Long.parseLong(id), Card.class);
                DAO.closeOpenedSession();
                if (card != null) {
                    card.setQuestion(question);
                    card.setAnswer(answer);
                    DAO.updateObject(card);
                    Card card1 = (Card) DAO.getObjectById(Long.parseLong(id), Card.class);
                    DAO.closeOpenedSession();
                    String s = objectMapper.writeValueAsString(card1);
                    resp.getWriter().println(s);
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("There is no card with id= " + id);
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            resp.getWriter().println("Incorrect input format");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (id != null) {
                Card card = (Card) DAO.getObjectById(Long.parseLong(id), Card.class);
                DAO.closeOpenedSession();
                if (card != null) {
                    DAO.deleteObject(card);
                    resp.getWriter().println(objectMapper.writeValueAsString(card));
                } else {
                    resp.setStatus(400);
                    resp.getWriter().println("Can not find card with id = " + id);
                }
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println("Incorrect format of id!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
