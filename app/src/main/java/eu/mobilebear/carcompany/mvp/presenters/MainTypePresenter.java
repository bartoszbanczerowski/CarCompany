package eu.mobilebear.carcompany.mvp.presenters;

import static java.net.HttpURLConnection.HTTP_OK;

import eu.mobilebear.carcompany.mvp.model.APIResponse;
import eu.mobilebear.carcompany.mvp.model.MainType;
import eu.mobilebear.carcompany.mvp.view.MainTypeView;
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
public class MainTypePresenter implements Presenter<MainTypeView> {

  private static final int START_PAGE = 0;

  private RestClient restClient;
  private CompositeSubscription compositeSubscription;
  private MainTypeView view;
  private List<MainType> mainTypes;
  private String manufacturerId;


  public MainTypePresenter(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void onStart() {
    view.showLoading();
    mainTypes = new ArrayList<>();
    compositeSubscription = new CompositeSubscription();
    compositeSubscription.add(getMainTypesFromPage(START_PAGE));
  }

  @Override
  public void onStop() {
    view.dismissLoading();
    mainTypes.clear();
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
  public void attachView(MainTypeView view) {
    this.view = view;
  }

  public void setManufacturer(String manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  private Subscription getMainTypesFromPage(int page) {
    return restClient.getCarService()
        .getMainTypes(restClient.getToken(), page, 10, manufacturerId)
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
      mainTypes.add(new MainType(entry.getKey(), entry.getValue()));
    }
    view.showMainTypes(mainTypes);
    view.dismissLoading();

    if (hasNextPage(apiResponse.getPage(), apiResponse.getTotalPageCount())) {
      mainTypes.clear();
      compositeSubscription.add(getMainTypesFromPage(apiResponse.getPage() + 1));
    }
  }

  private boolean hasNextPage(int currentPage, int pageTotalCount) {
    return pageTotalCount > currentPage;
  }

}
