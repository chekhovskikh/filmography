package util.dbdrivers;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbManager {

    void setPassword(String password);

    String getPassword();

    void setUsername(String username);

    String getUsername();

    Connection connect(String hostname, int port, String sid) throws SQLException;
}
