package com.app.frontend.models;

public class NoteResponse {

    // 1. المتغيرات الأساسية (المعلومات اللي بتوصف الملاحظة)
    private Long id;
    private String title;
    private String content;

    // 2. الكونستراكتور الفاضي (مهم جداً عشان الجافا ما تضرب لما نربط مع الباك إند بعدين)
    public NoteResponse() {
    }

    // 3. الكونستراكتور المليان (هاد اللي استخدمناه بالسيرفر الوهمي لنصنع ملاحظات جاهزة)
    public NoteResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // 4. دوال الـ Getters (عشان الواجهات تقدر تقرأ العنوان والمحتوى)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // 5. دوال الـ Setters (عشان نقدر نعدل على الملاحظة لما آلاء تكتب بالمحرر)
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}