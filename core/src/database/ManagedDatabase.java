package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ManagedDatabase {

    Connection conn;

    public ManagedDatabase(Connection conn) throws IOException, SQLException {
        this.conn = conn;
    }
}
