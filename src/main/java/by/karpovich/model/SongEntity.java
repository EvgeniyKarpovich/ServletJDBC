package by.karpovich.model;

public class SongEntity /*extends BaseEntity*/ {

    //Many to one
    private Long id;
    private String name;
    private SingerEntity singer;
//    //Many to one
//    private AlbumEntity album;
//    //One to many
//    private List<AuthorEntity> authors = new ArrayList<>();

    public SongEntity() {
    }

    public SongEntity(String name, SingerEntity singer) {
        this.name = name;
        this.singer = singer;
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

    public SongEntity(Long id, String name, SingerEntity singer) {
        this.id = id;
        this.name = name;
        this.singer = singer;
    }

    //    public SongEntity(Long id, String name, SingerEntity singers) {
//        super(id, name);
//        this.singer = singers;
//    }
//
//    public SongEntity(String name, SingerEntity singers) {
//        super(name);
//        this.singer = singers;
//    }
//
//    public SingerEntity getSinger() {
//        return singer;
//    }
//
//    public void setSinger(SingerEntity singer) {
//        this.singer = singer;
//    }
}
