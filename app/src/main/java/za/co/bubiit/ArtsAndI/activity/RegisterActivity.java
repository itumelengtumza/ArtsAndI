package za.co.bubiit.ArtsAndI.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class RegisterActivity extends AppCompatActivity {
    private static final String[] COUNTRIES = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Türkiye", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
    private String country;
    private String email;
    private AutoCompleteTextView inputCountry;
    private EditText inputEmail;
    private EditText inputFullName;
    private String name;
    private SessionManager session;

    /* renamed from: za.co.bubiit.ArtsAndI.activity.RegisterActivity$1 */
    class C02571 implements OnClickListener {

        /* renamed from: za.co.bubiit.ArtsAndI.activity.RegisterActivity$1$1 */
        class C02551 implements DialogInterface.OnClickListener {
            C02551() {
            }

            public void onClick(DialogInterface arg0, int arg1) {
                new DatabaseConnections(RegisterActivity.this).insertIntoDB(ServerConnect.user, new String[]{RegisterActivity.this.name, RegisterActivity.this.email, RegisterActivity.this.country}, -1);
            }
        }

        /* renamed from: za.co.bubiit.ArtsAndI.activity.RegisterActivity$1$2 */
        class C02562 implements DialogInterface.OnClickListener {
            C02562() {
            }

            public void onClick(DialogInterface dialog, int which) {
            }
        }

        C02571() {
        }

        public void onClick(View view) {
            RegisterActivity.this.name = RegisterActivity.this.inputFullName.getText().toString().trim();
            RegisterActivity.this.email = RegisterActivity.this.inputEmail.getText().toString().trim();
            RegisterActivity.this.country = RegisterActivity.this.inputCountry.getText().toString();
            String USER_DETAILS = "userDetails";
            String EMAIL = "email";
            String NAME = "name";
            String COUNTRY = "country";
            Editor editor = RegisterActivity.this.getSharedPreferences("userDetails", 0).edit();
            editor.putString("email", RegisterActivity.this.email);
            editor.putString("name", RegisterActivity.this.name);
            editor.putString("country", RegisterActivity.this.country);
            editor.commit();
            if (RegisterActivity.this.name.isEmpty() || RegisterActivity.this.email.isEmpty() || RegisterActivity.this.country.isEmpty()) {
                Toast.makeText(RegisterActivity.this.getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(RegisterActivity.this.email).matches()) {
                Toast.makeText(RegisterActivity.this.getApplicationContext(), "Email format is wrong!", Toast.LENGTH_LONG).show();
            } else if (RegisterActivity.this.validCountry(RegisterActivity.COUNTRIES, RegisterActivity.this.country)) {
                Builder alertDialogBuilder = new Builder(RegisterActivity.this);
                alertDialogBuilder.setMessage((CharSequence) "To finish with the registration, a key will be send to your email.");
                alertDialogBuilder.setPositiveButton((CharSequence) "ok", new C02551());
                alertDialogBuilder.setNegativeButton((CharSequence) "cancel", new C02562());
                alertDialogBuilder.create().show();
            } else {
                Toast.makeText(RegisterActivity.this.getApplicationContext(), "Country name " + RegisterActivity.this.country + " is not known", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.RegisterActivity$2 */
    class C02582 implements OnClickListener {
        C02582() {
        }

        public void onClick(View view) {
            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this.getApplicationContext(), LoginActivity.class));
            RegisterActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.register);
        this.inputFullName = (EditText) findViewById(R.id.name);
        this.inputEmail = (EditText) findViewById(R.id.email);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, COUNTRIES);
        this.inputCountry = (AutoCompleteTextView) findViewById(R.id.country);
        this.inputCountry.setAdapter(adapter);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        this.session = new SessionManager(getApplicationContext());
        if (this.session.isLoggedIn()) {
            startActivity(new Intent(this, TabsActivity.class));
            finish();
        }
        btnRegister.setOnClickListener(new C02571());
        btnLinkToLogin.setOnClickListener(new C02582());
    }

    private boolean validCountry(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
