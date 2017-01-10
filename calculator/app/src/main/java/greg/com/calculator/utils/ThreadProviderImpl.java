package greg.com.calculator.utils;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Greg on 09-01-2017.
 */
public class ThreadProviderImpl implements ThreadProvider {
    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.newThread();
    }
}
