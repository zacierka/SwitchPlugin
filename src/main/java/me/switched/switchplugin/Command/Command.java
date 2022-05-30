package me.switched.switchplugin.Command;

import me.switched.switchplugin.Intf.CommandIntf;
import me.switched.switchplugin.Switchplugin;

public class Command implements CommandIntf {
    public String name;

    public String getName() {
        return this.name;
    }

    @Override
    public void execute() {
        Switchplugin.LOGGER.info(String.format(" Command Executed: %s", this.name));
    }
}
