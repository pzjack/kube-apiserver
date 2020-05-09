package org.pz.netty.c3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer (int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Start Server on port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop () {
        this.stop = true;
    }

    @Override
    public void run () {
        while (!stop) {
            try {
                selector.select(1000);//sleep 1 sed
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        if (null != key) {
                            key.cancel();
                            if (null != key.channel()) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        if (null == selector) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handlerInput (SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer rb = ByteBuffer.allocate(1024);
                int rbn = sc.read(rb);
                if (rbn > 0) {
                    rb.flip();
                    byte[] bs = new byte[rb.remaining()];
                    rb.get(bs);
                    String body = new String (bs, "UTF-8");
                    System.out.println("Receiver:" + body);
                    String ct = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    doWrite(sc, ct);
                } else if (rbn < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    ;//read 0 lenght byte;
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (null != response && response.trim().length() > 0) {
            byte[] bs = response.getBytes();
            ByteBuffer bf = ByteBuffer.allocate(bs.length);
            bf.put(bs);
            bf.flip();
            channel.write(bf);
        }
    }
 }
