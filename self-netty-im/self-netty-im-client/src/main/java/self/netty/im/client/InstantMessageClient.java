package self.netty.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import self.netty.im.core.packet.codec.PacketDecoder;
import self.netty.im.core.packet.codec.PacketEncoder;

/**
 * InstantMessageClient
 *
 * @author chenzb
 * @date 2020/11/30
 */
public class InstantMessageClient {

	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup worker = new NioEventLoopGroup();
		bootstrap.group(worker)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline()
								.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 7, 4))
								.addLast(new PacketDecoder())
								.addLast(new PacketEncoder());
					}
				})
				.connect("localhost", 8000);
	}
}
