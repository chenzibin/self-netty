package self.netty.base.netty.bytebuf;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * DirectByteBufferDemo
 *
 * @author chenzb
 * @date 2020/11/20
 */
public class DirectByteBufferDemo {

	@Test
	public void test() {
		ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
	}
}
