package self.netty.base.netty.bytebuf;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * UnsafeDemo
 *
 * @author chenzb
 * @date 2020/11/20
 */
public class UnsafeDemo {

	@Test
	public void testGetUnsafeByConstructor() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		Class<Unsafe> clazz = Unsafe.class;
		Constructor constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		Unsafe unsafe = (Unsafe) constructor.newInstance();
		long memoryAddress = unsafe.allocateMemory(10);
		unsafe.freeMemory(memoryAddress);
	}

	@Test
	public void testGetUnsafeByField() throws NoSuchFieldException, IllegalAccessException {
		Class clazz = Unsafe.class;
		Field field = clazz.getDeclaredField("theUnsafe");
		field.setAccessible(true);
		Unsafe unsafe = (Unsafe) field.get(null);
		unsafe.allocateMemory(10);
	}
}
