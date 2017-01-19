package eu.mobilebear.carcompany.mvp.presenters;

import static java.net.HttpURLConnection.HTTP_OK;

import android.content.Context;
import eu.mobilebear.carcompany.injection.annotations.ActivityContext;
import eu.mobilebear.carcompany.mvp.model.APIResponse;
import eu.mobilebear.carcompany.mvp.model.MainType;
import eu.mobilebear.carcompany.mvp.view.MainTypeView;
import eu.mobilebear.carcompany.rest.RestClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */
public class MainTypePresenter implements Presenter<MainTypeView> {

  private RestClient restClient;
  private Subscription mainTypeSubscription;
  private MainTypeView view;
  private List<MainType> mainTypes;


  private String manufacturerId;
  private int page;
  private int pageSize;
  private int totalPageCount;

  public MainTypePresenter(RestClient restClient) {
    this.restClient = restClient;
  }


  @Override
  public void onStart() {
    view.showLoading();
    mainTypes = new ArrayList<>();
    mainTypeSubscription = getMainTypes();
  }

  @Override
  public void onStop() {
    mainTypes.clear();
    if (mainTypeSubscription != null && mainTypeSubscription.isUnsubscribed()) {
      mainTypeSubscription.unsubscribe();
    }
  }

  @Override
  public void onResume() {

  }

  @Override
  public void onPause() {

  }

  @Override
  public void attachView(MainTypeView view) {
    this.view = view;
  }

  public void setManufacturer(String manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  private Subscription getMainTypes() {
    Observable<Response<APIResponse>> response = restClient.getCarService()
        .getMainTypes(restClient.getToken(), 0, 10, manufacturerId);

    return response
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(this::getManufacturers)
        .doOnError(throwable -> Timber.e(throwable.getMessage()))
        .doOnCompleted(() -> view.showMainTypes(mainTypes))
        .subscribe();
  }


  private void getManufacturers(Response<APIResponse> response) {
    if (response.code() != HTTP_OK) {
      view.showError("Something went wrong: " + response.errorBody().toString());
      return;
    }
    HashMap<String, String> manufacturersHashMap = response.body().getItems();

    for (Entry<String, String> entry : manufacturersHashMap.entrySet()) {
      mainTypes.add(new MainType(entry.getKey(), entry.getValue()));
    }
  }


}
