package org.pz.netty.c3;

public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        MultiplexerTimeServer server = new MultiplexerTimeServer(port);
        new Thread(server, "NIO-MultiplexerTimeServer-001").start();
    }
}
