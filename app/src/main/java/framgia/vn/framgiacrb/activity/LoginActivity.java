package framgia.vn.framgiacrb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import framgia.vn.framgiacrb.R;

/**
 * Created by lethuy on 01/07/2016.
 */
public class LoginActivity extends Activity {
    private EditText mEditTextEmail, mEditTextPassword;
    private Button mButtonLogin, mButtonLoginFb, mButtonLoginGg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = (EditText) findViewById(R.id.edit_email);
        mEditTextPassword = (EditText) findViewById(R.id.edit_password);
        mButtonLogin = (Button) findViewById(R.id.btn_login);
        mButtonLoginFb = (Button) findViewById(R.id.btn_facebook);
        mButtonLoginGg = (Button) findViewById(R.id.btn_google);
    }
}