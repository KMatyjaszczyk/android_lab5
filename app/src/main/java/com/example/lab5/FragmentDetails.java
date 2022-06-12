package com.example.lab5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class FragmentDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_details, container, false);
    }

    public void setImage(Image image) {
        setProperImageUriInImageView(image);
        addComeBackToBrowseActivityListener();
    }

    private void setProperImageUriInImageView(Image image) {
        Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                Long.toString(image.id));
        ImageView imageView = getView().findViewById(R.id.imageView);
        imageView.setImageURI(imageUri);
    }

    private void addComeBackToBrowseActivityListener() {
        Button backButton = getView().findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), BrowseActivity.class);
            intent.putExtra(BrowseActivity.SHOW_DETAILS_KEY, false);
            getActivity().startActivity(intent);
            getActivity().finish();

        });
    }
}