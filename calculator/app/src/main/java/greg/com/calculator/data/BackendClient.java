package greg.com.calculator.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Greg on 08-01-2017.
 */
public interface BackendClient {
    @GET("date")
    Observable<CurrentServerDate> currentDate();
    @POST("calculation")
    Observable<Void> addCalculation(@Body Calculation c);
}
