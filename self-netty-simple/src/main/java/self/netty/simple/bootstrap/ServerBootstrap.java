package self.netty.simple.bootstrap;

import self.netty.simple.channel.ChannelHandler;
import self.netty.simple.channel.NioServerSocketChannel;
import self.netty.simple.eventloop.NioEventLoopGroup;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

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
	private ChannelHandler channelHandler;

	public ServerBootstrap group(NioEventLoopGroup boss, NioEventLoopGroup worker) {
		inspectNPE(boss, "boss group");
		inspectNPE(worker, "worker group");
		if (this.boss != null) {
			throw new IllegalStateException("boss group set already");
		}
		if (this.worker != null) {
			throw new IllegalStateException("worker group set already");
		}
		this.boss = boss;
		this.worker = worker;
		return this;
	}

	public ServerBootstrap channel(Class<NioServerSocketChannel> channelClass) {
		inspectNPE(channelClass, "channel class");
		this.channelClass = channelClass;
		return this;
	}

	public ServerBootstrap childHandler(ChannelHandler channelHandler) {
		inspectNPE(channelHandler, "channel handler");
		this.channelHandler = channelHandler;
		return this;
	}

	public ServerBootstrap bind(int port) {
		SocketAddress localAddress = new InetSocketAddress(port);
		inspectNPE(localAddress, "localAddress");
		if (this.boss == null) {
			throw new IllegalStateException("group not set");
		}
		if (channelClass == null) {
			throw new IllegalStateException("channel not set");
		}
		doBind(localAddress);
		return this;
	}

	private void doBind(SocketAddress localAddress) {

	}

	private void inspectNPE(Object subject, String name) {
		if (subject == null) {
			throw new NullPointerException(name);
		}
	}
}
