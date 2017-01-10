package greg.com.calculator.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greg.com.calculator.CalculatorApplication;
import greg.com.calculator.data.BackedServiceImpl;
import greg.com.calculator.data.BackendClient;
import greg.com.calculator.data.BackendService;
import greg.com.calculator.data.ServiceGenerator;
import greg.com.calculator.domain.CalculationService;
import greg.com.calculator.domain.CalculationServiceImpl;
import greg.com.calculator.utils.ResourceRetriever;
import greg.com.calculator.utils.ResourceRetrieverImpl;
import greg.com.calculator.utils.ThreadProvider;
import greg.com.calculator.utils.ThreadProviderImpl;

/**
 * Created by Greg on 08-01-2017.
 */

@Module
public class AppModule {
    private final CalculatorApplication app;

    public AppModule(CalculatorApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }


    @Provides
    public ResourceRetriever provideResourceRetriever(Context c){
        return new ResourceRetrieverImpl(c);
    }
    @Provides
    public ThreadProvider provideThreadProvider(){
        return new ThreadProviderImpl();
    }
    @Provides
    public BackendClient provideBackendClient(){
        return ServiceGenerator.createService(BackendClient.class);
    }
    @Provides
    public BackendService provideBackendService(BackendClient bc){
        return new BackedServiceImpl(bc);
    }
    @Provides
    public CalculationService provideCalculationService(BackendService bs){
        return new CalculationServiceImpl(bs);
    }
}
