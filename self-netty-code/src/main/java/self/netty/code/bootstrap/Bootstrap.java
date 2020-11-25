package self.netty.code.bootstrap;

/**
 * Bootstrap
 *
 * @author chenzb
 * @date 2020/11/25
 */
public class Bootstrap extends AbstractBootstrap {

	public Bootstrap() {
	}

	public Bootstrap(Bootstrap bootstrap) {
		super();
	}

	@Override
	public Object clone() {
		return new Bootstrap(this);
	}
}
