package com.example.oopproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.Location;
import com.example.oopproject.LutemonStorage;
import com.example.oopproject.R;
import com.example.oopproject.models.Lutemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Map.Entry<Integer, Lutemon>> lutemonList;
    private LutemonStorage storage;
    private OnLutemonClickListener listener;

    public interface OnLutemonClickListener {
        void onLutemonClick(int id);
        void onLutemonSelected(int id, boolean isSelected);
    }

    public HomeAdapter(Context context, OnLutemonClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.storage = LutemonStorage.getInstance();
        updateLutemonList();
    }

    public void updateLutemonList() {
        lutemonList = getLutemonsInHome();
        notifyDataSetChanged();
    }

    private ArrayList<Map.Entry<Integer, Lutemon>> getLutemonsInHome() {
        ArrayList<Map.Entry<Integer, Lutemon>> homeList = new ArrayList<>();
        HashMap<Integer, Lutemon> allLutemons = storage.getAllLutemons();

        for (Map.Entry<Integer, Lutemon> entry : allLutemons.entrySet()) {
            if (entry.getValue().getLocation() == Location.HOME) {
                homeList.add(entry);
            }
        }

        return homeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lutemon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map.Entry<Integer, Lutemon> entry = lutemonList.get(position);
        int id = entry.getKey();
        Lutemon lutemon = entry.getValue();

        // Set Lutemon image based on color
        setLutemonImage(holder.imageViewLutemon, lutemon.getColor());

        // Set text data
        holder.textViewName.setText(lutemon.getName());
        holder.textViewType.setText("Type: " + lutemon.getColor());
        holder.textViewStats.setText("ATK: " + lutemon.getAttack() + " | HP: " + lutemon.getHealth() + "/" + lutemon.getMaxHealth());
        holder.textViewXpLevel.setText("XP: " + lutemon.getExperience() + " | LVL: " + lutemon.getLevel());

        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLutemonClick(id);
            }
        });

        holder.checkBoxSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onLutemonSelected(id, isChecked);
            }
        });
    }

    private void setLutemonImage(ImageView imageView, String color) {
        // Set appropriate image resource based on color
        // Example implementation - you'll need to create drawables for each color
        int resourceId;
        switch (color.toLowerCase()) {
            case "orange":
                resourceId = android.R.drawable.ic_menu_compass; // Placeholder
                break;
            case "black":
                resourceId = android.R.drawable.ic_menu_agenda; // Placeholder
                break;
            case "white":
                resourceId = android.R.drawable.ic_menu_view; // Placeholder
                break;
            case "green":
                resourceId = android.R.drawable.ic_menu_slideshow; // Placeholder
                break;
            case "pink":
                resourceId = android.R.drawable.ic_menu_gallery; // Placeholder
                break;
            default:
                resourceId = android.R.drawable.ic_menu_help; // Default placeholder
                break;
        }
        imageView.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return lutemonList != null ? lutemonList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLutemon;
        TextView textViewName, textViewType, textViewStats, textViewXpLevel;
        CheckBox checkBoxSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewLutemon = itemView.findViewById(R.id.imageViewLutemon);
            textViewName = itemView.findViewById(R.id.textView7);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewStats = itemView.findViewById(R.id.textViewStats);
            textViewXpLevel = itemView.findViewById(R.id.textViewXpLevel);
            checkBoxSelected = itemView.findViewById(R.id.checkBoxSelected);
        }
    }
}


