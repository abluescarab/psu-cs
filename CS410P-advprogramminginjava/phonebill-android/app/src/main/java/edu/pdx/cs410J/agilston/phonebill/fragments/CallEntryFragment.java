package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.pdx.cs410J.agilston.phonebill.R;

public class CallEntryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_list, container, false);

        // set the view adapter for the RecyclerView
        if(view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView)view;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);

            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation()));
            recyclerView.setLayoutManager(layoutManager);
        }

        return view;
    }
}
