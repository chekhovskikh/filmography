package entitiy;

public class Genre implements Entity {
    private int genreId;
    private String genreName;
    private int parentId;

    public Genre() {
    }

    public Genre(int genreId) {
        this.genreId = genreId;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public Genre(String genreName, int parentId) {
        this.genreName = genreName;
        this.parentId = parentId;
    }

    public Genre(int genreId, String genreName, int parentId) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.parentId = parentId;
    }

    public int getId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setId(int genreId) {
        this.genreId = genreId;
    }
}
