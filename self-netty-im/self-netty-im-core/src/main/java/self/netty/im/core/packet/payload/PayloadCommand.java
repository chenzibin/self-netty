package self.netty.im.core.packet.payload;

/**
 * PayloadCommand
 *
 * @author chenzb
 * @date 2020/12/22
 */
public class PayloadCommand {

	public static final byte LOGIN_REQUEST = 1;
	public static final byte MESSAGE_REQUEST = 2;

	public static Class<? extends Payload> match(byte code) {
		switch (code) {
			case LOGIN_REQUEST:
				return LoginRequestPayload.class;
			case MESSAGE_REQUEST:
				return MessageRequestPayload.class;
			default:
				throw new IllegalArgumentException("Unsupported command " + code);
		}
	}
}
