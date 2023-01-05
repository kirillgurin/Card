package com.kirillgurin.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillgurin.model.Card;
import com.kirillgurin.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CardRepository {
    private Card card;
    private ArrayList<Card> cardsList;

    public Card getCard() {
        return card;
    }

    public ArrayList<Card> getCardsList() {
        return cardsList;
    }

    public CardRepository() {
    }

    @Override
    public String toString() {
        return "CardRepository{" +
                "card=" + card +
                ", cardsList=" + cardsList +
                '}';
    }

    private static InputStream getData(String link, String method) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        if (httpURLConnection.getResponseCode() == 400) {
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getErrorStream()))) {
                String error = bufferedReader.readLine();
                throw new IllegalArgumentException(error);
            }
        }
        return httpURLConnection.getInputStream();
    }

    public ArrayList<Card> getCards(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/card?id_category=" + id, "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            return this.cardsList = mapper.readValue(inputStream, new TypeReference<>() {
            });
        }
    }


    public Card addCard(String question, String answer, int idCategory) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream = getData(Constants.SERVER_URL + "/card?question="  + URLEncoder.encode(question, StandardCharsets.UTF_8) +
                "&answer=" + URLEncoder.encode(answer, StandardCharsets.UTF_8) +
                "&category_id=" + idCategory,"POST");
        ObjectMapper mapper = new ObjectMapper();
        return this.card = mapper.readValue(inputStream, Card.class);
    }

    public Card deleteCard(int id) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/card?id=" + id, "DELETE");
        ObjectMapper mapper = new ObjectMapper();
        return this.card = mapper.readValue(inputStream, Card.class);
    }

    public Card updateCard (String question, String answer, int id) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/card?question="  +
                URLEncoder.encode(question, StandardCharsets.UTF_8) +
                "&answer=" + URLEncoder.encode(answer, StandardCharsets.UTF_8) +
                "&id=" + id, "PUT");
        ObjectMapper mapper = new ObjectMapper();
        return this.card = mapper.readValue(inputStream, Card.class);
    }
}
