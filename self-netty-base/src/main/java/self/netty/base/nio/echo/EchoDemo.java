package self.netty.base.nio.echo;

import org.junit.Test;

import java.io.IOException;

/**
 * EchoDemo
 *
 * @author chenzb
 * @date 2020/7/6
 */
public class EchoDemo {

	static String ip = "localhost";
	static int port = 8999;

	@Test
	public void testServer() throws IOException {
		EchoServer server = new EchoServer(port);
		server.run();
	}

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient(ip, port);
		client.run();
	}
}
