package com.codingtive.dicodingmade.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.codingtive.dicodingmade.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {

    Button btnClose, btnChoose;
    RadioGroup rgOptions;
    RadioButton rb1, rb2, rb3;
    OnOptionDialogListener optionDialogListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnClose = view.findViewById(R.id.btn_close);
        btnChoose = view.findViewById(R.id.btn_choose);
        rgOptions = view.findViewById(R.id.rg_options);
        rb1 = view.findViewById(R.id.rb_1);
        rb2 = view.findViewById(R.id.rb_2);
        rb3 = view.findViewById(R.id.rb_3);

        btnChoose.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();

        if (fragment instanceof DetailCategoryFragment) {
            DetailCategoryFragment detailCategoryFragment = (DetailCategoryFragment) fragment;
            this.optionDialogListener = detailCategoryFragment.optionDialogListener;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.optionDialogListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                getDialog().cancel();
                break;
            case R.id.btn_choose:
                int checkedRadioButtonId = rgOptions.getCheckedRadioButtonId();
                if (checkedRadioButtonId != -1) {
                    String favorite = null;
                    switch (checkedRadioButtonId) {
                        case R.id.rb_1:
                            favorite = rb1.getText().toString().trim();
                            break;
                        case R.id.rb_2:
                            favorite = rb2.getText().toString().trim();
                            break;
                        case R.id.rb_3:
                            favorite = rb3.getText().toString().trim();
                            break;
                    }

                    if (optionDialogListener != null) {
                        optionDialogListener.onOptionChosen(favorite);
                    }
                    getDialog().dismiss();
                }
                break;
        }
    }

    public interface OnOptionDialogListener {
        void onOptionChosen(String text);
    }
}
