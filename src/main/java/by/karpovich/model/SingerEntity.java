package by.karpovich.model;

import java.util.List;

public class SingerEntity {

    //one to many
    private List<SongEntity> songs;

    public SingerEntity(List<SongEntity> songs) {
        this.songs = songs;
    }

    public List<SongEntity> getSongs() {
        return songs;
    }

    public void setSongs(List<SongEntity> songs) {
        this.songs = songs;
    }
}

