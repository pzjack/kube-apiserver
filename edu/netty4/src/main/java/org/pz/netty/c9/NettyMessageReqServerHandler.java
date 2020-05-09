package org.pz.netty.c9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.pz.netty.c8.SubscribeReqProto;
import org.pz.netty.c8.SubscribeRespProto;

import java.net.SocketAddress;

public class NettyMessageReqServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        SocketAddress sct = ctx.channel().remoteAddress();
//        System.out.println("Handler:" + sct.toString());
        NettyMessage req = (NettyMessage)msg;
        System.out.println("Server rec:" + req.toString());

        ctx.writeAndFlush(new RespMessage());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
