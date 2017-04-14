package com.pramont.helpme.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.pramont.helpme.Pojos.NotificationSettings.Contact;
import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;
import com.pramont.helpme.Utils.Preferences;
import com.pramont.helpme.Utils.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class Contacts extends Fragment implements View.OnClickListener {
    private LinearLayout.LayoutParams mLayoutParams;
    LinearLayout.LayoutParams mParamsFieldsTv;
    LinearLayout.LayoutParams mParamsFieldsEt;
    LinearLayout mContainer_contacts_lly;
    LinearLayout mEmail_container;
    LinearLayout mPhone_container;
    LinearLayout mSpace_lly;
    private TextView mEmail_tv;
    private TextView mPhone_tv;
    private EditText mEmail_et;
    private EditText mPhone_et;
    private Button mAddBtn;
    private Button mRmvBtn;
    private int mCountViews = 0;
    private static final int ID_LL_EMAIL = 100;
    private static final int ID_LL_PHONE = 200;
    private static final int ID_ET_EMAIL = 300;
    private static final int ID_ET_PHONE = 400;
    private static final int ID_LL_SP = 500;
    private boolean mIsEmail = false;
    private View mRootView;
    private UserSettings mProfile;
    private BroadcastReceiver mEmailReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIsEmail = intent.getBooleanExtra(Constants.CHECKED_EMAIL, false);
            changeVisibility();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        //To set the main layout container
        mContainer_contacts_lly = (LinearLayout) mRootView.findViewById(R.id.container_contacts);

        //To load the buttons add and remove
        LinearLayout hidden_buttons_lly = (LinearLayout) mRootView.findViewById(R.id.hiddenButtons);
        View buttonsViews = getLayoutInflater(savedInstanceState).inflate(R.layout.hidden_buttons, mContainer_contacts_lly, false);
        mAddBtn = (Button) buttonsViews.findViewById(R.id.add_btn);
        mRmvBtn = (Button) buttonsViews.findViewById(R.id.rmv_btn);

        mRmvBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);

        mContainer_contacts_lly.addView(buttonsViews);

        loadData();
        changeVisibility();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mEmailReceiver, new IntentFilter(Constants.BROADCAST));

        return mRootView;
    }

    /*
    * Method to load the preferences from sharedPreferences
    * */
    private void loadData() {
        EditText emailTo_et;
        EditText phoneTo_et;
        mProfile = new Utils()
                .getUserData(
                        new Preferences(getActivity()
                                .getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)));
        mIsEmail = mProfile.isEmailChecked();
        for (int index = 0; index < mProfile.getContacts().getPhoneNumbers().size(); index++)
        {
            mCountViews = index;
            loadContactsFields();
            emailTo_et = (EditText) mRootView.findViewById(ID_ET_EMAIL + index);
            phoneTo_et = (EditText) mRootView.findViewById(ID_ET_PHONE + index);
            if (index < mProfile.getContacts().getMailTo().size())
            {
                emailTo_et.setText(mProfile.getContacts().getMailTo().get(index));
            }
            phoneTo_et.setText(Long.toString(mProfile.getContacts().getPhoneNumbers().get(index)));
        }
    }

    /*
    * Method to change the visibility according the status for the email switch, hiding or showing
    * the email layout
    * */
    private void changeVisibility() {
        LinearLayout emailContainer_ll;
        if (mIsEmail)
        {
            for (int index = 0; index < mCountViews; index++)
            {
                emailContainer_ll = (LinearLayout) mRootView.findViewById(ID_LL_EMAIL + index);
                emailContainer_ll.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            for (int index = 0; index < mCountViews; index++)
            {
                emailContainer_ll = (LinearLayout) mRootView.findViewById(ID_LL_EMAIL + index);
                emailContainer_ll.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mEmailReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        Contact contact = new Contact();
        ArrayList<Long> phones_al = new ArrayList<>();
        ArrayList<String> emails_al = new ArrayList<>();
        EditText phones;
        EditText emails;

        mProfile = new Utils()
                .getUserData
                        (new Preferences(
                                getActivity().
                                        getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)));

        String tmp = new String();
        for (int index = 0; index < mCountViews; index++)
        {
            phones = (EditText) mRootView.findViewById(ID_ET_PHONE + index);
            tmp = phones.getText().toString().trim();
            if (!tmp.isEmpty())
            {
                phones_al.add(index, Long.parseLong(tmp));
            }

            if (mProfile.isEmailChecked())
            {
                emails = (EditText) mRootView.findViewById(ID_ET_EMAIL + index);
                tmp = emails.getText().toString().trim();
                if (!tmp.isEmpty())
                {
                    emails_al.add(index, tmp);
                }
            }
        }
        contact.setMailTo(emails_al);
        contact.setPhoneNumbers(phones_al);
        mProfile.setContacts(contact);

        //Setting the sharedPreference in order to send the user information for sharedPreferences
        Preferences preferences = new Preferences(
                getContext()
                        .getSharedPreferences(Constants.PREFERENCES, getContext().MODE_PRIVATE));
        //Saving the whole structure from user preferences, at this point al the values should be
        //into the structure for settings fragment
        preferences.setPreferences(mProfile);
    }

    /*
    * Method to set the setting for space
    * */

    private LinearLayout getSpaceLayout(LinearLayout linearLayout, int id) {
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        mLayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.space_dimen)));
        linearLayout.setBackgroundResource(R.color.background);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setId(id);
        return linearLayout;
    }

    /*
    * To set the email and phone container layout
    * */

    private LinearLayout getContainerLayout(LinearLayout linearLayout, int id) {
        linearLayout.setId(id);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        mLayoutParams.MATCH_PARENT, mLayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundResource(R.color.white);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    @Override
    public void onClick(View view) {
        Integer contacts_limit = getResources().getInteger(R.integer.max_contacts);
        StringBuilder stringBuilderMsg = new StringBuilder();
        stringBuilderMsg
                .append(contacts_limit)
                .append(Constants.DEFAULT_VALUE)
                .append(getResources().getString(R.string.contacts_limit));

        switch (view.getId())
        {
            case R.id.add_btn:
                if (mCountViews < contacts_limit)
                {
                    loadContactsFields();
                }
                else
                {
                    Toast.makeText(getContext(), stringBuilderMsg.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rmv_btn:
                if (mCountViews > 0)
                {
                    removeContactsFields();
                }
                else
                {
                    Toast.makeText(getContext(), R.string.no_contacts_msg, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /*
    * Method to delete the Linear Layouts from email and phone
    * */
    private void removeContactsFields() {
        int id_ll_email = ID_LL_EMAIL + mCountViews - 1;
        int id_ll_phone = ID_LL_PHONE + mCountViews - 1;
        int id_ll_space = ID_LL_SP + mCountViews - 1;

        deleteView(id_ll_email);
        deleteView(id_ll_phone);
        deleteView(id_ll_space);

        mCountViews--;
    }

    private void deleteView(int id) {
        View viewToDelete = mContainer_contacts_lly.findViewById(id);
        ViewGroup parent = (ViewGroup) viewToDelete.getParent();
        parent.removeView(viewToDelete);
    }

    /*
        * method to load all the fields per contact
        * */
    private void loadContactsFields() {


        // Params for text levels, margins and layOuts
        mParamsFieldsTv = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.width), mLayoutParams.WRAP_CONTENT);
        mParamsFieldsTv.setMargins((int) getResources().getDimension(R.dimen.margin_right), 0, (int) getResources().getDimension(R.dimen.margin_right), 0);

        // Params for text levels, margins and layOuts
        mParamsFieldsEt = new LinearLayout.LayoutParams(mLayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen.height));

        addEmailField();
        addPhoneField();
        addSpaces();

        mCountViews++;
    }

    /*
    * Method to add the LinearLayout for phone
    * */
    private void addPhoneField() {
        mPhone_tv = getView(mPhone_tv, R.string.phone, mParamsFieldsTv);
        mPhone_container = new LinearLayout(getContext());
        mPhone_container = getContainerLayout(mPhone_container, ID_LL_PHONE + mCountViews);
        mPhone_container.addView(mPhone_tv);

        mPhone_et = getView(mPhone_et,
                R.string.phone_hint,
                mParamsFieldsEt,
                InputType.TYPE_CLASS_PHONE,
                ID_ET_PHONE + mCountViews);
        //To set the maxLength from edit text, in this case for 10 numbers
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(Constants.MAX_LENG);
        mPhone_et.setFilters(FilterArray);

        mPhone_container.addView(mPhone_et);
        mContainer_contacts_lly.addView(mPhone_container);
    }

    /*
    * Method to add the LinearLayout for emails
    * */
    private void addEmailField() {
        mEmail_tv = getView(mEmail_tv, R.string.email, mParamsFieldsTv);
        mEmail_container = new LinearLayout(getContext());
        mEmail_container = getContainerLayout(mEmail_container, ID_LL_EMAIL + mCountViews);

        mEmail_container.addView(mEmail_tv);

        mEmail_et = getView(mEmail_et,
                R.string.email_hint, mParamsFieldsEt,
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                ID_ET_EMAIL + mCountViews);
        if (!mIsEmail)
        {
            mEmail_container.setVisibility(View.GONE);
        }

        mEmail_container.addView(mEmail_et);
        mContainer_contacts_lly.addView(mEmail_container);
    }

    /*
    * Method to add spaces into the views
    * */

    private void addSpaces() {
        // To set the spaces
        mSpace_lly = new LinearLayout(getContext());
        mSpace_lly = getSpaceLayout(mSpace_lly, ID_LL_SP + mCountViews);
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
    private EditText getView(EditText editText, int idHintString, LinearLayout.LayoutParams params, int inputType, int id) {
        editText = new EditText(getContext());
        editText.setLayoutParams(params);
        editText.setHint(idHintString);
        editText.setBackground(null);
        editText.setInputType(inputType);
        editText.setId(id);
        return editText;
    }
}
