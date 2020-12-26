package self.netty.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import self.netty.im.core.packet.codec.PacketDecoder;
import self.netty.im.core.packet.codec.PacketEncoder;

import java.nio.charset.Charset;

/**
 * InstantMessageServer
 *
 * @author chenzb
 * @date 2020/11/25
 */
public class InstantMessageServer {

	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		bootstrap.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline()
								.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 5, 2))
								.addLast(new PacketDecoder())
								.addLast(new PacketEncoder());
					}
				})
				.bind(8000);
	}

	private static class MyServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ByteBuf byteBuf = (ByteBuf) msg;
			System.out.println(byteBuf.toString(Charset.defaultCharset()));
		}
	}
}
