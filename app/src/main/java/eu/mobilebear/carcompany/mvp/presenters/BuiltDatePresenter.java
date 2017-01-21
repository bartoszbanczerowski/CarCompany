package eu.mobilebear.carcompany.mvp.presenters;

import static java.net.HttpURLConnection.HTTP_OK;

import eu.mobilebear.carcompany.mvp.model.APIResponse;
import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import eu.mobilebear.carcompany.mvp.view.BuiltDateView;
import eu.mobilebear.carcompany.rest.RestClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */

public class BuiltDatePresenter implements Presenter<BuiltDateView> {

  private RestClient restClient;
  private CompositeSubscription compositeSubscription;
  private BuiltDateView view;
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
    compositeSubscription = new CompositeSubscription();
    compositeSubscription.add(getBuiltDatesFromPage());
  }

  @Override
  public void onStop() {
    view.dismissLoading();
    builtDates.clear();
    if (compositeSubscription != null && compositeSubscription.isUnsubscribed()) {
      compositeSubscription.unsubscribe();
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

  private Subscription getBuiltDatesFromPage() {
    return restClient.getCarService()
        .getBuiltDates(restClient.getToken(), manufacturerId, mainTypeId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::retrieveData, throwable -> view.showError(throwable.getMessage()));
  }

  private void retrieveData(Response<APIResponse> response) {
    if (response.code() != HTTP_OK) {
      view.showError("Something went wrong: " + response.errorBody().toString());
      return;
    }
    getMainTypes(response);
  }


  private void getMainTypes(Response<APIResponse> response) {
    APIResponse apiResponse = response.body();
    HashMap<String, String> manufacturersHashMap = apiResponse.getItems();

    for (Entry<String, String> entry : manufacturersHashMap.entrySet()) {
      builtDates.add(new BuiltDate(entry.getKey(), entry.getValue()));
    }
    view.showBuiltDates(builtDates);
    view.dismissLoading();
  }

}
