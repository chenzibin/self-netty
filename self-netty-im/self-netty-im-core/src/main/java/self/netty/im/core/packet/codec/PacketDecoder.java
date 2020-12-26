package self.netty.im.core.packet.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import self.netty.im.core.packet.Packet;
import self.netty.im.core.packet.payload.Payload;
import self.netty.im.core.packet.payload.PayloadCommand;
import self.netty.im.core.packet.serializer.SerializerEnum;

import java.util.List;

/**
 * PacketDecoder
 *
 * @author chenzb
 * @date 2020/12/22
 */
public class PacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		short magic = in.readShort();
		byte version = in.readByte();
		SerializerEnum serializerEnum = SerializerEnum.match(in.readByte());
		Class<? extends Payload> payloadClass = PayloadCommand.match(in.readByte());
		short length = in.readShort();
		byte[] payloadBytes = in.readBytes(length).array();

		Payload payload = serializerEnum.getSerializer().deserialize(payloadBytes, payloadClass);

		out.add(payload);
	}
}
