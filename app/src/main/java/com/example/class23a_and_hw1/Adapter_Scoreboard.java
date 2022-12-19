package com.example.class23a_and_hw1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.class23a_and_hw1.Fragments.MapFragment;
import com.example.class23a_and_hw1.databinding.ScoreBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link HighScore}.
 * TODO: Replace the implementation with code for your data type.
 */
public class Adapter_Scoreboard extends RecyclerView.Adapter<Adapter_Scoreboard.ViewHolder> {

    private final List<HighScore> mValues;


    public Adapter_Scoreboard(List<HighScore> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(ScoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mScoreView.setText(""+mValues.get(position).score);
        holder.mPlaceView.setText(""+mValues.get(position).place);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mScoreView;
        public final TextView mPlaceView;
        public HighScore mItem;

        public ViewHolder(ScoreBinding binding) {
            super(binding.getRoot());
            mNameView = binding.name;
            mScoreView = binding.score;
            mPlaceView = binding.place;

            View.OnClickListener cl = v -> {
                GoogleMap map = MapFragment.getMap();
                map.animateCamera(CameraUpdateFactory.newLatLng(mItem.getPosition()));
            };
            mNameView.setOnClickListener(cl);
            mScoreView.setOnClickListener(cl);
            mPlaceView.setOnClickListener(cl);
        }
    }
}