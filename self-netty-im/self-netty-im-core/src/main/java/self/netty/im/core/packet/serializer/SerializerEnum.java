package self.netty.im.core.packet.serializer;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * SerializerEnum
 *
 * @author chenzb
 * @date 2020/12/22
 */
@Getter
public enum SerializerEnum {

	JSON((byte) 1, new JsonSerializer());

	private byte code;

	private Serializer serializer;

	SerializerEnum(byte code, Serializer serializer) {
		this.code = code;
		this.serializer = serializer;
	}

	static Map<Byte, SerializerEnum> serializerEnumMap = new HashMap<>();

	static {
		for (SerializerEnum serializerEnum : SerializerEnum.values()) {
			serializerEnumMap.put(serializerEnum.getCode(), serializerEnum);
		}
	}

	public static SerializerEnum match(byte code) {
		return serializerEnumMap.get(code);
	}
}
