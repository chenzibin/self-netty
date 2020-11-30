package self.netty.im.core.packet;

/**
 * Packet
 *
 * @author chenzb
 * @date 2020/11/25
 */
public class Packet {

	private short magic;
	private byte version = 0;
	private byte serializer;
	private byte command;
}
