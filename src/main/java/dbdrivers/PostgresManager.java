package dbdrivers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class PostgresManager implements DbManager {

    public static final String DB_DRIVER = "org.postgresql.Driver";

    private String password;
    private String username;

    public PostgresManager(String username, String password){
        Locale.setDefault(Locale.ENGLISH);
        setUsername(username);
        setPassword(password);
    }

    @Override
    public void setPassword(String password) {
        if (password != null)
            this.password = password;
        else throw new NullPointerException("Password equals null");
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setUsername(String username) {
        if (username != null)
            this.username = username;
        else throw new NullPointerException("Username equals null");
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Connection connect(String hostname, int port, String sid) throws SQLException {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:postgresql://" + hostname + ":" + port + "/" + sid;
        return DriverManager.getConnection(url, username, password);
    }
}
