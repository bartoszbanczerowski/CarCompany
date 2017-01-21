package eu.mobilebear.carcompany.mvp.presenters;

import eu.mobilebear.carcompany.mvp.model.SearchCriteria;
import eu.mobilebear.carcompany.mvp.view.CarView;
import eu.mobilebear.carcompany.rest.RestClient;
import rx.subscriptions.CompositeSubscription;

/**
 * @author bartoszbanczerowski@gmail.com Created on 21.01.2017.
 */
public class CarPresenter implements Presenter<CarView> {

  private RestClient restClient;
  private CarView view;
  private CompositeSubscription compositeSubscription;
  private SearchCriteria searchCriteria;

  public CarPresenter(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void onStart() {
    view.showLoading();
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void onStop() {
    view.dismissLoading();
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
  public void attachView(CarView view) {
    this.view = view;
  }

  public void setSearchCriteria(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

}
