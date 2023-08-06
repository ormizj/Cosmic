package net.netty;

import database.character.CharacterSaver;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChannelServer extends AbstractServer {
    private final int world;
    private final int channel;
    private final CharacterSaver characterSaver;
    private Channel nettyChannel;

    public ChannelServer(int port, int world, int channel, CharacterSaver characterSaver) {
        super(port);
        this.world = world;
        this.channel = channel;
        this.characterSaver = characterSaver;
    }

    @Override
    public void start() {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelServerInitializer(world, channel, characterSaver));

        this.nettyChannel = bootstrap.bind(port).syncUninterruptibly().channel();
    }

    @Override
    public void stop() {
        if (nettyChannel == null) {
            throw new IllegalStateException("Must start ChannelServer before stopping it");
        }

        nettyChannel.close().syncUninterruptibly();
    }
}
