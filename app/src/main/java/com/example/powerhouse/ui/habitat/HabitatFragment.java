package com.example.powerhouse.ui.habitat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.powerhouse.databinding.FragmentHabitatBinding;


public class HabitatFragment extends Fragment {

    private FragmentHabitatBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HabitatViewModel HabitatViewModel =
                new ViewModelProvider(this).get(HabitatViewModel.class);

        binding = FragmentHabitatBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}