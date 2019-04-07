package za.co.bubiit.ArtsAndI.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private Button btnLinkToRegister;
    private Button btnLogin;
    private Button btnResetPassword;
    private DatabaseConnections dbConn;
    private String email;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;

    /* renamed from: za.co.bubiit.ArtsAndI.activity.LoginActivity$1 */
    class C02401 implements OnFocusChangeListener {
        C02401() {
        }

        public void onFocusChange(View view, boolean b) {
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.LoginActivity$2 */
    class C02412 implements OnClickListener {
        C02412() {
        }

        public void onClick(View view) {
            LoginActivity.this.email = LoginActivity.this.inputEmail.getText().toString().trim();
            String password = LoginActivity.this.inputPassword.getText().toString().trim();
            final String USER_DETAILS = "userDetails";
            final String EMAIL = "email";
            Editor editor = LoginActivity.this.getSharedPreferences(USER_DETAILS, 0).edit();
            editor.putString(EMAIL, LoginActivity.this.email);
            editor.commit();
            if (LoginActivity.this.email.isEmpty() || (LoginActivity.this.inputPassword.getVisibility() == View.VISIBLE && password.isEmpty())) {
                Toast.makeText(LoginActivity.this.getApplicationContext(), "Please enter all credentials!", Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(LoginActivity.this.email).matches()) {
                Toast.makeText(LoginActivity.this.getApplicationContext(), "Email format is wrong!", Toast.LENGTH_LONG).show();
            } else if (LoginActivity.this.inputPassword.getVisibility() == View.VISIBLE) {
                LoginActivity.this.dbConn.insertIntoDB(ServerConnect.login, new String[]{LoginActivity.this.email, password}, -1);
                LoginActivity.this.dbConn.retrieveFromDB(ServerConnect.user, -1);
                LoginActivity.this.dbConn.retrieveFromDB(ServerConnect.notice, -1);
                LoginActivity.this.dbConn.retrieveFromDB(ServerConnect.event, -1);
                LoginActivity.this.dbConn.retrieveFromDB(ServerConnect.annoucement, -1);
                LoginActivity.this.dbConn.retrieveFromDB(ServerConnect.media, -1);
            } else {
                LoginActivity.this.dbConn.insertIntoDB(ServerConnect.passwordResetRequest, new String[]{LoginActivity.this.email}, -1);
            }
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.LoginActivity$3 */
    class C02423 implements OnClickListener {
        C02423() {
        }

        public void onClick(View view) {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), RegisterActivity.class));
            LoginActivity.this.finish();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.LoginActivity$4 */
    class C02434 implements OnClickListener {
        C02434() {
        }

        public void onClick(View view) {
            LoginActivity.this.inputPassword.setVisibility(View.GONE);
            LoginActivity.this.btnLogin.setText("SUBMIT");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.inputEmail = (EditText) findViewById(R.id.email);
        this.inputPassword = (EditText) findViewById(R.id.password);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        this.btnResetPassword = (Button) findViewById(R.id.passwordReset);
        this.pDialog = new ProgressDialog(this);
        this.session = new SessionManager(getApplicationContext());
        this.dbConn = new DatabaseConnections(this);
        this.inputPassword.setVisibility(View.VISIBLE);
        this.btnLogin.setText("LOGIN");
        if (this.session.isLoggedIn()) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
        this.inputPassword.setOnFocusChangeListener(new C02401());
        this.btnLogin.setOnClickListener(new C02412());
        this.btnLinkToRegister.setOnClickListener(new C02423());
        this.btnResetPassword.setOnClickListener(new C02434());

    }

    private void showDialog() {
        if (!this.pDialog.isShowing()) {
            this.pDialog.setMessage("Sending...");
            this.pDialog.setIndeterminate(true);
            this.pDialog.setCancelable(false);
            this.pDialog.show();
        }
    }

    private void hideDialog() {
        if (this.pDialog.isShowing()) {
            this.pDialog.dismiss();
        }
    }
}
