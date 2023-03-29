package client.command;

import database.drop.DropProvider;
import server.ShopFactory;

public record CommandContext(DropProvider dropProvider, ShopFactory shopFactory) {
}
