package by.karpovich.model;

public class BaseEntity {

    private Long id;
    private String name;

    public BaseEntity() {
    }

    public BaseEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseEntity(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", surname='" + name + '\'' +
                '}';
    }
}
