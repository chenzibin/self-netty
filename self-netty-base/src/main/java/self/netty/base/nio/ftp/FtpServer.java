package self.netty.base.nio.ftp;

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
					if (selectionKey.isAcceptable()) {
						Runnable runnable = (Runnable) selectionKey.attachment();
						runnable.run();
						ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
						SocketChannel socketChannel = channel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					} else if (selectionKey.isReadable()) {
						String tempFileName = FTP_TEMP_DIR + System.currentTimeMillis();
						try (SocketChannel channel = (SocketChannel) selectionKey.channel();
							 FileChannel tempFileChannel = new FileOutputStream(tempFileName).getChannel()) {
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							while (channel.read(buffer) > 0) {
								buffer.flip();
								tempFileChannel.write(buffer);
								buffer.clear();
							}
						}

					} else if (selectionKey.isWritable()) {
						SocketChannel channel = (SocketChannel) selectionKey.channel();
//						channel.write()

					}
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
			ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
			try (SocketChannel socketChannel = channel.accept()) {
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new FtpServer().bind("localhost", 8999).startup();
	}
}
