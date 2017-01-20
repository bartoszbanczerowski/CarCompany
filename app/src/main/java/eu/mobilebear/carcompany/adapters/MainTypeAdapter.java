package eu.mobilebear.carcompany.adapters;

import static eu.mobilebear.carcompany.utils.FragmentUtils.BUILT_DATES_FRAGMENT;

import android.app.Activity;
import android.support.annotation.NonNull;
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
import eu.mobilebear.carcompany.mvp.model.MainType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */

public class MainTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
    Filterable {

  private static final int EVEN_ROW_TYPE = 0;
  private static final int ODD_ROW_TYPE = 1;
  private static final int PROGRESS_BAR_TYPE = 2;

  private List<MainType> mainTypes;
  private List<MainType> originalMainTypes;
  private MainActivity activity;
  private String manufacturerId;

  public MainTypeAdapter(@NonNull final Activity activity, String manufacturerId,
      @Nullable List<MainType> mainTypes) {
    this.activity = (MainActivity) activity;
    this.mainTypes = mainTypes;
    this.originalMainTypes = mainTypes;
    this.manufacturerId = manufacturerId;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case EVEN_ROW_TYPE:
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.search_item, parent, false);
        return new EvenViewHolder(view);
      case ODD_ROW_TYPE:
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.search_item, parent, false);
        return new OddViewHolder(view);
      case PROGRESS_BAR_TYPE:
        break;
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
      case PROGRESS_BAR_TYPE:
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    return position % 2 == 1 ? EVEN_ROW_TYPE : ODD_ROW_TYPE;
  }

  @Override
  public int getItemCount() {
    return mainTypes.size();
  }

  private void configureOddViewHolder(OddViewHolder oddViewHolder,
      final int position) {
    oddViewHolder.manufacturerName.setText(mainTypes.get(position).getName());
    oddViewHolder.backgroundLinearLayout.setBackgroundColor(oddViewHolder.color);
    oddViewHolder.itemView.setOnClickListener(view -> {
      activity.replaceFragment(BUILT_DATES_FRAGMENT, manufacturerId,
          mainTypes.get(position).getId());
    });
  }

  private void configureEvenViewHolder(EvenViewHolder evenViewHolder,
      final int position) {
    evenViewHolder.manufacturerName.setText(mainTypes.get(position).getName());
    evenViewHolder.backgroundLinearLayout.setBackgroundColor(evenViewHolder.color);
    evenViewHolder.itemView.setOnClickListener(view -> {
      activity.replaceFragment(BUILT_DATES_FRAGMENT, manufacturerId,
          mainTypes.get(position).getId());
    });
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence filter) {
        FilterResults results = new FilterResults();
        List<MainType> filteredMainTypes = new ArrayList<>();

        if (!TextUtils.isEmpty(filter)) {
          filter = filter.toString();
          for (MainType mainType : originalMainTypes) {
            if (mainType.getName().contains(filter)) {
              filteredMainTypes.add(mainType);
            }
          }
          results.count = filteredMainTypes.size();
          results.values = filteredMainTypes;
        } else {
          results.count = originalMainTypes.size();
          results.values = originalMainTypes;
        }
        return results;
      }

      @Override
      protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        mainTypes = (List<MainType>) filterResults.values;
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
    TextView manufacturerName;

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
    TextView manufacturerName;

    @BindColor(R.color.evenRow)
    int color;

    EvenViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
