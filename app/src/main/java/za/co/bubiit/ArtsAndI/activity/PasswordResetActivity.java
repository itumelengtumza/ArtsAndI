package za.co.bubiit.ArtsAndI.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class PasswordResetActivity extends AppCompatActivity {
    private static final String COUNTRY = "country";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String USER_DETAILS = "userDetails";
    private Button btnSubmit;
    private CountDownTimer countDownTimer;
    private String country;
    private DatabaseConnections dbConn;
    private EditText inputPassword;
    private EditText inputResetCode;
    private ProgressDialog pDialog;
    private SessionManager session;
    private TextView timer;

    /* renamed from: za.co.bubiit.ArtsAndI.activity.PasswordResetActivity$1 */
    class C02511 implements OnFocusChangeListener {
        C02511() {
        }

        public void onFocusChange(View view, boolean b) {
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.PasswordResetActivity$2 */
    class C02522 implements OnClickListener {
        C02522() {
        }

        public void onClick(View view) {
            String resetCode = PasswordResetActivity.this.inputResetCode.getText().toString().trim();
            String password = PasswordResetActivity.this.inputPassword.getText().toString().trim();
            SharedPreferences pref = PasswordResetActivity.this.getSharedPreferences(PasswordResetActivity.USER_DETAILS, 0);
            String email = pref.getString("email", "null");
            String name = pref.getString(PasswordResetActivity.NAME, "null");
            PasswordResetActivity.this.country = pref.getString(PasswordResetActivity.COUNTRY, "null");
            if (password.isEmpty() || resetCode.isEmpty()) {
                Toast.makeText(PasswordResetActivity.this.getApplicationContext(), "Please enter all credentials!", Toast.LENGTH_LONG).show();
                return;
            }
            String[] B = new String[]{name, email, PasswordResetActivity.this.country, resetCode, password};
            PasswordResetActivity.this.countDownTimer.cancel();
            PasswordResetActivity.this.dbConn.insertIntoDB(ServerConnect.passwordReset, B, -1);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);
        this.inputResetCode = (EditText) findViewById(R.id.resetCode);
        this.inputPassword = (EditText) findViewById(R.id.password);
        this.btnSubmit = (Button) findViewById(R.id.btnSubmit);
        this.pDialog = new ProgressDialog(this);
        this.session = new SessionManager(getApplicationContext());
        this.dbConn = new DatabaseConnections(this);
        this.timer = (TextView) findViewById(R.id.timer);
        startCountdownTimer();
        this.inputResetCode.setOnFocusChangeListener(new C02511());
        this.btnSubmit.setOnClickListener(new C02522());
    }

    private void startCountdownTimer() {
        this.countDownTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                PasswordResetActivity.this.timer.setText("Time remaining : " + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                Toast.makeText(PasswordResetActivity.this.getApplicationContext(), "Time Out ! Request again to reset password!", Toast.LENGTH_LONG).show();
                PasswordResetActivity.this.startActivity(new Intent(PasswordResetActivity.this.getApplicationContext(), LoginActivity.class));
            }
        }.start();
    }
}
