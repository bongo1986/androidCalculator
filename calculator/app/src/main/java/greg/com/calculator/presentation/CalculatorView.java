package greg.com.calculator.presentation;

import android.app.ProgressDialog;

/**
 * Created by Greg on 08-01-2017.
 */
public interface CalculatorView {

    void toggleMultiplyButton(boolean show);
    void toggleAddButton(boolean show);
    void toggleSubtractButton(boolean show);
    void toggleEqualButton(boolean show);
    void toggleClearButton(boolean show);
    void updateCalculatorDisplay(String value);
    void setCurrentDate(String date);
    void showMessageSnackBar(String errorText);
    void lockScreen(String message);
    void unlockScreen();
}
