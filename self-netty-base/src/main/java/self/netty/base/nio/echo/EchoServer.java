package self.netty.base.nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * EchoServer
 * 1、两个Selector， Boss、Worker
 * 		-- Boss: 接收所有Acceptor事件，使用AcceptorHandler进行处理，将其绑定Worker
 * 		-- Worker： 接收所有IO事件，使用IOHandler进行处理
 *
 * @author chenzb
 * @date 2020/7/2
 */
public class EchoServer implements Runnable {

	Selector acceptSelector;
	Selector ioSelector;
	ServerSocketChannel serverSocket;
	ThreadFactory threadFactory = new ThreadFactory() {

		AtomicInteger threadNumber = new AtomicInteger(1);
		ThreadGroup group = Thread.currentThread().getThreadGroup();


		@Override
		public Thread newThread(Runnable r) {
			return new Thread(group, r, getName(), 0);
		}

		String getName() {
			return String.format("pool-thread-%s", threadNumber.getAndIncrement());
		}
	};
	RejectedExecutionHandler rejectedExecutionHandler = (r, executor) -> {};
	ExecutorService pool = new ThreadPoolExecutor(10, 100, 10, TimeUnit.MINUTES, new LinkedBlockingDeque<>(), threadFactory, rejectedExecutionHandler);

	public EchoServer(int port) throws IOException {
		acceptSelector = Selector.open();
		ioSelector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		serverSocket.configureBlocking(false);
		serverSocket.bind(new InetSocketAddress("localhost", port));
		SelectionKey sk = serverSocket.register(acceptSelector, SelectionKey.OP_ACCEPT);
		sk.attach(new Acceptor());
	}

	@Override
	public void run() {
		Thread acceptor = new Thread(new Reactor(acceptSelector), "acceptor");
		Thread handler = new Thread(new Reactor(ioSelector), "handler");
		acceptor.start();
		handler.start();
		try {
			acceptor.join();
			handler.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class Reactor implements Runnable {

		Selector selector;

		public Reactor(Selector selector) {
			this.selector = selector;
		}

		@Override
		public void run() {
			try {
				while (!Thread.interrupted()) {
					// select() 用来获取当前的
					int count = selector.select(5000);
					Set<SelectionKey> selected = selector.selectedKeys();
					for (SelectionKey sk : selected) {
						Runnable r = (Runnable) sk.attachment();
						if (r != null) {
							pool.execute(r);
						}
					}
					selected.clear();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Acceptor implements Runnable {

		@Override
		public void run() {
			try {
				SocketChannel socket = serverSocket.accept();
				if (socket != null) {
					new IOHandler(ioSelector, socket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class IOHandler implements Runnable {

		SelectionKey sk;
		SocketChannel socket;
		volatile ByteBuffer buffer = ByteBuffer.allocate(1024);
		static final int READING = 0;
		static final int SENDING = 1;
		int state = READING;


		IOHandler(Selector selector, SocketChannel socket) throws IOException {
			this.socket = socket;
			socket.configureBlocking(false);
			sk = socket.register(selector, SelectionKey.OP_READ);
			sk.attach(this);
		}

		@Override
		public void run() {
			try {
				if (state == READING) {
					read();
				} else if (state == SENDING) {
					send();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void read() throws IOException {
			socket.read(buffer);
			buffer.flip();
			sk.interestOps(SelectionKey.OP_WRITE);
			state = SENDING;
		}

		private void send() throws IOException {
			socket.write(buffer);
			buffer.clear();
			sk.interestOps(SelectionKey.OP_READ);
			state = READING;
		}
	}
}
