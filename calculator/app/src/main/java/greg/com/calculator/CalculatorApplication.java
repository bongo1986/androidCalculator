package greg.com.calculator;

import android.app.Application;

import greg.com.calculator.di.AppComponent;
import greg.com.calculator.di.AppModule;
import greg.com.calculator.di.DaggerAppComponent;

/**
 * Created by Greg on 08-01-2017.
 */
public class CalculatorApplication extends Application{

    private static AppComponent appComponent;

    private static CalculatorApplication instance = new CalculatorApplication();
    public static CalculatorApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
        //return null;
    }
}
