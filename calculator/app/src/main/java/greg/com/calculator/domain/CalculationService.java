package greg.com.calculator.domain;

import greg.com.calculator.data.CurrentServerDate;
import greg.com.calculator.data.calculationType;
import rx.Observable;

/**
 * Created by Greg on 08-01-2017.
 */
public interface CalculationService {
    Observable<Double> PerformCalculation(double value1, double value2, final calculationType type);
    Observable<CurrentServerDate> GetServerDate();
}
