package self.netty.im.core.packet.payload;

import lombok.Data;

@Data
public class MessageRequestPayload implements Payload {

    private String message;

    @Override
    public byte getCommand() {
        return PayloadCommand.MESSAGE_REQUEST;
    }
}
