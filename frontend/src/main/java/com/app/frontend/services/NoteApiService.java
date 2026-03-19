package com.app.frontend.services;

import com.app.frontend.models.NoteResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class NoteApiService {
    // 1. حذفنا كلمة /api لتطابق الباكند
    private final String BASE_URL = "http://localhost:9090/notes";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<NoteResponse> getAllNotes() {
        try {
            // 2. أضفنا ?userId=1 لأن الباكند بيطلبه كـ @RequestParam
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "?userId=1"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), new TypeToken<List<NoteResponse>>(){}.getType());
            }
            return List.of();
        } catch (Exception e) {
            System.out.println("❌ Error fetching notes: " + e.getMessage());
            return List.of();
        }
    }

    public void createNote(Long userId, String title, String content) {
        try {
            // 3. الباكند بياخد الـ userId في الرابط والـ Note في الجسم (Body)
            String json = String.format("{\"title\":\"%s\", \"content\":\"%s\"}", title, content);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "?userId=" + userId)) // userId هون
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Server Response: " + response.statusCode());
        } catch (Exception e) {
            System.out.println("❌ Error saving note: " + e.getMessage());
        }
    }
}