package com.example.bmotter.triptracker;

import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

import weborb.client.Fault;

import static android.R.attr.name;
import static android.R.id.message;


public class LoginActivity extends AppCompatActivity {
    public EditText menter_name;
    public Button msign_up;
    public Button mlogin_button;
    public TextView msign_up_text;
    public EditText menter_email;
    public EditText menter_password;
    final private String VERSION = "v1";
    final private String APP_ID = "155752DD-F276-4642-FFF6-CA4DBF5ED600";
    final private String SECRET_KEY = "0D1EF370-789A-3A00-FF47-B65174EA3900";
    private final String TAG = "log";

    public void warnUser(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(message);
        builder.setTitle(R.string.authentication_error_title);
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Backendless.initApp(this, APP_ID, SECRET_KEY, VERSION);

        msign_up_text = (TextView) findViewById(R.id.sign_up_text);
        menter_name = (EditText) findViewById(R.id.enter_name);
        mlogin_button = (Button) findViewById(R.id.login_button);
        msign_up = (Button) findViewById(R.id.signupbutton);
        menter_email = (EditText) findViewById(R.id.enter_email);
        menter_password = (EditText) findViewById(R.id.enter_password);


        View.OnClickListener signMeUpListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = menter_email.getText().toString();
                String password = menter_password.getText().toString();
                String name = menter_name.getText().toString();

                userEmail = userEmail.trim();
                password = password.trim();
                name = name.trim();

                if (!userEmail.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                    BackendlessUser user = new BackendlessUser();
                            /* register in Backendless */
                    user.setEmail(menter_email.getText().toString());
                    user.setPassword(menter_password.getText().toString());
                    user.setProperty("name", name);

                    Backendless.UserService.register(user,
                            new BackendlessCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser backendlessUser) {
                                    Log.i(TAG, "Registration successful for " + backendlessUser.getEmail());
                                }
                                @Override
                                public void handleFault( BackendlessFault fault){
                                    Log.i(TAG, "Registration failed: " + fault.getMessage());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage(fault.getMessage());
                                    builder.setTitle(R.string.authentication_error_title);
                                    builder.setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }); /* register the user in Backendless */
                }
                else {
            /* warn the user of the problem */

                }
            }
        };

        View.OnClickListener loginOnClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userEmail = menter_email.getText().toString();
                String password = menter_password.getText().toString();
                String name = menter_name.getText().toString();

                userEmail = userEmail.trim();
                password = password.trim();

                if (!userEmail.isEmpty() && !password.isEmpty()){
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(menter_email.getText().toString());
                    user.setPassword(menter_password.getText().toString());

                    Backendless.UserService.login(userEmail, password,
                            new BackendlessCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser backendlessUser) {
                                    Log.i(TAG, "Login successful for " + backendlessUser.getEmail());
                                }
                                @Override
                                public void handleFault( BackendlessFault fault){
                                    Log.i(TAG, "Login failed: " + fault.getMessage());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage(fault.getMessage());
                                    builder.setTitle(R.string.authentication_error_title);
                                    builder.setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            });


                }
                else {

                }
            }
        };
    }


        @Override
        public void onBackPressed(){
            mlogin_button.setVisibility(View.VISIBLE);
            msign_up_text.setVisibility(View.VISIBLE);
            menter_name.setVisibility(View.INVISIBLE);
            msign_up.setVisibility(View.INVISIBLE);
        }

}
