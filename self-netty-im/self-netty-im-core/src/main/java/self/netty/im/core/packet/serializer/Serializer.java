package self.netty.im.core.packet.serializer;

/**
 * Serializer
 *
 * @author chenzb
 * @date 2020/12/21
 */
public interface Serializer {

	byte[] serialize(Object object);

	<T> T deserialize(byte[] bytes, Class<T> clazz);
}
