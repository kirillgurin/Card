package com.kirillgurin.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillgurin.model.User;
import com.kirillgurin.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserRepository {
    private User user;
    private ArrayList<User> usersList;

    public User getUser() {
        return user;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
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

    public User getUserById(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/user?id=" + id, "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            return this.user = mapper.readValue(inputStream, User.class);
        }
    }

    public User addUser(String login, String password, String name) throws IOException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream =
                getData(Constants.SERVER_URL + "/register?" +
                "login=" + URLEncoder.encode(login, StandardCharsets.UTF_8) +
                "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) +
                "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8), "POST");
        ObjectMapper mapper = new ObjectMapper();
        return this.user = mapper.readValue(inputStream, User.class);
    }

    public User checkUser(String login, String password) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/user?" +
                "login=" + URLEncoder.encode(login, StandardCharsets.UTF_8) +
                "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8), "POST");
        ObjectMapper mapper = new ObjectMapper();
        return this.user = mapper.readValue(inputStream, User.class);
    }


    public User deleteUser(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/user?id=" + id, "DELETE")) {
            ObjectMapper mapper = new ObjectMapper();
            return this.user = mapper.readValue(inputStream, User.class);
        }
    }

}
