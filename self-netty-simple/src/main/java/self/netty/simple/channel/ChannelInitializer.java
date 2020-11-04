package self.netty.simple.channel;

/**
 * ChannelInitializer
 *
 * @author chenzb
 * @date 2020/11/4
 */
public interface ChannelInitializer<C extends NioSocketChannel> extends ChannelHandler {

	void initChannel(NioSocketChannel channel) throws Exception;
}
