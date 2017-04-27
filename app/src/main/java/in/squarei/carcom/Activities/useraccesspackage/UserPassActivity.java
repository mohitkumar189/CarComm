package in.squarei.carcom.Activities.useraccesspackage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import in.squarei.carcom.Activities.SocialConnectBaseActivity;
import in.squarei.carcom.Activities.UserDashboardActivity;
import in.squarei.carcom.R;

public class UserPassActivity extends SocialConnectBaseActivity {

    private EditText editPassDigitOne, editPassDigitTwo, editPassDigitThree, editPassDigitFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass);
    }

    @Override
    protected void initViews() {
        editPassDigitOne = (EditText) findViewById(R.id.editPassDigitOne);
        editPassDigitTwo = (EditText) findViewById(R.id.editPassDigitTwo);
        editPassDigitThree = (EditText) findViewById(R.id.editPassDigitThree);
        editPassDigitFour = (EditText) findViewById(R.id.editPassDigitFour);
    }


    @Override
    protected void initContext() {

    }

    @Override
    protected void initListners() {
        //editPassDigitOne.addTextChangedListener(this);
        editPassDigitOne.addTextChangedListener(new MyTextWatcher(editPassDigitOne));
        editPassDigitTwo.addTextChangedListener(new MyTextWatcher(editPassDigitTwo));
        editPassDigitThree.addTextChangedListener(new MyTextWatcher(editPassDigitThree));
        editPassDigitFour.addTextChangedListener(new MyTextWatcher(editPassDigitFour));
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected boolean isHomeButton() {
        return false;
    }

    @Override
    protected boolean isNavigationView() {
        return false;
    }

    @Override
    protected boolean isTabs() {
        return false;
    }

    @Override
    protected boolean isFab() {
        return false;
    }

    @Override
    protected boolean isDrawerListener() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.editPassDigitOne:
                    editPassDigitTwo.requestFocus();
                    Log.d("tag", "editPassDigitOne");
                    break;
                case R.id.editPassDigitTwo:
                    editPassDigitThree.requestFocus();
                    Log.d("tag", "editPassDigitTwo");
                    break;
                case R.id.editPassDigitThree:
                    editPassDigitFour.requestFocus();
                    Log.d("tag", "editPassDigitThree");
                    break;
                case R.id.editPassDigitFour:

                    if (text != null || text != "") {
                        startActivity(UserPassActivity.this, UserDashboardActivity.class);

                    }
                    Log.d("tag", "editPassDigitFour");
                    break;
            }
        }
    }
}
