package eu.mobilebear.carcompany.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.injection.annotations.GetCars;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import io.realm.Realm;
import javax.inject.Inject;
import timber.log.Timber;

public class CarSearchFragment extends Fragment {

  @Inject
  @GetCars
  Realm realm;

  private Unbinder unbinder;

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

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_car, container, false);
    unbinder = ButterKnife.bind(this, view);

    //TODO have to implement logic for receiving searchCriteria and create presenter.
//    SearchCriteria searchCriteria = realm.where(SearchCriteria.class).findFirst();
//    Timber.d("SearchCriteria: " + searchCriteria.toString());
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void injectDependencies() {
    if (getActivity() instanceof MainActivity) {
      MainActivity activity = (MainActivity) getActivity();
      CarComponent carComponent = activity.getCarComponent();
      carComponent.inject(this);
    }
  }

}
