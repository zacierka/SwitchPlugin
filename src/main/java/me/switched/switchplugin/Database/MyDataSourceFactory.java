package me.switched.switchplugin.Database;

import com.mysql.cj.jdbc.MysqlDataSource;
import me.switched.switchplugin.Assets;

import javax.sql.DataSource;

public class MyDataSourceFactory {

    public static DataSource getMySQLDataSource() {
        MysqlDataSource mysqlDS = null;
        mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(Assets.DB_URL);
        mysqlDS.setUser(Assets.DB_USER);
        mysqlDS.setPassword(Assets.DB_PASS);
        return mysqlDS;
    }
}