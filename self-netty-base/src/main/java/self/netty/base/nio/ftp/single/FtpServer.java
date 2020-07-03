package self.netty.base.nio.ftp.single;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * FtpServer
 *
 * @author chenzb
 * @date 2020/6/5
 */
public class FtpServer {

	private static final String ROOT_DIR = System.getProperty("user.dir") + "/self-netty-base/src/main/resources/ftp/";
	private static final String FTP_SWAP_DIR = ROOT_DIR + "swap/";
	private static final String FTP_TEMP_DIR = ROOT_DIR + "temp/";

	private String ip;
	private int port;

	public FtpServer() {
	}

	public FtpServer bind(String ip, int port) {
		this.ip = ip;
		this.port = port;
		return this;
	}

	public void startup() {
		try (Selector selector = Selector.open();
			 ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
			serverChannel.configureBlocking(false);
			serverChannel.bind(new InetSocketAddress(ip, port));
			SelectionKey sk = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			sk.attach(new AcceptHandler(selector, serverChannel));
			while (selector.select() > 0) {
				Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
				while (selectionKeys.hasNext()) {
					SelectionKey selectionKey = selectionKeys.next();
					Runnable runnable = (Runnable) selectionKey.attachment();
					runnable.run();
					selectionKeys.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class AcceptHandler implements Runnable {

		private Selector selector;
		private ServerSocketChannel serverChannel;

		public AcceptHandler(Selector selector, ServerSocketChannel serverChannel) {
			this.selector = selector;
			this.serverChannel = serverChannel;
		}

		@Override
		public void run() {
			try (SocketChannel socketChannel = serverChannel.accept()) {
				socketChannel.configureBlocking(false);
				SelectionKey sk = socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				sk.attach(new IOHandler(selector, socketChannel));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class IOHandler implements Runnable {

		private Selector selector;
		private SocketChannel socketChannel;

		public IOHandler(Selector selector, SocketChannel socketChannel) {
			this.selector = selector;
			this.socketChannel = socketChannel;
		}

		@Override
		public void run() {
			String tempFileName = FTP_TEMP_DIR + System.currentTimeMillis();
			try (FileChannel tempFileChannel = new FileOutputStream(tempFileName).getChannel()) {
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				while (socketChannel.read(buffer) > 0) {
					buffer.flip();
					tempFileChannel.write(buffer);
					buffer.clear();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new FtpServer().bind("localhost", 8999).startup();
	}
}
