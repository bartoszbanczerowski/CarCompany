package eu.mobilebear.carcompany.mvp.presenters;

import static java.net.HttpURLConnection.HTTP_OK;

import android.content.Context;
import eu.mobilebear.carcompany.injection.annotations.ActivityContext;
import eu.mobilebear.carcompany.mvp.model.APIResponse;
import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import eu.mobilebear.carcompany.mvp.view.BuiltDateView;
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

public class BuiltDatePresenter implements Presenter<BuiltDateView> {

  private Subscription builtDatesSubscription;
  private BuiltDateView view;
  private RestClient restClient;
  private List<BuiltDate> builtDates;
  private String manufacturerId;
  private String mainTypeId;

  public BuiltDatePresenter(RestClient restClient) {
    this.restClient = restClient;
  }


  @Override
  public void onStart() {
    view.showLoading();
    builtDates = new ArrayList<>();
    builtDatesSubscription = getBuiltDatesSubscription();
  }

  @Override
  public void onStop() {
    builtDates.clear();
    if (builtDatesSubscription != null && builtDatesSubscription.isUnsubscribed()) {
      builtDatesSubscription.unsubscribe();
    }
  }

  @Override
  public void onResume() {

  }

  @Override
  public void onPause() {

  }

  @Override
  public void attachView(BuiltDateView view) {
    this.view = view;
  }

  public void setSearchParameters(String manufacturerId, String mainTypeId) {
    this.manufacturerId = manufacturerId;
    this.mainTypeId = mainTypeId;
  }

  private Subscription getBuiltDatesSubscription() {
    Observable<Response<APIResponse>> response = restClient.getCarService()
        .getBuiltDates(restClient.getToken(), manufacturerId, mainTypeId);

    return response
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(this::getBuiltDates)
        .doOnError(throwable -> Timber.e(throwable.getMessage()))
        .doOnCompleted(() -> view.showBuiltDates(builtDates))
        .subscribe();
  }


  private void getBuiltDates(Response<APIResponse> response) {
    if (response.code() != HTTP_OK) {
      view.showError("Something went wrong: " + response.errorBody().toString());
      return;
    }
    HashMap<String, String> manufacturersHashMap = response.body().getItems();

    for (Entry<String, String> entry : manufacturersHashMap.entrySet()) {
      builtDates.add(new BuiltDate(entry.getKey(), entry.getValue()));
    }
  }
}
