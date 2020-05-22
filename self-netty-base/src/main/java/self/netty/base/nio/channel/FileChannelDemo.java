package self.netty.base.nio.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.IntStream;

/**
 * FileChannelDemo
 *
 * @author chenzb
 * @date 2020/5/19
 */
public class FileChannelDemo {

	String srcFile = "src/main/resources/file/source.txt";
	String destFile = "src/main/resources/file/source_backup.txt";

	private int capacity = 3;

	@Test
	public void fileChannelOne() throws IOException {
		try (FileInputStream fis = new FileInputStream(srcFile);
			 FileChannel inChannel = fis.getChannel();
			 FileOutputStream fos = new FileOutputStream(destFile);
			 FileChannel outChannel = fos.getChannel()) {
			ByteBuffer buf = ByteBuffer.allocate(capacity);
			while (inChannel.read(buf) != -1) {
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
			outChannel.force(true);
		}
	}

	@Test
	public void fileChannelTwo() throws IOException {
		try (RandomAccessFile file = new RandomAccessFile(srcFile, "rw");
			 FileChannel inChannel = file.getChannel();
			 FileOutputStream fos = new FileOutputStream(destFile);
			FileChannel outChannel = fos.getChannel()) {
			inChannel.transferTo(0, inChannel.size(),  outChannel);
//			outChannel.transferFrom(inChannel, 0, inChannel.size());
		}
	}
}
