package eu.mobilebear.carcompany.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import eu.mobilebear.carcompany.R;
import eu.mobilebear.carcompany.mvp.model.Manufacturer;

/**
 * @author bartoszbanczerowski@gmail.com
 *         Created on 16.01.2017.
 */
public class ManufacturerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EVEN_ROW_TYPE = 0;
    private static final int ODD_ROW_TYPE = 1;
    private static final int PROGRESS_BAR_TYPE = 2;

    private List<Manufacturer> manufacturers;
    private Activity activity;

    public ManufacturerAdapter(@NonNull final Activity activity, @Nullable List<Manufacturer> manufacturers) {
        this.activity = activity;
        this.manufacturers = manufacturers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EVEN_ROW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manufacturer_item, parent, false);
                return new EvenViewHolder(view);
            case ODD_ROW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manufacturer_item, parent, false);
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
        return manufacturers.size();
    }

    private void configureProgressBarViewHolder() {

    }

    private void configureOddViewHolder(OddViewHolder oddViewHolder, final int position) {
        oddViewHolder.manufacturerName.setText(manufacturers.get(position).getManufacturers().get(position));
        oddViewHolder.backgroundLinearLayout.setBackgroundColor(oddViewHolder.color);
    }

    private void configureEvenViewHolder(EvenViewHolder evenViewHolder, final int position) {
        evenViewHolder.manufacturerName.setText(manufacturers.get(position).getManufacturers().get(position));
        evenViewHolder.backgroundLinearLayout.setBackgroundColor(evenViewHolder.color);
    }

    private class OddViewHolder extends RecyclerView.ViewHolder {

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

    private class EvenViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemBackgroundLinearLayout)
        LinearLayout backgroundLinearLayout;

        @BindView(R.id.manufacturersTextView)
        TextView manufacturerName;

        @BindColor(R.color.evenRow)
        int color;

        EvenViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
