package client.command;

import database.drop.DropProvider;
import server.shop.ShopFactory;

/**
 * @author Ponk
 */
public record CommandContext(DropProvider dropProvider, ShopFactory shopFactory) {
}
