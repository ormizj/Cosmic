package client.command.commands.gm0;

import client.Character;
import client.Client;
import client.command.Command;
import client.command.CommandContext;

public class ReadPointsCommand extends Command {
    {
        setDescription("Show point total.");
    }

    @Override
    public void execute(Client client, String[] params, CommandContext ctx) {

        Character player = client.getPlayer();
        if (params.length > 2) {
            player.yellowMessage("Syntax: @points (rp|all)");
            return;
        } else if (params.length == 0) {
            player.yellowMessage("RewardPoints: " + player.getRewardPoints());
            return;
        }

        switch (params[0]) {
            case "rp":
                player.yellowMessage("RewardPoints: " + player.getRewardPoints());
                break;
            default:
                player.yellowMessage("RewardPoints: " + player.getRewardPoints());
                break;
        }
    }
}
