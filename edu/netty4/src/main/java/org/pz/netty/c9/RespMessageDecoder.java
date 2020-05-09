package org.pz.netty.c9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RespMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() >= 4) {
            if (in.readableBytes() > Constance.MAX_LENGTH) {
                in.skipBytes(in.readableBytes());
            }

            // 记录包头开始的index
            int beginReader;
            while (true) {
                // 获取包头开始的index
                beginReader = in.readerIndex();
                // 标记包头开始的index
                in.markReaderIndex();
                // 读到协议的开始标志，结束while循环
                if (in.readInt() == Constance.MAGIC_NUM) {
                    break;
                }

                // 未读到包头，跳过一个字节
                // 每次跳过一个字节后，再去读取包头信息的开始标记
                in.resetReaderIndex();
                in.readByte();
                if (in.readableBytes() < 4) {
                    return;
                }
            }

            // 代码到这里，说明已经读到了报文标志
            byte flag = in.readByte();
            // 至此，读到一条完整报文

            RespMessage msg = new RespMessage();
            msg.setFlag(flag);
            list.add(msg);
        }
    }
}