package by.karpovich.model;

public class SingerEntity {

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


    public SingerEntity() {
    }

    @Override
    public String toString() {
        return "SingerEntity{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                '}';
    }
}

