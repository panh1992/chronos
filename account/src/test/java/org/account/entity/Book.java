package org.account.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "book", type = "_doc")
public class Book {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword)
    private String postDate;

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", postDate='" + postDate + '\'' +
                '}';
    }
}
