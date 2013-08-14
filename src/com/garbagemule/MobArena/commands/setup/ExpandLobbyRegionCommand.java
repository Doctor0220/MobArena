package com.garbagemule.MobArena.commands.setup;

import org.bukkit.command.CommandSender;

import com.garbagemule.MobArena.*;
import com.garbagemule.MobArena.commands.*;
import com.garbagemule.MobArena.framework.ArenaMaster;

@CommandInfo(
    name    = "expandlobbyregion",
    pattern = "expandlobby(region)?",
    usage   = "/ma expandlobbyregion <amount> up|down|out",
    desc    = "expand the arena lobby region",
    permission = "mobarena.setup.expandlobbyregion"
)
public class ExpandLobbyRegionCommand implements Command
{
    @Override
    public boolean execute(ArenaMaster am, CommandSender sender, String... args) {
        // Grab the argument, if any.
        String arg1 = (args.length > 0 ? args[0] : "");
        String arg2 = (args.length > 1 ? args[1] : "");

        if (args.length != 2 || !arg1.matches("(-)?[0-9]+")) {
            Messenger.tellPlayer(sender, "Usage: /ma expandlobbyregion <amount> up|down|out");
            return false;
        }
        
        if (!am.getSelectedArena().getRegion().isLobbyDefined()) {
            Messenger.tellPlayer(sender, "You must first define l1 and l2");
            return true;
        }
        
        if (arg2.equals("up")) {
            am.getSelectedArena().getRegion().expandLobbyUp(Integer.parseInt(arg1));
        }
        else if (arg2.equals("down")) {
            am.getSelectedArena().getRegion().expandLobbyDown(Integer.parseInt(arg1));
        }
        else if (arg2.equals("out")) {
            am.getSelectedArena().getRegion().expandLobbyOut(Integer.parseInt(arg1));
        }
        else {
            Messenger.tellPlayer(sender, "Usage: /ma expandlobbyregion <amount> up|down|out");
            return true;
        }
        
        // In case of a "negative" region, fix it!
        am.getSelectedArena().getRegion().fixLobbyRegion();
        
        Messenger.tellPlayer(sender, "Lobby region for '" + am.getSelectedArena().configName() + "' expanded " + arg2 + " by " + arg1 + " blocks.");
        am.getSelectedArena().getRegion().save();
        
        return true;
    }
}
