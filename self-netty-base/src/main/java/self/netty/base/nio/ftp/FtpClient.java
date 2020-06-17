package self.netty.base.nio.ftp;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * FtpClient
 *
 * @author chenzb
 * @date 2020/6/5
 */
public class FtpClient {

	private boolean connected;
	private SocketChannel channel;

	public FtpClient() {
	}

	public void connect(String ip, int port) {
		try {
			this.channel = SocketChannel.open();
			this.channel.configureBlocking(false);
			this.channel.connect(new InetSocketAddress(ip, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void upload(String uploadFile) throws IOException {
		ensureConnected();
		try (FileChannel uploadFileChannel = new FileInputStream(uploadFile).getChannel()) {
			uploadFileChannel.transferTo(0, uploadFileChannel.size(), this.channel);
			this.channel.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void download(String downloadFile) throws IOException {
		ensureConnected();

	}

	private void ensureConnected() throws IOException {
		while (!this.channel.finishConnect()) {

		}
	}
}
