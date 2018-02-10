package entities.filters;

public class GenreFilter implements EntityFilter {
    private String name;
    private String parentName;

    public GenreFilter() {
    }

    public GenreFilter(String name) {
        this.name = name;
    }

    public GenreFilter(int id, String name, String parentName) {
        this.name = name;
        this.parentName = parentName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String toString() {
        return name;
    }
}
