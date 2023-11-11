package by.karpovich.model;

public class SingerEntity /*extends BaseEntity*/ {

    private Long id;
    private String surname;

    public SingerEntity(Long id, String name) {
        this.id = id;
        this.surname = name;
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

    //    public SingerEntity(Long id, String name) {
//        super(id, name);
//    }

    public SingerEntity() {
    }

    //    List<SongEntity> songs = new ArrayList<>();
//
//    public SingerEntity(Long id, String name, List<SongEntity> songs) {
//        super(id, name);
//        this.songs = songs;
//    }
//
//    public List<SongEntity> getSongs() {
//        return songs;
//    }
//
//    public void setSongs(List<SongEntity> songs) {
//        this.songs = songs;
//    }


}

