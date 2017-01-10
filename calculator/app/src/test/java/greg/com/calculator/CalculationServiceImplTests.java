package greg.com.calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import greg.com.calculator.data.BackendService;
import greg.com.calculator.data.Calculation;
import greg.com.calculator.data.calculationType;
import greg.com.calculator.domain.CalculationServiceImpl;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CalculationServiceImplTests {

    private BackendService mockBackendService;
    private CalculationServiceImpl calculationServiceImpl;

    @Before
    public void setup() {
        mockBackendService = mock(BackendService.class);
        when(mockBackendService.addCalculation(Matchers.any(Calculation.class))).thenReturn(Observable.just(null));
        calculationServiceImpl = new CalculationServiceImpl(mockBackendService);
    }

    @Test
    public void performCalculation_addingNumbers_RightResult()  {
        //Arrange
        TestScheduler testScheduler = new TestScheduler();
        //Act
        Observable<Double> result = calculationServiceImpl.PerformCalculation(2,3, calculationType.addition);
        result.subscribeOn(testScheduler);
        testScheduler.triggerActions();
        double val = result.toBlocking().first();
        //Assert
        assertEquals(val, 5, 0.001);
    }
    @Test
    public void performCalculation_subtractingNumbers_RightResult()  {
        //Arrange
        TestScheduler testScheduler = new TestScheduler();
        //Act
        Observable<Double> result = calculationServiceImpl.PerformCalculation(2,3, calculationType.subtraction);
        result.subscribeOn(testScheduler);
        testScheduler.triggerActions();
        double val = result.toBlocking().first();
        //Assert
        assertEquals(val, -1, 0.001);
    }
    @Test
    public void performCalculation_multiplyingNumbers_RightResult() {
        //Arrange
        TestScheduler testScheduler = new TestScheduler();
        //Act
        Observable<Double> result = calculationServiceImpl.PerformCalculation(3,3, calculationType.multiplication);
        result.subscribeOn(testScheduler);
        testScheduler.triggerActions();
        double val = result.toBlocking().first();
        //Assert
        assertEquals(val, 9, 0.001);

    }
    @Test
    public void performCalculation_cantSaveCalculationOnServer_operationFails() {
        //Arrange
        TestScheduler testScheduler = new TestScheduler();
        TestSubscriber<Double> testSubscriber = TestSubscriber.create();
        when(mockBackendService.addCalculation(Matchers.any(Calculation.class))).thenReturn(Observable.error(new Exception()));
        //Act
        Observable<Double> result = calculationServiceImpl.PerformCalculation(3,3, calculationType.multiplication);
        result.subscribeOn(testScheduler).subscribe(testSubscriber);
        testScheduler.triggerActions();
        //Assert
        testSubscriber.assertError(Exception.class);
    }

}