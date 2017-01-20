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
import butterknife.Unbinder;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.adapters.MainTypeAdapter;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.mvp.model.MainType;
import eu.mobilebear.carcompany.mvp.presenters.MainTypePresenter;
import eu.mobilebear.carcompany.mvp.view.MainTypeView;
import eu.mobilebear.carcompany.utils.FragmentUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MainTypeFragment extends Fragment implements MainTypeView {

  @Inject
  MainTypePresenter mainTypePresenter;

  @BindView(R.id.itemsRecyclerView)
  RecyclerView mainTypeRecyclerView;

  @BindView(R.id.searchButton)
  Button searchButton;

  @BindView(R.id.filterEditText)
  EditText filterEditText;

  private MainTypeAdapter mainTypeAdapter;
  private List<MainType> mainTypes;
  private Unbinder unbinder;
  private String manufacturerId;
  private ProgressDialog progressDialog;

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
    View view = inflater.inflate(R.layout.fragment_items_search, container, false);
    unbinder = ButterKnife.bind(this, view);
    assignFieldsToValues();

    return view;
  }

  private void assignFieldsToValues() {
    mainTypes = new ArrayList<>();

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mainTypeRecyclerView.setLayoutManager(linearLayoutManager);
    mainTypeAdapter = new MainTypeAdapter(getActivity(), manufacturerId, mainTypes);
    mainTypeRecyclerView.setAdapter(mainTypeAdapter);
    mainTypeRecyclerView.setNestedScrollingEnabled(false);
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
  public void showMainTypes(List<MainType> mainTypes) {
    this.mainTypes.addAll(mainTypes);
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
    dismissProgressDialog();
    mainTypePresenter.onStop();
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
