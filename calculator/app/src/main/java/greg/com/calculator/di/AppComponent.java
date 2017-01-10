package greg.com.calculator.di;

import javax.inject.Singleton;

import dagger.Component;
import greg.com.calculator.ui.MainActivity;

/**
 * Created by Greg on 08-01-2017.
 */
@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {

    void inject(MainActivity mainActivity);

}
