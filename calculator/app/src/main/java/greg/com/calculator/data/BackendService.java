package greg.com.calculator.data;

import rx.Observable;

/**
 * Created by Greg on 08-01-2017.
 */
public interface BackendService {
    Observable<CurrentServerDate> getServerDate();
    Observable<Void> addCalculation(Calculation calculation);
}
