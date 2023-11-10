package by.karpovich.model;

public class SongEntity extends BaseEntity {

    //Many to one
    private SingerEntity singer;
//    //Many to one
//    private AlbumEntity album;
//    //One to many
//    private List<AuthorEntity> authors = new ArrayList<>();

//    public SongEntity(Long id, String name, SingerEntity singers, AlbumEntity album, List<AuthorEntity> authors) {
//        super(id, name);
//        this.singers = singers;
//        this.album = album;
//        this.authors = authors;
//    }
//
//    public SingerEntity getSingers() {
//        return singers;
//    }
//
//    public void setSingers(SingerEntity singers) {
//        this.singers = singers;
//    }
//
//    public AlbumEntity getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(AlbumEntity album) {
//        this.album = album;
//    }
//
//    public List<AuthorEntity> getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(List<AuthorEntity> authors) {
//        this.authors = authors;
//    }


    public SongEntity() {
    }

    public SongEntity(Long id, String name, SingerEntity singers) {
        super(id, name);
        this.singer = singers;
    }

    public SingerEntity getSinger() {
        return singer;
    }

    public void setSinger(SingerEntity singer) {
        this.singer = singer;
    }
}
