package self.netty.im.core.packet.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import self.netty.im.core.packet.Packet;

/**
 * PacketEncoder
 *
 * @author chenzb
 * @date 2020/12/22
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		byte[] payload = msg.getSerializer().getSerializer().serialize(msg.getPayload());
		out.writeShort(msg.getMagic());
		out.writeByte(msg.getVersion());
		out.writeByte(msg.getSerializer().getCode());
		out.writeByte(msg.getPayload().getCommand());
		out.writeShort(payload.length);
		out.writeBytes(payload);
	}
}
