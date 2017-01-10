package greg.com.calculator.presentation;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import greg.com.calculator.R;
import greg.com.calculator.data.calculationType;
import greg.com.calculator.domain.CalculationService;
import greg.com.calculator.utils.ResourceRetriever;
import greg.com.calculator.utils.ThreadProvider;

/**
 * Created by Greg on 08-01-2017.
 */
public class CalculatorPresenter implements BasePresenter<CalculatorView> {

    private CalculationService mCalculationService;
    private ThreadProvider mThreadProvider;
    private ResourceRetriever mResourceRetriever;
    private CalculatorView mCalculatorView;
    private String mValue1 = "";
    private String mValue2 = "";
    private boolean mIsSecondValue;
    private calculationType mCalculationType = calculationType.none;

    @Inject
    public CalculatorPresenter(CalculationService calculationService, ThreadProvider threadProvider, ResourceRetriever resourceRetriever) {
        mCalculationService = calculationService;
        mThreadProvider = threadProvider;
        mResourceRetriever = resourceRetriever;
    }

    @Override
    public void setView(CalculatorView view) {
        mCalculatorView = view;
    }

    public void addUserInput(String input) {
        if (mIsSecondValue == false) {
            mValue1 += input;
        } else {
            mValue2 += input;
        }
        updateDisplay();
        toggleButtons();
    }

    private void updateDisplay() {
        mCalculatorView.updateCalculatorDisplay(mValue1 + getCalculationTypeString(mCalculationType) + mValue2);
    }

    public void operationButtonPressed(calculationType type) {
        mCalculationType = type;
        mIsSecondValue = true;
        updateDisplay();
        toggleButtons();
    }

    public void clearButtonPressed() {
        mValue1 = "";
        mValue2 = "";
        mIsSecondValue = false;
        mCalculationType = calculationType.none;
        updateDisplay();
        toggleButtons();
    }

    public void equalsButtonPressed() {
        try {
            double val1 = Double.parseDouble(mValue1);
            double val2 = Double.parseDouble(mValue2);
            mCalculatorView.lockScreen(mResourceRetriever.getString(R.string.please_wait));
            mCalculationService.PerformCalculation(val1, val2, mCalculationType)
                    .delay(mResourceRetriever.getInt(R.integer.delayMs), TimeUnit.MILLISECONDS)
                    .subscribeOn(mThreadProvider.newThread())
                    .observeOn(mThreadProvider.mainThread())
                    .subscribe(
                            result -> {
                                mValue1 = String.format("%.0f", result);
                                mValue2 = "";
                                mIsSecondValue = false;
                                mCalculationType = calculationType.none;
                                updateDisplay();
                                toggleButtons();
                            }, e -> {
                                clearButtonPressed();
                                mCalculatorView.unlockScreen();
                                mCalculatorView.showMessageSnackBar(mResourceRetriever.getString(R.string.calculationError));
                            }, () -> {
                                mCalculatorView.unlockScreen();
                            }
                    );
        } catch (Exception ex) {
            clearButtonPressed();
            mCalculatorView.showMessageSnackBar(mResourceRetriever.getString(R.string.calculationError));
        }
    }


    @Override
    public void resume() {
        toggleButtons();
        mCalculatorView.lockScreen(mResourceRetriever.getString(R.string.please_wait));
        mCalculationService.GetServerDate()
                .delay(mResourceRetriever.getInt(R.integer.delayMs),TimeUnit.MILLISECONDS)
                .subscribeOn(mThreadProvider.newThread())
                .observeOn(mThreadProvider.mainThread())
                .subscribe(response -> {
                            mCalculatorView.setCurrentDate(response.getValue());
                        },
                        e -> {
                            mCalculatorView.unlockScreen();
                            mCalculatorView.showMessageSnackBar(mResourceRetriever.getString(R.string.date_error));
                        },
                        () -> {
                            mCalculatorView.unlockScreen();
                        });

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mCalculatorView = null;
    }


    private String getCalculationTypeString(calculationType type) {
        switch (type) {
            case addition:
                return "+";
            case subtraction:
                return "-";
            case division:
                return "/";
            case multiplication:
                return "*";
            default:
                return "";
        }
    }

    private void toggleButtons() {
        if (mValue1.equals("") && mValue2.equals("") && mCalculationType == calculationType.none) {
            mCalculatorView.toggleAddButton(false);
            mCalculatorView.toggleSubtractButton(false);
            mCalculatorView.toggleMultiplyButton(false);
            mCalculatorView.toggleClearButton(false);
            mCalculatorView.toggleEqualButton(false);
        } else if (mValue1.equals("") == false && mValue2.equals("") && mCalculationType == calculationType.none) {
            mCalculatorView.toggleAddButton(true);
            mCalculatorView.toggleSubtractButton(true);
            mCalculatorView.toggleMultiplyButton(true);
            mCalculatorView.toggleClearButton(true);
            mCalculatorView.toggleEqualButton(false);
        } else if (mValue1.equals("") == false && mValue2.equals("") && mCalculationType != calculationType.none) {
            mCalculatorView.toggleAddButton(false);
            mCalculatorView.toggleSubtractButton(false);
            mCalculatorView.toggleMultiplyButton(false);
            mCalculatorView.toggleClearButton(true);
            mCalculatorView.toggleEqualButton(false);
        } else if (mValue1.equals("") == false && mValue2.equals("") == false && mCalculationType != calculationType.none) {
            mCalculatorView.toggleAddButton(false);
            mCalculatorView.toggleSubtractButton(false);
            mCalculatorView.toggleMultiplyButton(false);
            mCalculatorView.toggleClearButton(true);
            mCalculatorView.toggleEqualButton(true);
        }
    }
}
