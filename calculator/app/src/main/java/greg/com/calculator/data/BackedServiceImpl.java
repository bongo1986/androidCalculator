package greg.com.calculator.data;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Greg on 08-01-2017.
 */
public class BackedServiceImpl implements BackendService {


    private BackendClient mBackendClient;

    @Inject
    public BackedServiceImpl(BackendClient backendClient){
        mBackendClient = backendClient;
    }

    @Override
    public Observable<CurrentServerDate> getServerDate() {
        return mBackendClient.currentDate();
    }

    @Override
    public Observable<Void> addCalculation(Calculation calculation) {
        return mBackendClient.addCalculation(calculation);
    }
}
