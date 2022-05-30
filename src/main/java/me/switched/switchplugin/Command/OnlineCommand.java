package me.switched.switchplugin.Command;

import me.switched.switchplugin.Assets;
import me.switched.switchplugin.DiscordWebhook;
import me.switched.switchplugin.Switchplugin;

import java.io.IOException;

public class OnlineCommand extends Command{
    public OnlineCommand() {
        this.name = "ONLINE_MCREQUEST";
    }

    @Override
    public void execute() {
        super.execute();
        DiscordWebhook webhook = new DiscordWebhook(Assets.MC_WEBHOOK);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(String.format("Players online: %s", Switchplugin.PLUGIN.getOnlinePlayers().size())));
        try {
            webhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
