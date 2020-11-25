package self.netty.code.sample;

import self.netty.code.bootstrap.ServerBootstrap;
import self.netty.code.channel.ChannelInitializer;
import self.netty.code.channel.NioServerSocketChannel;
import self.netty.code.channel.NioSocketChannel;
import self.netty.code.eventloop.NioEventLoopGroup;

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
