package com.ollethunberg.nationsplus.commands.reinforce;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ollethunberg.nationsplus.NationsPlus;
import com.ollethunberg.nationsplus.lib.SQLHelper;
import com.ollethunberg.nationsplus.lib.helpers.WalletBalanceHelper;

public class Reinforce extends WalletBalanceHelper {
    Plugin plugin = NationsPlus.getPlugin(NationsPlus.class);

    private void setReinforcementMode(String reinforcementMode, Player player) throws SQLException {
        SQLHelper.update("UPDATE player SET reinforcement_mode = ? WHERE uid = ?",
                reinforcementMode, player.getUniqueId().toString());
        player.sendMessage("§2Reinforcement mode set to " + reinforcementMode);
    }

    public void reinforce(Player player, String reinforcementType) throws SQLException {

        try {
            // check if reinforceTarget has a value
            if (reinforcementType == null) {
                // toggle reinforce mode
                ResultSet currentTarget = SQLHelper.query("SELECT reinforcement_mode FROM player WHERE uid = ?",
                        player.getUniqueId().toString());
                if (currentTarget.next()) {
                    String newReinforcementMode = currentTarget.getString("reinforcement_mode").equals("NATION")
                            ? "PRIVATE"
                            : "NATION";
                    setReinforcementMode(newReinforcementMode, player);
                }
            } else {
                reinforcementType = reinforcementType.toUpperCase();
                // check if newReinforcementMode is "N" or "P" and set it to "NATION" or
                // "PRIVATE"
                if (reinforcementType.equals("N")) {
                    reinforcementType = "NATION";
                } else if (reinforcementType.equals("P")) {
                    reinforcementType = "PRIVATE";
                }
                if (reinforcementType.equals("PRIVATE") || reinforcementType.equals("NATION")) {
                    setReinforcementMode(reinforcementType, player);
                } else {
                    player.sendMessage("§4Invalid reinforcement mode. Valid modes are: NATION, PRIVATE");
                }

            }
        } catch (SQLException e) {
            // print error
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}