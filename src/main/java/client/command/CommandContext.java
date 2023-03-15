package client.command;

import database.drop.DropProvider;

public record CommandContext(DropProvider dropProvider) {
}
