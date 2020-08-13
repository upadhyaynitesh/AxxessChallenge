package com.example.axxesschallenge.ui.fragments;

import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.axxesschallenge.R;
import com.example.axxesschallenge.database.AppDatabase;
import com.example.axxesschallenge.database.ImageDBEntity;
import com.example.axxesschallenge.databinding.FragmentDetailsBinding;
import com.example.axxesschallenge.model.ImageResponse;
import com.example.axxesschallenge.utils.Constants;
import com.example.axxesschallenge.viewmodel.AxxessViewModel;

import kotlinx.coroutines.GlobalScope;

public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding dataBinding;
    private ImageResponse imageResponse;
    AxxessViewModel axxessViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        dataBinding.setLifecycleOwner(getViewLifecycleOwner());

        Bundle bundle = getArguments();
        imageResponse = (ImageResponse) bundle.getSerializable("imageResponse");

        Toolbar toolbar = (Toolbar) dataBinding.getRoot().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(imageResponse.getTitle());
        toolbar.setNavigationIcon(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(view -> requireActivity().onBackPressed());

        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        String userComment = axxessViewModel.getUserComment(imageResponse.getAccount_id());
//        if (userComment != null) {
//            dataBinding.editTextComment.setText(userComment);
//        }

        dataBinding.setImageResponse(imageResponse);
        dataBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = dataBinding.editTextComment.getText().toString().trim();
                ImageDBEntity imageDBEntity = new ImageDBEntity(imageResponse.getAccount_id(), imageResponse.getImages().get(0).getLink(),
                        comment);
//                long recordInserted = axxessViewModel.storeUserComment(imageDBEntity);
//                System.out.println("Inserted record Id=============" + recordInserted);
            }
        });
    }
}