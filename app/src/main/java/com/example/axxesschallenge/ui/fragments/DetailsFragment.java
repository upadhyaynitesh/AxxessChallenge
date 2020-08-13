package com.example.axxesschallenge.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.axxesschallenge.R;
import com.example.axxesschallenge.model.ImageResponse;

public class DetailsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        ImageResponse imageResponse = (ImageResponse) bundle.getSerializable("imageResponse");

        System.out.println("Image response received==============" + imageResponse.toString());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}