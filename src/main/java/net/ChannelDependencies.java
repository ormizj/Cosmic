package net;

import client.command.CommandsExecutor;
import client.processor.action.MakerProcessor;
import client.processor.npc.FredrickProcessor;
import database.drop.DropProvider;
import service.NoteService;

import java.util.Objects;

public record ChannelDependencies(
        NoteService noteService, FredrickProcessor fredrickProcessor, MakerProcessor makerProcessor,
        DropProvider dropProvider, CommandsExecutor commandsExecutor
) {

    public ChannelDependencies {
        Objects.requireNonNull(noteService);
        Objects.requireNonNull(fredrickProcessor);
        Objects.requireNonNull(makerProcessor);
        Objects.requireNonNull(dropProvider);
        Objects.requireNonNull(commandsExecutor);
    }
}
