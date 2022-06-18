import me.switched.switchplugin.Database.DataSourceBroker;

public class DBTest {

    public static void main(String[] args) {
        DataSourceBroker db = new DataSourceBroker();
        db.setStatusData("ONLINE", "1.18.3", "Test MOTD", "vanilla");
    }
}
