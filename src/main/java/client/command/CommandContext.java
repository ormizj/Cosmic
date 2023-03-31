package client.command;

import database.drop.DropProvider;
import server.shop.ShopFactory;

/**
 * @author Ponk
 */
public record CommandContext(CommandsExecutor commandsExecutor, DropProvider dropProvider, ShopFactory shopFactory) {

    public CommandContext with(CommandsExecutor ce) {
        return new CommandContext(ce, this.dropProvider, this.shopFactory);
    }
}
