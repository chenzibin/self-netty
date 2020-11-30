package self.netty.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;

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
								.addLast(new MyClientHandler());
					}
				})
				.connect("localhost", 8000);
	}

	private static class MyClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			ByteBuf byteBuf = ctx.alloc().buffer();
			byteBuf.writeBytes("你好，neety!".getBytes());
			ctx.channel().writeAndFlush(byteBuf);
		}
	}
}
