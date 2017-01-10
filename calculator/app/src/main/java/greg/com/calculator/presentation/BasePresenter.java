package greg.com.calculator.presentation;

/**
 * Created by Greg on 08-01-2017.
 */
public interface BasePresenter<T> {
    void setView(T view);
    void resume();
    void pause();
    void destroy();
}
