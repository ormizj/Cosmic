package net.netty;

import database.character.CharacterSaver;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class DisconnectHandler extends ChannelInboundHandlerAdapter {
    private final CharacterSaver characterSaver;

    public DisconnectHandler(CharacterSaver characterSaver) {
        this.characterSaver = characterSaver;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof DisconnectException de) {
            var client = de.getClient();
            client.disconnect(true, false);
        } else {
            ctx.fireExceptionCaught(cause);
        }
    }

}
