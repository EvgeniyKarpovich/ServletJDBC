package by.karpovich.model;

public class AlbumEntity {

    private Long id;
    private String albumName;
    private SingerEntity singer;

    public AlbumEntity() {
    }

    public AlbumEntity(String albumName) {
        this.albumName = albumName;
    }

    public AlbumEntity(Long id, String albumName) {
        this.id = id;
        this.albumName = albumName;
    }

    public AlbumEntity(Long id, String name, SingerEntity singer) {
        this.id = id;
        this.albumName = name;
        this.singer = singer;
    }

    public AlbumEntity(String name, SingerEntity singer) {
        this.albumName = name;
        this.singer = singer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public SingerEntity getSinger() {
        return singer;
    }

    public void setSinger(SingerEntity singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "AlbumEntity{" +
                "id=" + id +
                ", albumName='" + albumName + '\'' +
                ", singer=" + singer +
                '}';
    }
}
