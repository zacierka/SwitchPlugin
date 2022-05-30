package me.switched.switchplugin.Command;

import me.switched.switchplugin.Intf.CommandHandlerIntf;

import java.util.LinkedList;
import java.util.List;

public class CommandHandler implements CommandHandlerIntf {

    private List<Command> commands;

    public CommandHandler(List<Command> commands) {
        this.commands = commands;
    }

    public CommandHandler() {
        this.commands = new LinkedList<>();
    }

    public void addCommand(Command cmd) {
        this.commands.add(cmd);
    }

    @Override
    public void run(String msg) {
        if(msg.isEmpty()) return;
        for (Command cmd : commands) {
            if(cmd.getName().equals(msg)) {
                cmd.execute();
            }
        }
    }
}
