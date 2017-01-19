package eu.mobilebear.carcompany.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.adapters.MainTypeAdapter;
import eu.mobilebear.carcompany.adapters.ManufacturerAdapter;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.mvp.model.MainType;
import eu.mobilebear.carcompany.mvp.presenters.MainTypePresenter;
import eu.mobilebear.carcompany.mvp.view.MainTypeView;
import eu.mobilebear.carcompany.utils.FragmentUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class MainTypeFragment extends Fragment implements MainTypeView {

  @Inject
  MainTypePresenter mainTypePresenter;

  @BindView(R.id.mainTypeRecyclerView)
  RecyclerView mainTypeRecyclerView;

  private MainTypeAdapter mainTypeAdapter;
  private List<MainType> mainTypes;
  private Unbinder unbinder;
  private String manufacturerId;


  public static MainTypeFragment newInstance(String manufacturerId) {
    MainTypeFragment fragment = new MainTypeFragment();
    Bundle args = new Bundle();
    args.putString(FragmentUtils.MAIN_TYPES_FRAGMENT, manufacturerId);
    fragment.setArguments(args);
    return fragment;
  }

  public MainTypeFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    injectDependencies();
    mainTypePresenter.attachView(this);
    if (getArguments() != null) {
      manufacturerId = getArguments().getString(FragmentUtils.MAIN_TYPES_FRAGMENT);
      mainTypePresenter.setManufacturer(manufacturerId);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main_type, container, false);
    unbinder = ButterKnife.bind(this, view);

    mainTypes = new ArrayList<>();

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mainTypeRecyclerView.setLayoutManager(linearLayoutManager);
    mainTypeAdapter = new MainTypeAdapter(getActivity(), manufacturerId, mainTypes);
    mainTypeRecyclerView.setAdapter(mainTypeAdapter);
    mainTypeRecyclerView.setNestedScrollingEnabled(false);

    return view;
  }


  @Override
  public void showMainTypes(List<MainType> mainTypes) {
    this.mainTypes.addAll(mainTypes);
    mainTypeAdapter.notifyDataSetChanged();
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void showError(String message) {

  }

  @Override
  public void onStart() {
    super.onStart();
    mainTypePresenter.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
    mainTypePresenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mainTypePresenter.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    mainTypePresenter.onStop();
  }

  private void injectDependencies() {
    if (getActivity() instanceof MainActivity) {
      MainActivity activity = (MainActivity) getActivity();
      CarComponent carComponent = activity.getCarComponent();
      carComponent.inject(this);
    }
  }
}
