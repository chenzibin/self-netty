package self.netty.base.nio.ftp;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * FtpDemo
 *
 * @author chenzb
 * @date 2020/6/5
 */
public class FtpDemo {

	@Test
	public void test() throws IOException {
		FtpClient client = new FtpClient();
		client.connect("localhost", 8999);
		client.upload("C:\\Users\\chenzb\\Downloads\\UDAP-2191.zip");
	}

	public void update() {

	}
}
