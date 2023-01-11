package com.ollethunberg.nationsplus.commands.crown;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ollethunberg.nationsplus.NationsPlus;

public class CrownHandler implements CommandExecutor {
    Crown crown = new Crown();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            /* Player sent the command */
            String cmd = command.getName().toLowerCase();
            Player player = (Player) sender;
            String action = args[0].toLowerCase();
            try {

                if (cmd.equals("crown")) {
                    switch (action) {
                        case "claim": {
                            crown.claim(player);
                            break;
                        }
                        case "unclaim": {
                            crown.unclaim(player);
                            break;
                        }
                    }
                }

            } catch (SQLException e) {
                player.sendMessage("§cThere was an error while executing the command!");
                e.printStackTrace();
                NationsPlus.LOGGER.warning(action + " " + e.getMessage());
                return true;
            } catch (Error | Exception e) {
                player.sendMessage("§r[§4§lERROR§r]§c " + e.getMessage());
                return true;
            }
        } else {
            /* Console sent the command */
            sender.sendMessage("You must be a player to use this command!");
        }
        return true;
    }

}