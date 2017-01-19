package eu.mobilebear.carcompany.mvp.presenters;

import static java.net.HttpURLConnection.HTTP_OK;

import android.content.Context;
import eu.mobilebear.carcompany.injection.annotations.ActivityContext;
import eu.mobilebear.carcompany.mvp.model.APIResponse;
import eu.mobilebear.carcompany.mvp.model.Manufacturer;
import eu.mobilebear.carcompany.mvp.view.ManufacturerView;
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
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class ManufacturerPresenter implements Presenter<ManufacturerView> {

  public static final int PAGE_SIZE = 10;
  public static final int PAGE = 0;
  @ActivityContext
  private Context context;

  private RestClient restClient;
  private Subscription manufacturersSubscription;

  private List<Manufacturer> manufacturers;
  private ManufacturerView view;
  private int page;
  private int pageSize;
  private int totalPageCount;

  public ManufacturerPresenter(@ActivityContext Context context, RestClient restClient) {
    this.restClient = restClient;
    this.context = context;
  }

  @Override
  public void onStart() {
    view.showLoading();
    manufacturers = new ArrayList<>();
    manufacturersSubscription = subscribeManufactuers(0);

  }

  @Override
  public void onStop() {
    manufacturers.clear();
    if (manufacturersSubscription != null && manufacturersSubscription.isUnsubscribed()) {
      manufacturersSubscription.unsubscribe();
    }
  }

  @Override
  public void onResume() {

  }

  @Override
  public void onPause() {

  }

  @Override
  public void attachView(ManufacturerView view) {
    this.view = view;
  }

  private Subscription subscribeManufactuers(int page) {
    Observable<Response<APIResponse>> manufacturer = restClient.getCarService()
        .getManufacturers(restClient.getToken(), page, PAGE_SIZE);

    return manufacturer
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(this::getManufacturers)
        .doOnError(throwable -> Timber.e(throwable.getMessage()))
        .doOnCompleted(() -> view.showManufacturers(manufacturers))
        .subscribe();
  }


  private void getManufacturers(Response<APIResponse> response) {
    if (response.code() != HTTP_OK) {
      view.showError("Something went wrong: " + response.errorBody().toString());
      return;
    }
    HashMap<String, String> manufacturersHashMap = response.body().getItems();

    for (Entry<String, String> entry : manufacturersHashMap.entrySet()) {
      manufacturers.add(new Manufacturer(entry.getKey(), entry.getValue()));
    }
  }

}
