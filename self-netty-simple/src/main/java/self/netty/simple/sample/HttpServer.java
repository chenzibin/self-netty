package self.netty.simple.sample;

import self.netty.simple.bootstrap.ServerBootstrap;
import self.netty.simple.channel.ChannelInitializer;
import self.netty.simple.channel.NioServerSocketChannel;
import self.netty.simple.channel.NioSocketChannel;
import self.netty.simple.eventloop.NioEventLoopGroup;

/**
 * HttpServer
 *
 * @author chenzb
 * @date 2020/11/3
 */
public class HttpServer {

	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		bootstrap.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					public void initChannel(NioSocketChannel channel) throws Exception {

					}
				})
				.bind(8000)
				;
	}
}
