package com.example.powerhouse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Header#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Header extends Fragment {


    private String title;

    public Header() {
        // Required empty public constructor
    }

    public static Header newInstance(String title) {
        Header fragment = new Header();
        Bundle args = new Bundle();
        args.putString("title" ,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header, container, false);
    }
}