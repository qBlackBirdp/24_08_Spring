package com.example.demo.vo;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

	int id;
	String title;
	String body;
	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Article article = (Article) o;
//        return id == article.id; // id로 동일성 판단
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}