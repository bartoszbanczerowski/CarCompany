package eu.mobilebear.carcompany.mvp.presenters;

import static eu.mobilebear.carcompany.rest.RestClient.PAGE_SIZE;
import static java.net.HttpURLConnection.HTTP_OK;

import eu.mobilebear.carcompany.mvp.model.APIResponse;
import eu.mobilebear.carcompany.mvp.model.Manufacturer;
import eu.mobilebear.carcompany.mvp.view.ManufacturerView;
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
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class ManufacturerPresenter implements Presenter<ManufacturerView> {

  private static final int START_PAGE = 0;

  private RestClient restClient;
  private CompositeSubscription compositeSubscription;
  private List<Manufacturer> manufacturers;
  private ManufacturerView view;

  public ManufacturerPresenter(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void onStart() {
    view.showLoading();
    manufacturers = new ArrayList<>();
    compositeSubscription = new CompositeSubscription();
    compositeSubscription.add(getManufacturersFromPage(START_PAGE));
  }

  @Override
  public void onStop() {
    view.dismissLoading();
    manufacturers.clear();
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
  public void attachView(ManufacturerView view) {
    this.view = view;
  }

  private Subscription getManufacturersFromPage(int page) {
    return restClient.getCarService()
        .getManufacturers(restClient.getToken(), page, PAGE_SIZE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::retrieveData, throwable -> view.showError(throwable.getMessage()));
  }

  private void retrieveData(Response<APIResponse> response) {
    if (response.code() != HTTP_OK) {
      view.showError("Something went wrong: " + response.errorBody().toString());
      return;
    }
    getManufacturers(response);
  }


  private void getManufacturers(Response<APIResponse> response) {
    APIResponse apiResponse = response.body();
    HashMap<String, String> manufacturersHashMap = apiResponse.getItems();

    for (Entry<String, String> entry : manufacturersHashMap.entrySet()) {
      manufacturers.add(new Manufacturer(entry.getKey(), entry.getValue()));
    }
    view.showManufacturers(manufacturers);
    view.dismissLoading();

    if (hasNextPage(apiResponse.getPage(), apiResponse.getTotalPageCount())) {
      manufacturers.clear();
      compositeSubscription.add(getManufacturersFromPage(apiResponse.getPage() + 1));
    }
  }

  private boolean hasNextPage(int currentPage, int pageTotalCount) {
    return pageTotalCount > currentPage;
  }

}
