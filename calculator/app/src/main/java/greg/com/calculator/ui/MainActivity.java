package greg.com.calculator.ui;

import android.app.ProgressDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greg.com.calculator.CalculatorApplication;
import greg.com.calculator.R;
import greg.com.calculator.data.calculationType;
import greg.com.calculator.presentation.CalculatorPresenter;
import greg.com.calculator.presentation.CalculatorView;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements CalculatorView {


    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;
    @BindView(R.id.fab_subtract)
    FloatingActionButton fab_subtract;
    @BindView(R.id.fab_multiply)
    FloatingActionButton fab_multiply;
    @BindView(R.id.fab_equals)
    FloatingActionButton fab_equals;
    @BindView(R.id.fab_clear)
    FloatingActionButton fab_clear;
    @BindView(R.id.tv_display)
    TextView tv_display;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.main_content)
    CoordinatorLayout layoutContainer;
    @Inject
    CalculatorPresenter mCalculatorPresenter;

    HashMap<FloatingActionButton, Boolean> mFabs;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CalculatorApplication.getInstance().getAppComponent().inject(this);
        mCalculatorPresenter.setView(this);
        mFabs = new HashMap<FloatingActionButton, Boolean>();
        mFabs.put(fab_add, true);
        mFabs.put(fab_subtract, true);
        mFabs.put(fab_multiply, true);
        mFabs.put(fab_clear, true);
        mFabs.put(fab_equals, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCalculatorPresenter.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCalculatorPresenter.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCalculatorPresenter.resume();
    }

    @OnClick(R.id.btnNum1)
    public void Num1Clicked(){
        mCalculatorPresenter.addUserInput("1");
    }
    @OnClick(R.id.btnNum2)
    public void Num2Clicked(){
        mCalculatorPresenter.addUserInput("2");
    }
    @OnClick(R.id.btnNum3)
    public void Num3Clicked(){
        mCalculatorPresenter.addUserInput("3");
    }
    @OnClick(R.id.btnNum4)
    public void Num4Clicked(){
        mCalculatorPresenter.addUserInput("4");
    }
    @OnClick(R.id.btnNum5)
    public void Num5Clicked(){
        mCalculatorPresenter.addUserInput("5");
    }
    @OnClick(R.id.btnNum6)
    public void Num6Clicked(){
        mCalculatorPresenter.addUserInput("6");
    }
    @OnClick(R.id.btnNum7)
    public void Num7Clicked(){
        mCalculatorPresenter.addUserInput("7");
    }
    @OnClick(R.id.btnNum8)
    public void Num8Clicked(){
        mCalculatorPresenter.addUserInput("8");
    }
    @OnClick(R.id.btnNum9)
    public void Num9Clicked(){
        mCalculatorPresenter.addUserInput("9");
    }
    @OnClick(R.id.btnNum0)
    public void Num0Clicked(){
        mCalculatorPresenter.addUserInput("0");
    }


    @OnClick(R.id.fab_add)
    public void ButtonAddClicked() {
        rotateFab(fab_add);
        mCalculatorPresenter.operationButtonPressed(calculationType.addition);
    }
    @OnClick(R.id.fab_subtract)
    public void ButtonSubtractClicked() {
        rotateFab(fab_subtract);
        mCalculatorPresenter.operationButtonPressed(calculationType.subtraction);
    }
    @OnClick(R.id.fab_multiply)
    public void ButtonMultiplyClicked() {
        rotateFab(fab_multiply);
        mCalculatorPresenter.operationButtonPressed(calculationType.multiplication);
    }
    @OnClick(R.id.fab_clear)
    public void ButtonClearClicked() {
        rotateFab(fab_clear);
        mCalculatorPresenter.clearButtonPressed();
    }
    @OnClick(R.id.fab_equals)
    public void ButtonEqualsClicked() {
        rotateFab(fab_equals);
        mCalculatorPresenter.equalsButtonPressed();
    }
    @Override
    public void toggleMultiplyButton(boolean show) {
        toggleFab(show, fab_multiply);
    }
    @Override
    public void toggleAddButton(boolean show) {
        toggleFab(show, fab_add);
    }
    @Override
    public void toggleSubtractButton(boolean show) {
        toggleFab(show, fab_subtract);
    }
    @Override
    public void toggleEqualButton(boolean show) {
        toggleFab(show, fab_equals);
    }
    @Override
    public void toggleClearButton(boolean show) {
        toggleFab(show, fab_clear);
    }
    @Override
    public void updateCalculatorDisplay(String value) {
        tv_display.setText(value);
    }
    @Override
    public void setCurrentDate(String date) {
        tv_date.setText(date);
    }
    @Override
    public void showMessageSnackBar(String errorText) {
        showSnackBar(layoutContainer,errorText);
    }
    @Override
    public void lockScreen(String message) {
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    public void unlockScreen() {
        progress.hide();
        progress.dismiss();
    }
    private void toggleFab(boolean show, FloatingActionButton fab){
        if(show == false && mFabs.get(fab) == true){
            mFabs.remove(fab);
            mFabs.put(fab, false);
            fab.setEnabled(false);
            fab.animate()
                    .alpha(0.5f)
                    .setDuration(200);
        }else if(show == true && mFabs.get(fab) == false){

            mFabs.remove(fab);
            mFabs.put(fab, true);
            fab.setEnabled(true);
            fab.animate()
                    .alpha(1.0f)
                    .setDuration(200);
        }
    }
    private void rotateFab(FloatingActionButton fab){
        fab.animate().rotationBy(360).setDuration(200).start();
    }



    private void showSnackBar(View c, String errorText) {
        Snackbar snackbar = Snackbar.make(c, errorText, Snackbar.LENGTH_LONG);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }
}
