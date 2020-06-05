package self.netty.base.nio.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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

	@Test
	public void test() {
		String jarFilePath = "src/main/resources/file/log-app-1.0.0-SNAPSHOT.jar";

		try (JarInputStream input = new JarInputStream(new FileInputStream(jarFilePath))) {

			JarEntry entry = null;
			while ((entry = input.getNextJarEntry()) != null) {
				if ("jar_info.json".equals(entry.getName())) {

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
