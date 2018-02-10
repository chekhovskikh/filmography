package entitiy.filter;

public class GenreFilter implements EntityFilter {
    private String genreName;
    private String parentName;

    public GenreFilter() {
    }

    public GenreFilter(String genreName) {
        this.genreName = genreName;
    }

    public GenreFilter(int id, String genreName, String parentName) {
        this.genreName = genreName;
        this.parentName = parentName;
    }


    public String getName() {
        return genreName;
    }

    public void setName(String genreName) {
        this.genreName = genreName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String toString() {
        return genreName;
    }
}
