package com.pramont.helpme.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pramont.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Buttons extends Fragment implements View.OnClickListener {

    private ImageButton mImgButton;
    private TextView    mMessage_tv;
    private boolean isFirst = true;

    public Buttons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buttons, container, false);

        mImgButton  = (ImageButton) rootView.findViewById(R.id.imgBtn);
        mMessage_tv = (TextView) rootView.findViewById(R.id.tv);

        mImgButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imgBtn:
                if (isFirst)
                {
                    mImgButton.setImageResource(R.drawable.ic_green_button);
                    mMessage_tv.setText(getString(R.string.active));
                    isFirst=false;
                }else {
                    mImgButton.setImageResource(R.drawable.ic_red_button);
                    mMessage_tv.setText(getString(R.string.press_it));
                    isFirst=true;
                }
                break;
        }
    }
}
