package self.netty.im.core.packet.serializer;

import com.alibaba.fastjson.JSON;

/**
 * JsonSerializer
 *
 * @author chenzb
 * @date 2020/12/21
 */
public class JsonSerializer implements Serializer {

	@Override
	public byte[] serialize(Object object) {
		return JSON.toJSONBytes(object);
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) {
		return JSON.parseObject(bytes, clazz);
	}
}
