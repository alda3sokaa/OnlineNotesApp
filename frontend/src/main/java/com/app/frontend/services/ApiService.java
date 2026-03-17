package com.app.frontend.services;

import com.app.frontend.models.User;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiService {

    // رابط سيرفر الـ Spring Boot تبعنا
    private static final String BASE_URL = "http://localhost:9090";

    private final HttpClient httpClient;
    private final Gson gson; // ضفنا الـ Gson هون عشان نستخدمه بكل الدوال

    public ApiService() {
        // تجهيز العميل مع وقت انتظار (Timeout) 10 ثواني
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // تهيئة الـ Gson مرة وحدة
        this.gson = new Gson();
    }

    /**
     * دالة تسجيل الدخول
     * تتواصل مع الباكند وترجع كائن User جاهز للاستخدام في الفرونتند
     */
    public User login(String email, String password) throws Exception {
        // بناء الرابط
        String urlString = BASE_URL + "/api/users/login?email=" + email + "&password=" + password;

        // تجهيز الطلب (POST)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("Accept", "application/json")
                .build();

        // إرسال الطلب واستقبال الرد من السيرفر
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // فحص حالة الرد
        if (response.statusCode() == 200) {
            // تحويل الـ JSON اللي رجع من السيرفر إلى كائن User باستخدام Gson
            return gson.fromJson(response.body(), User.class);
        } else {
            // إذا الإيميل أو الباسورد غلط، أو في مشكلة بالسيرفر
            throw new RuntimeException("Login failed. Server responded with status: " + response.statusCode());
        }
    }
    public User register(String username, String email, String password) throws Exception {
        // بناء الرابط لإنشاء يوزر جديد
        String urlString = BASE_URL + "/api/users";

        // تجهيز بيانات اليوزر كـ JSON
        String jsonBody = String.format("{\"username\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
                username, email, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            return gson.fromJson(response.body(), User.class);
        } else {
            throw new RuntimeException("Registration failed: " + response.body());
        }
    }
}