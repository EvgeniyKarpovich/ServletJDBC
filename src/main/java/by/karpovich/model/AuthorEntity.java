package by.karpovich.model;

import java.util.ArrayList;
import java.util.List;

public class AuthorEntity {
    //Many to many
    private List<SongEntity> songs = new ArrayList<>();

    public AuthorEntity(List<SongEntity> songs) {
        this.songs = songs;
    }

    public List<SongEntity> getSongs() {
        return songs;
    }

    public void setSongs(List<SongEntity> songs) {
        this.songs = songs;
    }
}
