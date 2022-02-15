package com.forgottenartsstudios.data;

import com.forgottenartsstudios.complicatedsimple.SimpleGame;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import database.ManagedDatabase;

public class CSData {

    private static Connection connection;
    private static ManagedDatabase db;

    private static String defaulturl;
    private static String defaultname;
    private static String defaultpass;

    public static Connection conn() {
        Properties prop  = new Properties();
        InputStream is = null;
        try {
            is = SimpleGame.class.getClassLoader().getResourceAsStream("config.properties");
            if (!Objects.isNull(is)) {
                prop.load(is);
                defaulturl = prop.getProperty("defaultdburl");
                defaultname = prop.getProperty("defaultdbname");
                defaultpass = prop.getProperty("defaultdbpass");
            }else{
                setDefaulturl(System.getenv().get("defaultdburl"));
                setDefaultname(System.getenv().get("defaultdbname"));
                setDefaultpass(System.getenv().get("defaultdbpass"));
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (connection != null) {
            return connection;
        }
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(getDefaulturl(), getDefaultname(), getDefaultpass());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static ManagedDatabase db() throws IOException, SQLException {
        if (db == null) {
            db = new ManagedDatabase(conn());
        }
        return db;
    }
    public static String getDefaulturl() {
        return defaulturl;
    }

    public static void setDefaulturl(String defaulturl) {
        CSData.defaulturl = defaulturl;
    }

    public static String getDefaultname() {
        return defaultname;
    }

    public static void setDefaultname(String defaultname) {
        CSData.defaultname = defaultname;
    }

    public static String getDefaultpass() {
        return defaultpass;
    }

    public static void setDefaultpass(String defaultpass) {
        CSData.defaultpass = defaultpass;
    }


}
