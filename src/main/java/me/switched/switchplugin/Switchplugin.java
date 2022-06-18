package me.switched.switchplugin;

import com.rabbitmq.client.*;
import me.switched.switchplugin.Command.Command;
import me.switched.switchplugin.Command.CommandHandler;
import me.switched.switchplugin.Command.OnlineCommand;
import me.switched.switchplugin.Database.DataSourceBroker;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public final class Switchplugin extends JavaPlugin {

    public static Logger LOGGER;
    public static Server PLUGIN;
    private Channel channel = null;
    private String MC_QUEUE = "MC_QUEUE";

    private java.sql.Connection conn = null;
    private CommandHandler commandHandler;

    private DataSourceBroker db = null;
    @Override
    public void onEnable() {

        LOGGER = this.getLogger();
        PLUGIN = this.getServer();
        this.commandHandler = new CommandHandler();
        commandHandler.addCommand(new OnlineCommand());

        db = new DataSourceBroker();
        db.setStatusData("ONLINE", getServer().getMinecraftVersion(), getServer().getMotd(), "vanilla");
        // Plugin startup logic
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.12");
        factory.setUsername("bot");
        factory.setPassword("Switched");
        factory.setPort(5672);

        Connection connection = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(MC_QUEUE, false, false, false, null);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {

                String message = new String(body, StandardCharsets.UTF_8);

                LOGGER.info("Received Message: "+message);
                commandHandler.run(message);
            }
        };
        try {
            channel.basicConsume(MC_QUEUE, true, consumer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        db.setStatusData("OFFLINE", getServer().getMinecraftVersion(), getServer().getMotd(), "vanilla");
        try {
            channel.close();
            db.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        // Plugin shutdown logic
    }
}
