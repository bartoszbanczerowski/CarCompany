package eu.mobilebear.carcompany.adapters;

import static eu.mobilebear.carcompany.utils.FragmentUtils.MAIN_TYPES_FRAGMENT;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import eu.mobilebear.carcompany.mvp.model.MainType;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */

public class BuiltDateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int EVEN_ROW_TYPE = 0;
  private static final int ODD_ROW_TYPE = 1;
  private static final int PROGRESS_BAR_TYPE = 2;

  private List<BuiltDate> mainTypes;
  private MainActivity activity;

  public BuiltDateAdapter(@NonNull final Activity activity,
      @Nullable List<BuiltDate> mainTypes) {
    this.activity = (MainActivity) activity;
    this.mainTypes = mainTypes;
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

  private void configureProgressBarViewHolder() {

  }

  private void configureOddViewHolder(OddViewHolder oddViewHolder,
      final int position) {
    oddViewHolder.manufacturerName.setText(mainTypes.get(position).getName());
    oddViewHolder.backgroundLinearLayout.setBackgroundColor(oddViewHolder.color);
    oddViewHolder.itemView.setOnClickListener(view -> {
//      activity.replaceFragment(MAIN_TYPES_FRAGMENT, mainTypes.get(position).getId());
    });
  }

  private void configureEvenViewHolder(EvenViewHolder evenViewHolder,
      final int position) {
    evenViewHolder.manufacturerName.setText(mainTypes.get(position).getName());
    evenViewHolder.backgroundLinearLayout.setBackgroundColor(evenViewHolder.color);
    evenViewHolder.itemView.setOnClickListener(view -> {
//      activity.replaceFragment(MAIN_TYPES_FRAGMENT, mainTypes.get(position).getId(), null);
    });
  }

  class OddViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemBackgroundLinearLayout)
    LinearLayout backgroundLinearLayout;

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
