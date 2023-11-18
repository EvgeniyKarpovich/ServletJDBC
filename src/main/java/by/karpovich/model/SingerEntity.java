package by.karpovich.model;

import java.util.ArrayList;
import java.util.List;

public class SingerEntity {

    private Long id;
    private String surname;
    private List<AlbumEntity> albums = new ArrayList<>();

    public SingerEntity(Long id) {
        this.id = id;
    }

    public SingerEntity(Long id, String name) {
        this.id = id;
        this.surname = name;
    }

    public SingerEntity(Long id, String surname, List<AlbumEntity> albums) {
        this.id = id;
        this.surname = surname;
        this.albums = albums;
    }

    public SingerEntity(String surname, List<AlbumEntity> albums) {
        this.surname = surname;
        this.albums = albums;
    }

    public SingerEntity(String name) {
        this.surname = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumEntity> albums) {
        this.albums = albums;
    }

    public SingerEntity() {
    }

    @Override
    public String toString() {
        return "SingerEntity{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", albums=" + albums +
                '}';
    }
}

