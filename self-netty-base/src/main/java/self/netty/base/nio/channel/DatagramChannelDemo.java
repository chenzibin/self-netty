package self.netty.base.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelDemo {

    private int capacity = 10;

    @Test
    public void datagram() throws IOException {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            ByteBuffer buf = ByteBuffer.allocate(capacity);
            SocketAddress client = channel.receive(buf);
        }
    }

    @Test
    public void datagramServer() {

    }
}
