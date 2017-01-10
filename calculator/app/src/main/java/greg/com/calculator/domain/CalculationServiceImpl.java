package greg.com.calculator.domain;

import javax.inject.Inject;

import greg.com.calculator.data.BackendService;
import greg.com.calculator.data.Calculation;
import greg.com.calculator.data.CurrentServerDate;
import greg.com.calculator.data.calculationType;
import rx.Observable;

/**
 * Created by Greg on 08-01-2017.
 */
public class CalculationServiceImpl implements CalculationService {

    private BackendService mBackendService;

    @Inject
    public CalculationServiceImpl(BackendService backendService){
        mBackendService = backendService;
    }

    public Observable<CurrentServerDate> GetServerDate(){
        return mBackendService.getServerDate();
    }
    public Observable<Double> PerformCalculation(double value1, double value2, final calculationType type){

        Calculation calculation = new Calculation();
        calculation.setValue1(value1);
        calculation.setValue2(value2);
        calculation.setCalculationType(type);

        return mBackendService.addCalculation(calculation).flatMap(r -> Observable.fromCallable(() -> calculateResult(value1, value2, type)));
    }

    private double calculateResult(double value1, double value2, calculationType type) throws Exception {
        double result = 0;

        switch (type){
            case addition:
                result = value1 + value2;
                break;
            case subtraction:
                result = value1 - value2;
                break;
            case division:
                result = value1 / value2;
                break;
            case multiplication:
                result = value1 * value2;
                break;
            default:
                break;
        }
        return result;
    }

}
