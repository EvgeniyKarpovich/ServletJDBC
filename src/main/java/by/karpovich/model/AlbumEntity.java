package by.karpovich.model;

import java.util.ArrayList;
import java.util.List;

public class AlbumEntity {
    //One to many
    private List<SongEntity> songs = new ArrayList<>();
    //Many to one
    private SingerEntity singer;

    public AlbumEntity(List<SongEntity> songs, SingerEntity singer) {
        this.songs = songs;
        this.singer = singer;
    }

    public List<SongEntity> getSongs() {
        return songs;
    }

    public void setSongs(List<SongEntity> songs) {
        this.songs = songs;
    }

    public SingerEntity getSinger() {
        return singer;
    }

    public void setSinger(SingerEntity singer) {
        this.singer = singer;
    }
}
