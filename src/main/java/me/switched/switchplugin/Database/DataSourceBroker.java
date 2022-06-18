package me.switched.switchplugin.Database;
import javax.sql.DataSource;
import java.sql.*;

public class DataSourceBroker {

    private DataSource db = null;


    public DataSourceBroker() {
        this.db = MyDataSourceFactory.getMySQLDataSource();
    }

    public void setStatusData(String status, String version, String motd, String type) {
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = db.getConnection();
            stmt = con.prepareCall("{CALL `minecraft`.`insert_update_status`(?, ?, ?, ?)}");
            stmt.setString(1, status);
            stmt.setString(2, version);
            stmt.setString(3, motd);
            stmt.setString(4, type);
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void close() {
        try {
            db.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
