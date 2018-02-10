package entities;

public class Genre implements Entity {
    private int id;
    private String name;
    private int parentId;

    public Genre() {
    }

    public Genre(int id) {
        this.id = id;
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name, int parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public Genre(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String toString() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
