package eu.mobilebear.carcompany.adapters;

import static eu.mobilebear.carcompany.utils.FragmentUtils.BUILT_DATES_FRAGMENT;
import static eu.mobilebear.carcompany.utils.FragmentUtils.CAR_FRAGMENT;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.injection.annotations.CarPreferences;
import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */

public class BuiltDateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
    Filterable {

  private static final int EVEN_ROW_TYPE = 0;
  private static final int ODD_ROW_TYPE = 1;

  private List<BuiltDate> originalBuiltDates;
  private List<BuiltDate> builtDates;
  private MainActivity activity;

  @CarPreferences
  private SharedPreferences sharedPreferences;

  public BuiltDateAdapter(Activity activity, @Nullable List<BuiltDate> builtDates,
      @CarPreferences SharedPreferences sharedPreferences) {
    this.activity = (MainActivity) activity;
    this.builtDates = builtDates;
    this.originalBuiltDates = builtDates;
    this.sharedPreferences = sharedPreferences;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.search_item, parent, false);
    switch (viewType) {
      case EVEN_ROW_TYPE:
        return new EvenViewHolder(view);
      case ODD_ROW_TYPE:
        return new OddViewHolder(view);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch (holder.getItemViewType()) {
      case EVEN_ROW_TYPE:
        EvenViewHolder evenViewHolder = (EvenViewHolder) holder;
        configureEvenViewHolder(evenViewHolder, position);
        break;
      case ODD_ROW_TYPE:
        OddViewHolder oddViewHolder = (OddViewHolder) holder;
        configureOddViewHolder(oddViewHolder, position);
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    return position % 2 == 1 ? EVEN_ROW_TYPE : ODD_ROW_TYPE;
  }

  @Override
  public int getItemCount() {
    return builtDates.size();
  }

  private void configureOddViewHolder(OddViewHolder oddViewHolder,
      final int position) {
    oddViewHolder.name.setText(builtDates.get(position).getName());
    oddViewHolder.backgroundLinearLayout.setBackgroundColor(oddViewHolder.color);
    oddViewHolder.itemView.setOnClickListener(view -> {
      sharedPreferences.edit()
          .putString(BUILT_DATES_FRAGMENT, builtDates.get(position).getName())
          .apply();
      activity.replaceFragment(CAR_FRAGMENT, null, null);
    });
    oddViewHolder.searchItemCheckBox.setOnCheckedChangeListener((compoundButton, b) ->
        builtDates.get(position).setCheckedForSearch(compoundButton.isChecked()));
  }

  private void configureEvenViewHolder(EvenViewHolder evenViewHolder,
      final int position) {
    evenViewHolder.name.setText(builtDates.get(position).getName());
    evenViewHolder.backgroundLinearLayout.setBackgroundColor(evenViewHolder.color);
    evenViewHolder.itemView.setOnClickListener(view -> {
      sharedPreferences.edit()
          .putString(BUILT_DATES_FRAGMENT, builtDates.get(position).getName())
          .apply();
      activity.replaceFragment(CAR_FRAGMENT, null, null);
    });
    evenViewHolder.searchItemCheckBox.setOnCheckedChangeListener((compoundButton, b) ->
        builtDates.get(position).setCheckedForSearch(compoundButton.isChecked()));
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence filter) {
        FilterResults results = new FilterResults();
        List<BuiltDate> filteredBuiltDates = new ArrayList<>();

        if (!TextUtils.isEmpty(filter)) {
          filter = filter.toString();
          for (BuiltDate builtDate : originalBuiltDates) {
            if (builtDate.getName().contains(filter)) {
              filteredBuiltDates.add(builtDate);
            }
          }
          results.count = filteredBuiltDates.size();
          results.values = filteredBuiltDates;
        } else {
          results.count = originalBuiltDates.size();
          results.values = originalBuiltDates;
        }
        return results;
      }

      @Override
      protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        builtDates = (List<BuiltDate>) filterResults.values;
        notifyDataSetChanged();
      }
    };
  }

  class OddViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemBackgroundLinearLayout)
    LinearLayout backgroundLinearLayout;

    @BindView(R.id.searchItemCheckBox)
    CheckBox searchItemCheckBox;

    @BindView(R.id.nameTextView)
    TextView name;

    @BindColor(R.color.oddRow)
    int color;

    OddViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  class EvenViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemBackgroundLinearLayout)
    LinearLayout backgroundLinearLayout;

    @BindView(R.id.searchItemCheckBox)
    CheckBox searchItemCheckBox;

    @BindView(R.id.nameTextView)
    TextView name;

    @BindColor(R.color.evenRow)
    int color;

    EvenViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
