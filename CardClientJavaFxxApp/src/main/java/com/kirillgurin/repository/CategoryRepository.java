package com.kirillgurin.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillgurin.model.Category;
import com.kirillgurin.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CategoryRepository {
    private Category category;
    private ArrayList<Category> categoriesList;

    public Category getCategory() {
        return category;
    }

    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }

    public CategoryRepository() {
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

    public Category getCategoryById(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/category?id_category=" + id, "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            return this.category = mapper.readValue(inputStream, Category.class);
        }
    }

    public ArrayList<Category> getCategoryByIdUser(int id) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/category?id_user=" + id, "GET");
        ObjectMapper mapper = new ObjectMapper();
        return this.categoriesList = mapper.readValue(inputStream, new TypeReference<>() {
        });
    }

    public Category addCategory(String name, int id) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/category?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) +
                "&id=" + id, "POST");
        ObjectMapper mapper = new ObjectMapper();
        return this.category = mapper.readValue(inputStream, Category.class);
    }

    public Category deleteCategory(int id) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/category?id=" + id, "DELETE");
        ObjectMapper mapper = new ObjectMapper();
        return this.category = mapper.readValue(inputStream, Category.class);
    }

    public Category updateCategory(int idCategory, String name, int id) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/category?idCategory=" + idCategory +
                "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) +
                "&id=" + id, "PUT");
        ObjectMapper mapper = new ObjectMapper();
        return this.category = mapper.readValue(inputStream, Category.class);
    }
}
