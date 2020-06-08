package com.pokkt.demo.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pokkt.ad.demo.R;
import com.pokkt.sdk.pokktnativead.PokktNativeAd;
import com.pokkt.sdk.pokktnativead.PokktNativeAdLayout;

import java.util.ArrayList;
import java.util.List;

class PokktNativeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE = 0;
    private static final int NATIVE_AD_VIEW_TYPE = 1;
    private final List<Object> mRecyclerViewItems;
    private final Context mContext;

    PokktNativeRecyclerViewAdapter(Context context, ArrayList<Object> recyclerViewItems) {
        this.mRecyclerViewItems = recyclerViewItems;
        this.mContext = context;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;

        ItemViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
        }
    }

    public class PokktNativeItemViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout container;

        public FrameLayout getContainer() {
            return container;
        }

        PokktNativeItemViewHolder(View view) {
            super(view);
            container = (FrameLayout) view;
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = mRecyclerViewItems.get(position);
        if (recyclerViewItem instanceof PokktNativeAd) {
            return NATIVE_AD_VIEW_TYPE;
        }
        return ITEM_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case NATIVE_AD_VIEW_TYPE:
                View container = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.native_container,
                        viewGroup, false);
                return new PokktNativeItemViewHolder(container);
            case ITEM_VIEW_TYPE:
                // Fall through.
            default:
                View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.native_list_item, viewGroup, false);
                return new ItemViewHolder(itemLayoutView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NATIVE_AD_VIEW_TYPE:
                PokktNativeAd nativeAd = (PokktNativeAd) mRecyclerViewItems.get(position);
                nativeAd.setMediaView((PokktNativeAdLayout) ((PokktNativeItemViewHolder)holder).getContainer().findViewById(R.id.pokkt_native_ad),mContext);
                break;
            case ITEM_VIEW_TYPE:
                // fall through
            default:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                String item = (String) mRecyclerViewItems.get(position);
                itemViewHolder.itemName.setText(item);
        }
    }
}