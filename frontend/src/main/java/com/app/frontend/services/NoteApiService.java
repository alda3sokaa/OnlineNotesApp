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
    private final String BASE_URL = "http://localhost:9090/api/notes";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<NoteResponse> getAllNotes() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), new TypeToken<List<NoteResponse>>(){}.getType());
        } catch (Exception e) {
            System.out.println("❌ Error fetching notes: " + e.getMessage());
            return List.of();
        }
    }

    // حفظ ملاحظة جديدة
    public void createNote(Long userId, String title, String content) {
        try {
            String json = String.format("{\"userId\":%d, \"title\":\"%s\", \"content\":\"%s\"}",
                    userId, title, content);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("✅ Note saved to Server!");
        } catch (Exception e) {
            System.out.println("❌ Error saving note: " + e.getMessage());
        }
    }
}