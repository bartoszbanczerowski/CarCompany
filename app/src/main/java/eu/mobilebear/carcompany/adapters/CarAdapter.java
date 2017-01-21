package eu.mobilebear.carcompany.adapters;

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
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.mvp.model.Car;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 21.01.2017.
 */

public class CarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int EVEN_ROW_TYPE = 0;
  private static final int ODD_ROW_TYPE = 1;

  private List<Car> cars;
  private List<Car> originalCars;

  public CarAdapter(@Nullable List<Car> cars) {
    this.cars = cars;
    this.originalCars = cars;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.car_item, parent, false);
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
    return cars.size();
  }

  private void configureOddViewHolder(OddViewHolder oddViewHolder,
      final int position) {
    oddViewHolder.manufacturerTextView.setText(cars.get(position).getManufacturer());
    oddViewHolder.mainTypeTextView.setText(cars.get(position).getMainType());
    oddViewHolder.buildDateTextView.setText(cars.get(position).getBuiltDate());
    oddViewHolder.backgroundLinearLayout.setBackgroundColor(oddViewHolder.color);
  }

  private void configureEvenViewHolder(EvenViewHolder evenViewHolder,
      final int position) {
    evenViewHolder.manufacturerTextView.setText(cars.get(position).getManufacturer());
    evenViewHolder.mainTypeTextView.setText(cars.get(position).getMainType());
    evenViewHolder.buildDateTextView.setText(cars.get(position).getBuiltDate());
    evenViewHolder.backgroundLinearLayout.setBackgroundColor(evenViewHolder.color);
  }

  class OddViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemBackgroundLinearLayout)
    LinearLayout backgroundLinearLayout;

    @BindView(R.id.manufacturerTextView)
    TextView manufacturerTextView;

    @BindView(R.id.mainTypeTextView)
    TextView mainTypeTextView;

    @BindView(R.id.builtDateTextView)
    TextView buildDateTextView;

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

    @BindView(R.id.manufacturerTextView)
    TextView manufacturerTextView;

    @BindView(R.id.mainTypeTextView)
    TextView mainTypeTextView;

    @BindView(R.id.builtDateTextView)
    TextView buildDateTextView;

    @BindColor(R.color.evenRow)
    int color;

    EvenViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
