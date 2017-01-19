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
import eu.mobilebear.carcompany.adapters.BuiltDateAdapter;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import eu.mobilebear.carcompany.mvp.presenters.BuiltDatePresenter;
import eu.mobilebear.carcompany.mvp.view.BuiltDateView;
import eu.mobilebear.carcompany.utils.FragmentUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class BuiltDateFragment extends Fragment implements BuiltDateView {

  @Inject
  BuiltDatePresenter builtDatePresenter;

  @BindView(R.id.builtDateRecyclerView)
  RecyclerView builtDatesRecyclerView;

  private BuiltDateAdapter mainTypeAdapter;
  private List<BuiltDate> builtDates;

  private Unbinder unbinder;

  public BuiltDateFragment() {
  }

  public static BuiltDateFragment newInstance(String manufacturerId, String mainTypeId) {
    BuiltDateFragment fragment = new BuiltDateFragment();
    Bundle args = new Bundle();
    args.putString(FragmentUtils.MANUFACTURER_FRAGMENT, manufacturerId);
    args.putString(FragmentUtils.MAIN_TYPES_FRAGMENT, mainTypeId);
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    injectDependencies();
    builtDatePresenter.attachView(this);

    if (getArguments() != null) {
      String manufacturerId = getArguments().getString(FragmentUtils.MANUFACTURER_FRAGMENT);
      String mainTypeId = getArguments().getString(FragmentUtils.MAIN_TYPES_FRAGMENT);
      builtDatePresenter.setSearchParameters(manufacturerId, mainTypeId);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_built_date, container, false);
    unbinder = ButterKnife.bind(this, view);

    builtDates = new ArrayList<>();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    builtDatesRecyclerView.setLayoutManager(linearLayoutManager);
    mainTypeAdapter = new BuiltDateAdapter(getActivity(), builtDates);
    builtDatesRecyclerView.setAdapter(mainTypeAdapter);
    builtDatesRecyclerView.setNestedScrollingEnabled(false);

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }


  @Override
  public void showBuiltDates(List<BuiltDate> builtDates) {
    this.builtDates.addAll(builtDates);
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
    builtDatePresenter.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
    builtDatePresenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    builtDatePresenter.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    builtDatePresenter.onStop();
  }

  private void injectDependencies() {
    if (getActivity() instanceof MainActivity) {
      MainActivity activity = (MainActivity) getActivity();
      CarComponent carComponent = activity.getCarComponent();
      carComponent.inject(this);
    }
  }
}
