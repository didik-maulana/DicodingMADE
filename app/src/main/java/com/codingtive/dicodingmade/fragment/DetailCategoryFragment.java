package com.codingtive.dicodingmade.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;
import com.codingtive.dicodingmade.intent.IntentActivity;

public class DetailCategoryFragment extends Fragment implements View.OnClickListener {

    TextView tvCategoryTitle, tvCategoryDesc;
    Button btnToIntent, btnShowDialog;

    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_DESC = "extra_desc";
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String categoryTitle = getArguments() != null ? getArguments().getString(EXTRA_TITLE) : "";
        tvCategoryTitle.setText(categoryTitle);
        tvCategoryDesc.setText(getDescription());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCategoryTitle = view.findViewById(R.id.tv_category_title);
        tvCategoryDesc = view.findViewById(R.id.tv_category_desc);
        btnToIntent = view.findViewById(R.id.btn_to_intent);
        btnShowDialog = view.findViewById(R.id.btn_show_dialog);

        btnToIntent.setOnClickListener(this);
        btnShowDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_intent:
                Intent intent = new Intent(getActivity(), IntentActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_show_dialog:
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                customDialogFragment.show(fragmentManager, CustomDialogFragment.class.getSimpleName());
                break;
        }
    }

    CustomDialogFragment.OnOptionDialogListener optionDialogListener = new CustomDialogFragment.OnOptionDialogListener() {
        @Override
        public void onOptionChosen(String text) {
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    };
}
