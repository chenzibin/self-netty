package self.netty.base.nio.selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * SelectorDemo
 *
 * @author chenzb
 * @date 2020/6/2
 */
public class SelectorDemo {

	private String ip = "127.0.0.1";
	private int port = 8999;

	@Test
	public void selector() throws IOException {
		try (Selector selector = Selector.open();
			 ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		) {
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.bind(new InetSocketAddress(ip, port));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while (selector.select() > 0) {
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isAcceptable()) {

					} else if (key.isConnectable()) {

					} else if (key.isReadable()) {

					} else if (key.isWritable()) {

					}
					iterator.remove();
				}
			}
		}
	}

}
