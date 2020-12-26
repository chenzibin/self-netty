package self.netty.im.core.packet;

import lombok.Data;
import self.netty.im.core.packet.payload.Payload;
import self.netty.im.core.packet.serializer.SerializerEnum;

/**
 * Packet
 *
 * @author chenzb
 * @date 2020/11/25
 */
@Data
public class Packet<T extends Payload> {

	private short magic;
	private byte version = 1;
	private SerializerEnum serializer;
	private T payload;
}
