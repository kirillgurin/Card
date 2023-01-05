package com.kirillgurin.model;


import java.util.Date;
import java.util.Objects;

public class Card {
    private int id;
    private String question;
    private String answer;
    private int categoryId;

    private Date creationDate;

    public Card() {
    }

    public Card(int id, String question, String answer, int categoryId, Date creationDate) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.categoryId = categoryId;
        this.creationDate = creationDate;
    }

    public Card(String question, String answer, int categoryId, Date creationDate) {
        this.question = question;
        this.answer = answer;
        this.categoryId = categoryId;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && categoryId == card.categoryId && Objects.equals(question, card.question) && Objects.equals(answer, card.answer) && Objects.equals(creationDate, card.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, answer, categoryId, creationDate);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", categoryId=" + categoryId +
                ", creationDate=" + creationDate +
                '}';
    }
}
