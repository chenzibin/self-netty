package self.netty.base.nio.channel;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {

    private String ip = "127.0.0.1";
    private int port = 8999;

    String srcFile = "src/main/resources/file/source.txt";
    String destFile = "src/main/resources/file/source_backup.txt";

    private int capacity = 10;

    @Test
    public void socket() throws IOException {
        try (SocketChannel channel = SocketChannel.open()){
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(ip, port));
            while (!channel.finishConnect()) {
            }

            try (FileOutputStream file = new FileOutputStream(destFile);
                    FileChannel outChannel = file.getChannel()) {
                int len = 0;
                ByteBuffer buf = ByteBuffer.allocate(capacity);
                while ((len = channel.read(buf)) != -1) {
                    outChannel.write(buf, len);
                }
            }
        }
    }

    @Test
    public void serverSocket() throws IOException {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
        SocketChannel channel = serverChannel.accept()) {
            channel.configureBlocking(false);
            while (!channel.finishConnect()) {
            }

            try (RandomAccessFile file = new RandomAccessFile(srcFile, "rw");
                 FileChannel inChannel = file.getChannel()) {
                inChannel.transferTo(0, inChannel.size(), channel);
            }

            channel.shutdownOutput();
        }
    }
}
