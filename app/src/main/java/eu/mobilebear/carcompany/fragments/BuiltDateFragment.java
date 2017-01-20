package eu.mobilebear.carcompany.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

  @BindView(R.id.itemsRecyclerView)
  RecyclerView builtDatesRecyclerView;

  @BindView(R.id.searchButton)
  Button searchButton;

  @BindView(R.id.filterEditText)
  EditText filterEditText;

  private BuiltDateAdapter mainTypeAdapter;
  private List<BuiltDate> builtDates;
  private ProgressDialog progressDialog;
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
    View view = inflater.inflate(R.layout.fragment_items_search, container, false);
    unbinder = ButterKnife.bind(this, view);
    assignFieldsToValues();
    return view;
  }

  private void assignFieldsToValues() {
    builtDates = new ArrayList<>();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    builtDatesRecyclerView.setLayoutManager(linearLayoutManager);
    mainTypeAdapter = new BuiltDateAdapter(builtDates);
    builtDatesRecyclerView.setAdapter(mainTypeAdapter);
    builtDatesRecyclerView.setNestedScrollingEnabled(false);
    filterEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mainTypeAdapter.getFilter().filter(charSequence);
      }

      @Override
      public void afterTextChanged(Editable editable) {
      }
    });
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
    dismissProgressDialog();
    builtDatePresenter.onStop();
  }


  @OnClick(R.id.searchButton)
  void searchCars() {
    getActivity().getSupportFragmentManager();
  }


  private void injectDependencies() {
    if (getActivity() instanceof MainActivity) {
      MainActivity activity = (MainActivity) getActivity();
      CarComponent carComponent = activity.getCarComponent();
      carComponent.inject(this);
    }
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
}
