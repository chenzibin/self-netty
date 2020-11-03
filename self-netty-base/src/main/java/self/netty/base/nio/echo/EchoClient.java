package self.netty.base.nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * EchoClient
 *
 * @author chenzb
 * @date 2020/7/2
 */
public class EchoClient implements Runnable {

	Selector selector;
	SocketChannel socket;
	ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	ByteBuffer sendBuffer = ByteBuffer.allocate(1024);

	EchoClient(String ip, int port) throws IOException {
		selector = Selector.open();
		socket = SocketChannel.open();
		socket.configureBlocking(false);
		socket.connect(new InetSocketAddress(ip, port));
		while (!socket.finishConnect()) {

		}
		socket.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				selector.select();
				Set<SelectionKey> selected = selector.selectedKeys();
				for (SelectionKey key : selected) {
					 if (key.isReadable()) {
						 socket.read(readBuffer);
						 readBuffer.flip();
						 CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
						 CharBuffer charBuffer = decoder.decode(readBuffer.asReadOnlyBuffer());
						 System.out.println(charBuffer.toString());
						 readBuffer.clear();
					 }
					 if (key.isWritable()) {
					 	Scanner scanner = new Scanner(System.in);
					 	if (scanner.hasNext()) {
							String line = scanner.nextLine();
							sendBuffer.put(line.getBytes());
							sendBuffer.flip();
							socket.write(sendBuffer);
							sendBuffer.clear();
						}
					 }
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
