package com.example.anushai.phonecode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anushai on 12/20/17.
 */

public class OperatorLayout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View operatorView = inflater.inflate(R.layout.operator_layout,container,false);

        return operatorView;
    }
}
