package com.app.backend.models;

import jakarta.persistence.*;//boot3 ise boot2 için javax

@Entity //veritabanında tablo olacak  [
public class NoteShare {
	@Id //tablonun her satırı id num. alcak anahtarı olcak
	@GeneratedValue(strategy=GenerationType.IDENTITY) //id'yi veritabanı otomatik üretsin (her yeni kayıtta 1 artarak üretir)
private Long id;
private Long noteId;
private Long userId;
@Enumerated(EnumType.STRING) //enumu nasıl yazacağını belirtir (yazı olarak kaydetsin)//olmazsa sayı olarak kaydeder
private ShareRole  role;

public Long getId() {
	return id;
}
public Long getNoteId() {
	return noteId;
}
public void setNoteId(Long noteId) {
	this.noteId=noteId;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId=userId;
}
public ShareRole getRole() {
	return role;
}
public void setRole(ShareRole role) {
	this.role=role;
}
}