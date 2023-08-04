package client.command;

import database.drop.DropProvider;
import server.shop.ShopFactory;
import service.ChannelService;

/**
 * @author Ponk
 */
public record CommandContext(CommandsExecutor commandsExecutor, DropProvider dropProvider, ShopFactory shopFactory,
                             ChannelService channelService) {

    public CommandContext with(CommandsExecutor ce) {
        return new CommandContext(ce, this.dropProvider, this.shopFactory, this.channelService);
    }
}
