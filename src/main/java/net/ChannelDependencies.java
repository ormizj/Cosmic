package net;

import client.command.CommandsExecutor;
import client.processor.action.MakerProcessor;
import client.processor.npc.FredrickProcessor;
import database.character.CharacterSaver;
import database.drop.DropProvider;
import server.shop.ShopFactory;
import service.ChannelService;
import service.NoteService;

import java.util.Objects;

/**
 * @author Ponk
 */
public record ChannelDependencies(
        CharacterSaver characterSaver, NoteService noteService, FredrickProcessor fredrickProcessor,
        MakerProcessor makerProcessor, DropProvider dropProvider, CommandsExecutor commandsExecutor,
        ShopFactory shopFactory, ChannelService channelService
) {

    public ChannelDependencies {
        Objects.requireNonNull(characterSaver);
        Objects.requireNonNull(noteService);
        Objects.requireNonNull(fredrickProcessor);
        Objects.requireNonNull(makerProcessor);
        Objects.requireNonNull(dropProvider);
        Objects.requireNonNull(commandsExecutor);
        Objects.requireNonNull(shopFactory);
        Objects.requireNonNull(channelService);
    }
}
