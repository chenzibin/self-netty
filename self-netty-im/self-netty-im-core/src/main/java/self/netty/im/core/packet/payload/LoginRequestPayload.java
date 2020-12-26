package self.netty.im.core.packet.payload;

import lombok.Data;

/**
 * LoginRequestPayload
 *
 * @author chenzb
 * @date 2020/12/2
 */
@Data
public class LoginRequestPayload implements Payload {

	private String username;

	private String password;

	@Override
	public byte getCommand() {
		return PayloadCommand.LOGIN_REQUEST;
	}
}
