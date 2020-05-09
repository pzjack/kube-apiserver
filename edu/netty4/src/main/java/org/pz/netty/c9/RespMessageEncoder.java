package org.pz.netty.c9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RespMessageEncoder extends MessageToByteEncoder<RespMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RespMessage msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getHeader());
        out.writeByte(msg.getFlag());
    }
}
