package com.example.lab5;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImagesViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Image> mImages;
    private final Activity mActivity;

    public ImageListAdapter(Activity context, List<Image>images) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mImages = images;
        this.mActivity = context;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> mImages) {
        this.mImages = mImages;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View row = mLayoutInflater.inflate(R.layout.image_row, viewGroup, false);
        return new ImagesViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        Image image = mImages.get(position);
        holder.mTextName.setText(image.getName());
        holder.mButton.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder {
        TextView mTextName;
        Button mButton;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            mButton = itemView.findViewById(R.id.button);
            mTextName = itemView.findViewById(R.id.nazwa);

            addGoToDetailsViewListener();
        }

        private void addGoToDetailsViewListener() {
            mButton.setOnClickListener(view -> {
                Intent intent = new Intent(mActivity, BrowseActivity.class);
                int index = (Integer) mButton.getTag();
                Image image = mImages.get(index);
                intent.putExtra(BrowseActivity.CURRENT_IMAGE_KEY, image);
                intent.putExtra(BrowseActivity.SHOW_DETAILS_KEY, true);
                mActivity.finish();
                mActivity.startActivity(intent);
            });
        }

    }
}
