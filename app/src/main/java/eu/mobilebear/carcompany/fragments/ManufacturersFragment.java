package eu.mobilebear.carcompany.fragments;

import static eu.mobilebear.carcompany.utils.FragmentUtils.CAR_SEARCH_FRAGMENT;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import eu.mobilebear.carcompany.adapters.ManufacturerAdapter;
import eu.mobilebear.carcompany.injection.annotations.CarPreferences;
import eu.mobilebear.carcompany.injection.annotations.GetCars;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.mvp.model.Manufacturer;
import eu.mobilebear.carcompany.mvp.model.Search;
import eu.mobilebear.carcompany.mvp.model.SearchCriteria;
import eu.mobilebear.carcompany.mvp.presenters.ManufacturerPresenter;
import eu.mobilebear.carcompany.mvp.view.ManufacturerView;
import io.realm.Realm;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class ManufacturersFragment extends Fragment implements ManufacturerView {

  @Inject
  ManufacturerPresenter manufacturerPresenter;

  @Inject
  @CarPreferences
  SharedPreferences carSharedPreferences;

  @Inject
  @GetCars
  Realm realm;

  @BindView(R.id.itemsRecyclerView)
  RecyclerView manufacturersRecyclerView;

  @BindView(R.id.searchButton)
  Button searchButton;

  @BindView(R.id.filterEditText)
  EditText filterEditText;

  private Unbinder unbinder;
  private ManufacturerAdapter manufacturerAdapter;
  private List<Manufacturer> manufacturers;
  private ProgressDialog progressDialog;

  public static ManufacturersFragment newInstance() {
    return new ManufacturersFragment();
  }

  public ManufacturersFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    injectDependencies();
    manufacturerPresenter.attachView(this);
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
    manufacturers = new ArrayList<>();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    manufacturersRecyclerView.setLayoutManager(linearLayoutManager);
    manufacturerAdapter = new ManufacturerAdapter(getActivity(), manufacturers,
        carSharedPreferences);
    manufacturersRecyclerView.setAdapter(manufacturerAdapter);
    manufacturersRecyclerView.setNestedScrollingEnabled(false);
    filterEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        manufacturerAdapter.getFilter().filter(charSequence);
      }

      @Override
      public void afterTextChanged(Editable editable) {
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();
    manufacturerPresenter.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
    manufacturerPresenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    manufacturerPresenter.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    manufacturerPresenter.onStop();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void showManufacturers(List<Manufacturer> manufacturers) {
    this.manufacturers.addAll(manufacturers);
    manufacturerAdapter.notifyDataSetChanged();
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
    dismissProgressDialog();
    Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
  }

  @OnClick(R.id.searchButton)
  void searchCars() {
    RealmList<Search> manufacturerSearches = new RealmList<>();
    for (Manufacturer manufacturer : manufacturers) {
      if (manufacturer.isCheckedForSearch()) {
        manufacturerSearches.add(new Search(manufacturer.getId()));
      }
    }

    if (!manufacturerSearches.isEmpty()) {
      realm.executeTransaction(
          realm1 -> realm1.copyToRealmOrUpdate(new SearchCriteria(manufacturerSearches)));
      getActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, CarSearchFragment.newInstance(), CAR_SEARCH_FRAGMENT)
          .addToBackStack(CAR_SEARCH_FRAGMENT)
          .commit();
    } else {
      showError("You have to select at least one manufacturer.");
    }

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
