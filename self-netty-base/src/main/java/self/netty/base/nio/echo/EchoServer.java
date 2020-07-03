package self.netty.base.nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * EchoServer
 *
 * @author chenzb
 * @date 2020/7/2
 */
public class EchoServer implements Runnable {

	Selector selector;
	ServerSocketChannel serverSocket;

	public EchoServer(int port) throws IOException {
		selector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		serverSocket.configureBlocking(false);
		serverSocket.bind(new InetSocketAddress("localhost", port));
		SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		sk.attach(new Acceptor());


		SocketChannel socket = serverSocket.accept();
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// select() 用来获取当前的
				int count = selector.select();
				Set<SelectionKey> selected = selector.selectedKeys();
				for (SelectionKey sk : selected) {
					Runnable r = (Runnable) sk.attachment();
					if (r != null) {
						r.run();
					}
				}
				selected.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Acceptor implements Runnable {

		@Override
		public void run() {
			try {
				SocketChannel socket = serverSocket.accept();
				if (socket != null) {
					new IOHandler(selector, socket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class IOHandler implements Runnable {

		SelectionKey sk;
		SocketChannel socket;

		IOHandler(Selector selector, SocketChannel socket) throws IOException {
			this.socket = socket;
			socket.configureBlocking(false);
			sk = socket.register(selector, SelectionKey.OP_WRITE);
			sk.attach(this);
		}

		@Override
		public void run() {

		}
	}
}
