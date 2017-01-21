package eu.mobilebear.carcompany.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.adapters.CarAdapter;
import eu.mobilebear.carcompany.injection.annotations.GetCars;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.mvp.model.Car;
import eu.mobilebear.carcompany.mvp.model.Search;
import eu.mobilebear.carcompany.mvp.model.SearchCriteria;
import eu.mobilebear.carcompany.mvp.presenters.CarPresenter;
import eu.mobilebear.carcompany.mvp.view.CarView;
import io.realm.Realm;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class CarSearchFragment extends Fragment implements CarView {

  @Inject
  CarPresenter carPresenter;

  @Inject
  @GetCars
  Realm realm;

  @BindView(R.id.itemsRecyclerView)
  RecyclerView carsRecyclerView;

  private Unbinder unbinder;
  private CarAdapter carAdapter;
  private ProgressDialog progressDialog;
  private List<Car> cars;

  public static CarSearchFragment newInstance() {
    return new CarSearchFragment();
  }

  public CarSearchFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    injectDependencies();
    carPresenter.attachView(this);
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search_car, container, false);
    unbinder = ButterKnife.bind(this, view);
    assignFieldsToValues();
    //TODO have to implement logic for receiving searchCriteria and presenter.
    SearchCriteria searchCriteria = realm.where(SearchCriteria.class).findFirst();
    carPresenter.setSearchCriteria(searchCriteria);
    showSearchParamtersForQuery(searchCriteria);
    return view;
  }

  /**
   * showing toast with parameters for search query. If we would have an endpoint we could normally
   * ask server for it. This method normally would't exist.
   */
  private void showSearchParamtersForQuery(SearchCriteria searchCriteria) {
    if (searchCriteria != null) {
      RealmList<Search> manufacturers = searchCriteria.getManufacturers();
      RealmList<Search> mainTypes = searchCriteria.getMainTypes();
      RealmList<Search> builtDates = searchCriteria.getBuiltDates();

      if (mainTypes.isEmpty() && builtDates.isEmpty()) {
        Toast.makeText(getActivity(), String.format("Search for cars from %1$s manufacturers",
            manufacturers.size()), Toast.LENGTH_LONG).show();
      }

      if (!mainTypes.isEmpty() && builtDates.isEmpty()) {
        Toast.makeText(getActivity(), String.format("Search for cars from %1$s manufacturer and "
                + "only for %2$s main types", manufacturers.size(), mainTypes.size()),
            Toast.LENGTH_LONG)
            .show();
      }
      if (!builtDates.isEmpty()) {
        Toast.makeText(getActivity(), String.format("Search for cars from %1$s manufacturer and "
                + "only for %2$s main type and %3$s specific built dates", manufacturers.size(),
            mainTypes.size(), builtDates.size()), Toast.LENGTH_LONG).show();
      }
    }

  }

  private void assignFieldsToValues() {
    cars = new ArrayList<>();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    carsRecyclerView.setLayoutManager(linearLayoutManager);
    carAdapter = new CarAdapter(cars);
    carsRecyclerView.setAdapter(carAdapter);
    carsRecyclerView.setNestedScrollingEnabled(false);
  }

  private void injectDependencies() {
    if (getActivity() instanceof MainActivity) {
      MainActivity activity = (MainActivity) getActivity();
      CarComponent carComponent = activity.getCarComponent();
      carComponent.inject(this);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    carPresenter.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    carPresenter.onStop();
  }

  @Override
  public void onResume() {
    super.onResume();
    carPresenter.onResume();
  }

  @Override
  public void onStart() {
    super.onStart();
    carPresenter.onStart();
  }

  @Override
  public void showCars(List<Car> cars) {

  }

  @Override
  public void showLoading() {
    launchProgressDialog();
  }

  @Override
  public void dismissLoading() {
    dismissProgressDialog();
  }

  @Override
  public void showError(String message) {
    dismissLoading();
    Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
  }

  private void launchProgressDialog() {
    progressDialog = ProgressDialog
        .show(getActivity(), "Please wait", "Downloading...", true);
    progressDialog.setCancelable(true);
  }

  private void dismissProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

}
