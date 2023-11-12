package by.karpovich.model;

public class SongEntity /*extends BaseEntity*/ {

    private Long id;
    private String name;
    private SingerEntity singer;
    private AlbumEntity album;
//    //Many to many
//    private List<AuthorEntity> authors = new ArrayList<>();

    public SongEntity() {
    }

    public SongEntity(String name, SingerEntity singer) {
        this.name = name;
        this.singer = singer;
    }

    public SongEntity(Long id, String name, SingerEntity singer, AlbumEntity album) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.album = album;
    }

    public SongEntity(String name, SingerEntity singer, AlbumEntity album) {
        this.name = name;
        this.singer = singer;
        this.album = album;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SingerEntity getSinger() {
        return singer;
    }

    public void setSinger(SingerEntity singer) {
        this.singer = singer;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public SongEntity(Long id, String name, SingerEntity singer) {
        this.id = id;
        this.name = name;
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "SongEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", singer=" + singer +
                '}';
    }
}
