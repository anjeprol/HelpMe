package com.pramont.helpme.Fragments;


import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.pramont.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class Contacts extends Fragment implements View.OnClickListener{
    private LinearLayout.LayoutParams mLayoutParams;
    LinearLayout.LayoutParams params;
    LinearLayout container_contacts_lly;
    LinearLayout email_container;
    private TextView email_tv;
    private TextView phone_tv;
    private Button addBtn;
    private Button rmvBtn;
    int co = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Params for text levels, margins and layOuts
        params = new LinearLayout.LayoutParams(500,mLayoutParams.WRAP_CONTENT);
        params.setMargins(50,0,50,0);



        //To set the main layout container
        container_contacts_lly = (LinearLayout) rootView.findViewById(R.id.container_contacts);

        // To set the spaces
        LinearLayout space_lly = new LinearLayout(getContext());
        space_lly = getSpaceLayout(space_lly);








        LinearLayout hidden_buttons_lly = (LinearLayout) rootView.findViewById(R.id.hiddenButtons);


        View buttonsViews = getLayoutInflater(savedInstanceState).inflate(R.layout.hidden_buttons, container_contacts_lly, false);
        addBtn = (Button) buttonsViews.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(this);



        container_contacts_lly.addView(space_lly);



       // container_contacts_lly.addView(space_lly);
        container_contacts_lly.addView(buttonsViews);
        return rootView;
    }

    /*
    * Method to set the setting for space
    * */

    private LinearLayout getSpaceLayout(LinearLayout linearLayout) {
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        mLayoutParams.MATCH_PARENT, 70));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.background, null));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    /*
    * To set the email container layout
    * */

    private LinearLayout getEmailContLayout(LinearLayout linearLayout, int count){
        linearLayout.setId(count);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        mLayoutParams.MATCH_PARENT,mLayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white,null));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    /*
    * To set the text labels from email and phone
    * */
    private TextView getTextViewLabels(TextView textView, int text, LinearLayout.LayoutParams params){
        textView = new TextView(getContext());
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        return textView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_btn:
                email_tv = getTextViewLabels(email_tv,R.string.email,params);
                phone_tv = getTextViewLabels(phone_tv,R.string.phone,params);

                email_container = new LinearLayout(getContext());
                email_container = getEmailContLayout(email_container,view.getId()+co);

                email_container.addView(email_tv);
                email_container.addView(phone_tv);

                container_contacts_lly.addView(email_container);
                co++;
                break;
        }
    }
}
