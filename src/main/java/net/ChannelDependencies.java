package net;

import client.processor.action.MakerProcessor;
import client.processor.npc.FredrickProcessor;
import service.NoteService;

import java.util.Objects;

public record ChannelDependencies(
        NoteService noteService, FredrickProcessor fredrickProcessor, MakerProcessor makerProcessor
) {

    public ChannelDependencies {
        Objects.requireNonNull(noteService);
        Objects.requireNonNull(fredrickProcessor);
        Objects.requireNonNull(makerProcessor);
    }
}
