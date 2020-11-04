package self.netty.demo.im;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * IMServer
 *
 * @author chenzb
 * @date 2020/7/28
 */
public class IMServer {

	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		bootstrap.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel channel) throws Exception {
						channel.pipeline()
								.addLast(new HttpServerCodec())
								.addLast(new HttpContentCompressor())
								.addLast(new HttpObjectAggregator(65536))
								.addLast(new HttpServerHandler());
					}
				})
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.bind(8000).sync()
				.channel().closeFuture().sync();

	}

}
