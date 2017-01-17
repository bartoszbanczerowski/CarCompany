package eu.mobilebear.carcompany.mvp.presenters;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
public interface Presenter<T> {

	void onCreate();

	void onStart();

	void onStop();

	void onPause();

	void attachView(T view);


}
