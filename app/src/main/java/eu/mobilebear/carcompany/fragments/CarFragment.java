package eu.mobilebear.carcompany.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.injection.annotations.CarPreferences;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.utils.FragmentUtils;
import javax.inject.Inject;

public class CarFragment extends Fragment {

  @Inject
  @CarPreferences
  SharedPreferences sharedPreferences;

  @BindView(R.id.manufacturerTextView)
  TextView manufacturerTextView;

  @BindView(R.id.mainTypeTextView)
  TextView mainTypeTextView;

  @BindView(R.id.builtDateTextView)
  TextView builtDateTextView;

  private Unbinder unbinder;

  public CarFragment() {
    // Required empty public constructor
  }

  public static CarFragment newInstance() {
    return new CarFragment();
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
    assignFieldsToVariables();
    return view;
  }

  private void assignFieldsToVariables() {
    String manufacturer = sharedPreferences.getString(FragmentUtils.MANUFACTURER_FRAGMENT, null);
    String mainType = sharedPreferences.getString(FragmentUtils.MAIN_TYPES_FRAGMENT, null);
    String builtDate = sharedPreferences.getString(FragmentUtils.BUILT_DATES_FRAGMENT, null);

    manufacturerTextView.setText(manufacturer);
    mainTypeTextView.setText(mainType);
    builtDateTextView.setText(builtDate);
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
