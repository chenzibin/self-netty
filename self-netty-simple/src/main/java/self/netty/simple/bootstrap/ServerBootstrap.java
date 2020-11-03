package self.netty.simple.bootstrap;

import self.netty.simple.channel.NioServerSocketChannel;
import self.netty.simple.eventloop.NioEventLoopGroup;

/**
 * ServerBootstrap
 *
 * @author chenzb
 * @date 2020/11/3
 */
public class ServerBootstrap {

	private NioEventLoopGroup boss;
	private NioEventLoopGroup worker;
	private Class<NioServerSocketChannel> channelClass;

	public ServerBootstrap group(NioEventLoopGroup boss, NioEventLoopGroup worker) {
		if (boss == null) {
			throw new NullPointerException("boss group");
		}
		if (this.boss != null) {
			throw new IllegalStateException("boss group set already");
		}
		if (worker == null) {
			throw new NullPointerException("worker group");
		}
		if (this.worker != null) {
			throw new IllegalStateException("worker group set already");
		}
		this.boss = boss;
		this.worker = worker;
		return this;
	}

	public ServerBootstrap channel(Class<NioServerSocketChannel> channelClass) {
		if (channelClass == null) {
			throw new  NullPointerException("channel class");
		}
		this.channelClass = channelClass;
		return this;
	}

	public ServerBootstrap bind(int port) {
		
	}
}
