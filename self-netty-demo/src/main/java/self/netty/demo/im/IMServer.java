package self.netty.demo.im;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
					protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

					}
				})
				.bind(8000)
				.addListener(future -> {
					if (future.isSuccess()) {
						System.out.println("success");
					} else {
						System.out.println("fail");
					}
				});

	}

}
