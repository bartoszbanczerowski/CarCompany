package eu.mobilebear.carcompany.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import eu.mobilebear.carcompany.adapters.ManufacturerAdapter;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.mvp.model.Manufacturer;
import eu.mobilebear.carcompany.mvp.presenters.ManufacturerPresenter;
import eu.mobilebear.carcompany.mvp.view.ManufacturerView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class ManufacturersFragment extends Fragment implements ManufacturerView {

  @Inject
  ManufacturerPresenter manufacturerPresenter;

  @BindView(R.id.manufacturerRecyclerView)
  RecyclerView manufacturersRecyclerView;

  private Unbinder unbinder;
  private ManufacturerAdapter manufacturerAdapter;
  private List<Manufacturer> manufacturers;
  private boolean isLoading = false;
  private boolean isLastPage = false;

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

    View view = inflater.inflate(R.layout.fragment_manufacturers, container, false);
    unbinder = ButterKnife.bind(this, view);

    manufacturers = new ArrayList<>();
    Timber.d("Manufacturers: " + manufacturers.size());

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    manufacturersRecyclerView.setLayoutManager(linearLayoutManager);
    manufacturerAdapter = new ManufacturerAdapter(getActivity(), manufacturers);
    manufacturersRecyclerView.setAdapter(manufacturerAdapter);
    manufacturersRecyclerView.setNestedScrollingEnabled(false);
//    manufacturersRecyclerView.addOnScrollListener(new OnScrollListener() {
//      @Override
//      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
//        int visibleItemCount = linearLayoutManager.getChildCount();
//        int totalItemCount = linearLayoutManager.getItemCount();
//        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

//        if (!isLoading && !isLastPage) {
//          if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//              && firstVisibleItemPosition >= 0
//              && totalItemCount >= PAGE_SIZE) {
//            manufacturerPresenter.loadMoreItems();
//          }
//        }
//      }
//
//    });

    return view;
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
    isLoading = false;
  }

  @Override
  public void showLoading() {
    isLoading = true;
  }

  @Override
  public void showError(String message) {
    Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
  }

  private void injectDependencies() {
    if (getActivity() instanceof MainActivity) {
      MainActivity activity = (MainActivity) getActivity();
      CarComponent carComponent = activity.getCarComponent();
      carComponent.inject(this);
    }
  }
}
