package program;

import DAO.DAO;
import model.Card;
import model.Category;
import model.User;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        /*User user = new User("Kir", "Gurin", "Kirill", new Date());
        DAO.addObject(user);
        System.out.println(user);*/

        User user = (User) DAO.getObjectById(1L, User.class);
        DAO.closeOpenedSession();

        /*User user1 = new User("mih", "Mihail", "Golubev", new Date());
        DAO.addObject(user1);
        Category category = new Category("c", user1);
        DAO.addObject(category);
        Card card = new Card("hz", "54321", new Date(), category);
        DAO.addObject(card);*/

        User user1 = (User) DAO.getObjectById(5L, User.class);
        DAO.closeOpenedSession();
        Category category = (Category) DAO.getObjectById(3L, Category.class);
        DAO.closeOpenedSession();
        Card card = (Card) DAO.getObjectById(3L, Card.class);
        DAO.closeOpenedSession();

        user1.setName("Oleg");
        user1.setLogin("Ol");
        category.setName("Abvgd");
        category.setUser(user1);
        card.setAnswer("???");
        card.setQuestion("!!!");
        DAO.updateObject(user1);
        DAO.updateObject(category);
        DAO.updateObject(card);



    }
}
