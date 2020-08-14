package com.example.axxesschallenge.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.axxesschallenge.R;
import com.example.axxesschallenge.database.AppDatabase;
import com.example.axxesschallenge.database.ImageDBEntity;
import com.example.axxesschallenge.databinding.FragmentDetailsBinding;
import com.example.axxesschallenge.model.ImageResponse;
import com.example.axxesschallenge.utils.Constants;
import com.example.axxesschallenge.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding dataBinding;
    private ImageResponse imageResponse;
    AppDatabase appDatabase;
    public final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        dataBinding.setLifecycleOwner(getViewLifecycleOwner());

        Bundle bundle = getArguments();
        if (bundle != null) {
            imageResponse = (ImageResponse) bundle.getSerializable("imageResponse");
            dataBinding.setImageResponse(imageResponse);

            Toolbar toolbar = dataBinding.getRoot().findViewById(R.id.toolbar);
            ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
            /*Set Tool bar title to Image title user is seeing currently.*/
            toolbar.setTitle(imageResponse.getTitle());
            toolbar.setNavigationIcon(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_baseline_arrow_back_24));
            toolbar.setNavigationOnClickListener(view -> requireActivity().onBackPressed());
        }
        dataBinding.executePendingBindings();
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (imageResponse == null) {
            Utils.showAlertDialog(
                    requireActivity(),
                    getString(R.string.error_title),
                    getString(R.string.no_data_detail_screen_message),
                    getString(R.string.ok_button));
            return;
        }
        /*Instantiate application object, required to run executor for performing database insert/retrieval operations.*/
        /*Create database object*/
        appDatabase = Room.databaseBuilder(requireActivity(), AppDatabase.class, Constants.DATABASE_NAME).build();

        /*Check in db if user has made some comments to this image. If yes, set the comment in edittext.*/
        executorService.execute(() -> {
            String userComment = appDatabase.imageDao().retrieveImageComment(imageResponse.getAccount_id());
            if (userComment != null && userComment.trim().length() > 0) {
                requireActivity().runOnUiThread(() -> {
                    dataBinding.editTextComment.setText(userComment);
                    dataBinding.editTextComment.setSelection(userComment.length());
                });
            }
        });

        dataBinding.btnSubmit.setOnClickListener(view1 -> {
            String comment = dataBinding.editTextComment.getText().toString().trim();
            ImageDBEntity imageDBEntity = new ImageDBEntity(imageResponse.getAccount_id(), imageResponse.getImages().get(0).getLink(),
                    comment);
            /*Store comment made by user in db for the particular image.*/
            executorService.execute(() -> {
                long recordInserted = appDatabase.imageDao().insertImageComment(imageDBEntity);
                if (recordInserted > 0) {
                    requireActivity().runOnUiThread(() -> Toast.makeText(requireActivity(), "Comment saved.", Toast.LENGTH_LONG).show());
                }
            });
        });
    }
}