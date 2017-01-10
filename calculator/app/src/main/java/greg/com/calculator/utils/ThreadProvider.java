package greg.com.calculator.utils;

import rx.Scheduler;

/**
 * Created by Greg on 09-01-2017.
 */
public interface ThreadProvider {
    Scheduler mainThread();
    Scheduler newThread();
}
