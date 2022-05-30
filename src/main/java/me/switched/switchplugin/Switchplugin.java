package me.switched.switchplugin;

import com.rabbitmq.client.*;
import me.switched.switchplugin.Command.Command;
import me.switched.switchplugin.Command.CommandHandler;
import me.switched.switchplugin.Command.OnlineCommand;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public final class Switchplugin extends JavaPlugin {

    public static Logger LOGGER;
    public static Server PLUGIN;
    private Channel channel = null;
    private String MC_QUEUE = "MC_QUEUE";

    private CommandHandler commandHandler;
    @Override
    public void onEnable() {

        LOGGER = this.getLogger();
        PLUGIN = this.getServer();
        this.commandHandler = new CommandHandler();
        commandHandler.addCommand(new OnlineCommand());
        // Plugin startup logic
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
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

                LOGGER.info("Got Message: "+message);
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

        // Plugin shutdown logic
    }
}
