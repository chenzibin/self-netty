package self.netty.base.nio.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FutureTaskDemo
 *
 * @author chenzb
 * @date 2020/7/21
 */
public class FutureTaskDemo {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new ThreadFactory() {

			AtomicInteger threadNumber = new AtomicInteger(0);
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "thread-" + threadNumber.getAndIncrement());
			}
		});
		// 1、执行逻辑Callable
		Callable<Boolean> callable = () -> true;
		// 2、可异步获取值Futrue, 绑定Callable
		FutureTask<Boolean> future = new FutureTask<>(callable);
		// 3、线程池执行Future
		executor.execute(future);
		// 4、异步获取值
		future.get();


		ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);

		FutureCallback<Boolean> futureCallback = new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				System.out.println(result);
			}

			@Override
			public void onFailure(Throwable t) {
				throw new RuntimeException(t);
			}
		};

		// 2、可异步获取值Futrue, 绑定Callable, 并 3、线程池执行Future
		ListenableFuture<Boolean> listenableFuture = listeningExecutor.submit(callable);
		// 添加执行结果的回调, 可直接回调赋值, 跳过4步骤
		Futures.addCallback(listenableFuture, futureCallback, executor);
		// 4、异步获取值
		listenableFuture.get();

	}
}
