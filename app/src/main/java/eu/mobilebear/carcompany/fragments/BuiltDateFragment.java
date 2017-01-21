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
import eu.mobilebear.carcompany.adapters.BuiltDateAdapter;
import eu.mobilebear.carcompany.injection.annotations.CarPreferences;
import eu.mobilebear.carcompany.injection.annotations.GetCars;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import eu.mobilebear.carcompany.mvp.model.Search;
import eu.mobilebear.carcompany.mvp.model.SearchCriteria;
import eu.mobilebear.carcompany.mvp.presenters.BuiltDatePresenter;
import eu.mobilebear.carcompany.mvp.view.BuiltDateView;
import eu.mobilebear.carcompany.utils.FragmentUtils;
import io.realm.Realm;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class BuiltDateFragment extends Fragment implements BuiltDateView {

  @Inject
  BuiltDatePresenter builtDatePresenter;

  @Inject
  @GetCars
  Realm realm;

  @Inject
  @CarPreferences
  SharedPreferences carPreferences;

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
  private String manufacturerId;
  private String mainTypeId;

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
      manufacturerId = getArguments().getString(FragmentUtils.MANUFACTURER_FRAGMENT);
      mainTypeId = getArguments().getString(FragmentUtils.MAIN_TYPES_FRAGMENT);
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
    mainTypeAdapter = new BuiltDateAdapter(getActivity(), builtDates, carPreferences);
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
    builtDatePresenter.onStop();
  }


  @OnClick(R.id.searchButton)
  void searchCars() {
    RealmList<Search> manufacturersSearches = new RealmList<>();
    RealmList<Search> mainTypesSearches = new RealmList<>();
    manufacturersSearches.add(new Search(manufacturerId));
    mainTypesSearches.add(new Search(mainTypeId));
    RealmList<Search> builtDatesSearches = new RealmList<>();
    for (BuiltDate builtDate : builtDates) {
      if (builtDate.isCheckedForSearch()) {
        builtDatesSearches.add(new Search(builtDate.getId()));
      }
    }

    if (!builtDatesSearches.isEmpty()) {
      realm.executeTransaction(
          realm1 -> realm1.copyToRealmOrUpdate(new SearchCriteria(manufacturersSearches,
              mainTypesSearches, builtDatesSearches)));
      getActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, CarSearchFragment.newInstance(), CAR_SEARCH_FRAGMENT)
          .addToBackStack(CAR_SEARCH_FRAGMENT)
          .commit();
    } else {
      showError("You have to select at least one built date.");
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
