package eu.mobilebear.carcompany.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.adapters.BuiltDateAdapter;
import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import eu.mobilebear.carcompany.mvp.view.BuiltDateView;
import eu.mobilebear.carcompany.utils.FragmentUtils;
import java.util.List;

public class BuiltDateFragment extends Fragment implements BuiltDateView {

  @BindView(R.id.mainTypeRecyclerView)
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
    if (getArguments() != null) {

    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_built_date, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }


  @Override
  public void showBuiltDates(List<BuiltDate> builtDates) {

  }

  @Override
  public void showLoading() {

  }

  @Override
  public void showError(String message) {

  }
}
