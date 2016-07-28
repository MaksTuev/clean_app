package com.agna.setmaster.util.rx;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

/**
 *
 */
public class AsyncUtil {


    public interface Function<T> {
        T call() throws Exception;
    }

    public static <T> Observable<T> runIO(Function<T> f) {
        return Async.start(() -> {
            try {                return f.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, Schedulers.io());
    }
}
