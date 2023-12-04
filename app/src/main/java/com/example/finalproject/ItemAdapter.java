package com.example.finalproject;

// Rowan Conway

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.databinding.CityEntryBinding;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    // region adapterMethods
    private List<City> cityList;
    public ItemAdapter(List<City> cityList)
    {

        this.cityList = cityList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        CityEntryBinding binding = CityEntryBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ItemViewHolder(binding, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
    // endregion

    // region recycle methods
    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        private CityEntryBinding binding;
        private Context context;
        private City city;
        public ItemViewHolder(CityEntryBinding binding, Context context)
        {
            // sets up the context and variables. Then it sets up a click listener. creates intent so the DetailsActivity can begin.
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            binding.cityInfo.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("city", city);
                context.startActivity(intent);
            });
        }
        public void bind(City city)
        {
            this.city = city;
            binding.cityInfo.setText(context.getString(R.string.city_info, city.name, city.population));
        }
    }
    // endregion
}
