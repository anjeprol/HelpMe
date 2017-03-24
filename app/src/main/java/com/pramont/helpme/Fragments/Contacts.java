package com.pramont.helpme.Fragments;


import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.pramont.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class Contacts extends Fragment implements View.OnClickListener {
    private LinearLayout.LayoutParams mLayoutParams;
    LinearLayout.LayoutParams mParamsFieldsTv;
    LinearLayout.LayoutParams mParamsFieldsEt;
    LinearLayout mContainer_contacts_lly;
    LinearLayout mEmail_container;
    LinearLayout mSpace_lly;
    private TextView mEmail_tv;
    private TextView mPhone_tv;
    private EditText mEmail_et;
    private EditText mPhone_et;
    private Button mAddBtn;
    private Button mRmvBtn;
    private int mId = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        //To set the main layout container
        mContainer_contacts_lly = (LinearLayout) rootView.findViewById(R.id.container_contacts);

        //To load the buttons add and remove
        LinearLayout hidden_buttons_lly = (LinearLayout) rootView.findViewById(R.id.hiddenButtons);
        View buttonsViews = getLayoutInflater(savedInstanceState).inflate(R.layout.hidden_buttons, mContainer_contacts_lly, false);
        mAddBtn = (Button) buttonsViews.findViewById(R.id.add_btn);
        mAddBtn.setOnClickListener(this);

        // To set the spaces
        addSpaces();


        // mContainer_contacts_lly.addView(mSpace_lly);
        mContainer_contacts_lly.addView(buttonsViews);

        addSpaces();
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

    private LinearLayout getEmailContLayout(LinearLayout linearLayout, int count) {
        linearLayout.setId(count);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        mLayoutParams.MATCH_PARENT, mLayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white, null));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.add_btn:
                loadContactsFields();
                break;
        }
    }

    /*
    * method to load all the fields per contact
    * */
    private void loadContactsFields() {

        // Params for text levels, margins and layOuts
        mParamsFieldsTv = new LinearLayout.LayoutParams(180, mLayoutParams.WRAP_CONTENT);
        mParamsFieldsTv.setMargins(50, 0, 50, 0);

        // Params for text levels, margins and layOuts
        mParamsFieldsEt = new LinearLayout.LayoutParams(mLayoutParams.WRAP_CONTENT, 200);

       // mPhone_tv = getView(mPhone_tv, R.string.phone, mParamsFieldsTv);


       // mEmail_container.addView(mPhone_tv);
        addEmailField();


        addSpaces();
        mId++;
    }

    private void addEmailField(){
        mEmail_tv = getView(mEmail_tv, R.string.email, mParamsFieldsTv);
        mEmail_container = new LinearLayout(getContext());
        mEmail_container = getEmailContLayout(mEmail_container, mId);

        mEmail_container.addView(mEmail_tv);

        mEmail_et = getView(mEmail_et,
                R.string.email_hint, mParamsFieldsEt,
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                mId);

        mEmail_container.addView(mEmail_et);
        mContainer_contacts_lly.addView(mEmail_container);
    }

    /*
    * Method to add spaces into the views
    * */

    private void addSpaces() {
        // To set the spaces
        mSpace_lly = new LinearLayout(getContext());
        mSpace_lly = getSpaceLayout(mSpace_lly);
        mContainer_contacts_lly.addView(mSpace_lly);
    }

    /*
    * Method to set the text labels from email and phone
    * */
    private TextView getView(TextView textView, int idString, LinearLayout.LayoutParams params) {
        textView = new TextView(getContext());
        textView.setLayoutParams(params);
        textView.setText(idString);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        return textView;
    }

    /*
    *  Method to set the edit text
    * */
    private EditText getView(EditText editText, int idHintString,LinearLayout.LayoutParams params, int inputType, int id){
        editText = new EditText(getContext());
        editText.setLayoutParams(params);
        editText.setHint(idHintString);
        editText.setBackground(null);
        editText.setInputType(inputType);
        editText.setId(id);
        return  editText;
    }
}
