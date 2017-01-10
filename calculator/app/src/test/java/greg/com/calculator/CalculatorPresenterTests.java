package greg.com.calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import greg.com.calculator.data.calculationType;
import greg.com.calculator.domain.CalculationService;
import greg.com.calculator.presentation.CalculatorPresenter;
import greg.com.calculator.presentation.CalculatorView;
import greg.com.calculator.utils.ResourceRetriever;
import greg.com.calculator.utils.ThreadProvider;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Greg on 10-01-2017.
 */
public class CalculatorPresenterTests {

    private CalculationService mockCalculationService;
    private CalculatorView mockCalculatorView;
    private ResourceRetriever strRetriever;
    private CalculatorPresenter calculatorPresenter;
    private TestScheduler testScheduler;

    private final class MockThreadProvider implements ThreadProvider {

        @Override
        public Scheduler mainThread() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler newThread() {
            return Schedulers.immediate();
        }
    }

    @Before
    public void setup() {
        mockCalculationService = mock(CalculationService.class);
        mockCalculatorView  = mock(CalculatorView.class);
        strRetriever  = mock(ResourceRetriever.class);
        when(strRetriever.getString(Matchers.anyInt())).thenReturn("str");
        when(strRetriever.getInt(Matchers.anyInt())).thenReturn(0);
        calculatorPresenter = new CalculatorPresenter(mockCalculationService, new MockThreadProvider(),strRetriever );
        calculatorPresenter.setView(mockCalculatorView);
    }
    @Test
    public void calculationPresenter_addUserInput_RefreshesScreenInput() {
        //Act
        calculatorPresenter.addUserInput("1");
        calculatorPresenter.addUserInput("2");
        calculatorPresenter.addUserInput("3");
        //Assert
        verify(mockCalculatorView, times(3)).updateCalculatorDisplay(anyString());
    }

    @Test
    public void calculationPresenter_calculationError_ErrorMessageDisplayed() {
        //Arrange
        when(mockCalculationService.PerformCalculation(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.any(calculationType.class))).thenReturn(Observable.error(new Exception()));
        calculatorPresenter.addUserInput("1");
        calculatorPresenter.addUserInput("2");
        calculatorPresenter.operationButtonPressed(calculationType.multiplication);
        calculatorPresenter.addUserInput("3");
        //Act
        calculatorPresenter.equalsButtonPressed();
        //Assert
        verify(mockCalculatorView, times(1)).showMessageSnackBar(anyString());
    }

}
