package by.karpovich.model;

import java.util.ArrayList;
import java.util.List;

public class AuthorEntity {
    private Long id;
    private String authorName;
    private List<SongEntity> songs = new ArrayList<>();

    public AuthorEntity() {
    }

    public AuthorEntity(Long id, String authorName, List<SongEntity> songs) {
        this.id = id;
        this.authorName = authorName;
        this.songs = songs;
    }

    public AuthorEntity(String authorName, List<SongEntity> songs) {
        this.authorName = authorName;
        this.songs = songs;
    }

    public AuthorEntity(Long id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }



    public AuthorEntity(String authorName) {
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<SongEntity> getSongs() {
        return songs;
    }

    public void setSongs(List<SongEntity> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", songs=" + songs +
                '}';
    }
}
