package za.co.bubiit.ArtsAndI.helper_util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import za.co.bubiit.ArtsAndI.activity.LoginActivity;
import za.co.bubiit.ArtsAndI.activity.PasswordResetActivity;
import za.co.bubiit.ArtsAndI.activity.WelcomeActivity;

public class DatabaseConnections {
    private String TAG = "DatabaseConnections";
    private Context context;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private SessionManager session;

    /* renamed from: za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections$2 */
    class C03812 implements ErrorListener {
        C03812() {
        }

        public void onErrorResponse(VolleyError error) {
            DatabaseConnections.this.hideDialog();
            Log.e(DatabaseConnections.this.TAG, "Sending Error: Please check your internet connection!");
            Toast.makeText(DatabaseConnections.this.context.getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections$5 */
    class C03835 implements ErrorListener {
        C03835() {
        }

        public void onErrorResponse(VolleyError error) {
            String errorMsg = error.getMessage();
            VolleyLog.d(DatabaseConnections.this.TAG, "Error: " + errorMsg);
        }
    }

    public DatabaseConnections(Activity context) {
        this.context = context;
        this.pDialog = new ProgressDialog(this.context);
    }

    private void showDialog(String msg) {
        if (!this.pDialog.isShowing()) {
            this.pDialog.setMessage(msg);
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

    public void insertIntoDB(final String[] A, String[] B, int i) {
        if (A[0].equals(ServerConnect.URL_LOGIN)) {
            showDialog("Login...");
        }
        if (A[0].equals(ServerConnect.URL_PASSWORD_RESET_REQUEST) || A[0].equals(ServerConnect.URL_REGISTER)) {
            showDialog("Sending Key...");
        }
        if (A[0].equals(ServerConnect.URL_PASSWORD_RESET)) {
            showDialog("Setting Password...");
        }
        final String[] strArr = B;
        final String[] strArr2 = A;
        AppController.getInstance().addToRequestQueue(new StringRequest(1, A[0], new Listener<String>() {
            public void onResponse(String response) {
                DatabaseConnections.this.TAG = DatabaseConnections.this.TAG + " insertIntoDB: ";
                Log.d(DatabaseConnections.this.TAG, "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    if (jObj.getBoolean("error")) {
                        Toast.makeText(DatabaseConnections.this.context.getApplicationContext(), jObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(DatabaseConnections.this.context, "Success !", Toast.LENGTH_SHORT).show();
                    DatabaseConnections.this.session = new SessionManager(DatabaseConnections.this.context.getApplicationContext());
                    if (A[0].equals(ServerConnect.URL_LOGIN)) {
                        DatabaseConnections.this.session.setLogin(true);
                        DatabaseConnections.this.context.startActivity(new Intent(DatabaseConnections.this.context, WelcomeActivity.class));
                    }
                    if (A[0].equals(ServerConnect.URL_PASSWORD_RESET)) {
                        DatabaseConnections.this.context.startActivity(new Intent(DatabaseConnections.this.context, LoginActivity.class));
                    }
                    if (A[0].equals(ServerConnect.URL_REGISTER) || A[0].equals(ServerConnect.URL_PASSWORD_RESET_REQUEST)) {
                        Toast.makeText(DatabaseConnections.this.context, "Please check your email for key!", Toast.LENGTH_LONG).show();
                        DatabaseConnections.this.context.startActivity(new Intent(DatabaseConnections.this.context, PasswordResetActivity.class));
                    }
                    DatabaseConnections.this.hideDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    DatabaseConnections.this.hideDialog();
                }
            }
        }, new C03812()) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                for (int j = 0; j < strArr.length; j++) {
                    params.put(strArr2[j + 4], strArr[j]);
                }
                return params;
            }
        }, "send_req");
    }

    public void retrieveFromDB(final String[] A, final String table_name) {
        AppController.getInstance().addToRequestQueue(new StringRequest(1, ServerConnect.URL_PUBLISHER, new Listener<String>() {
            public void onResponse(String response) {
                DatabaseConnections.this.db = new SQLiteHandler(DatabaseConnections.this.context.getApplicationContext(), A);
                DatabaseConnections.this.TAG = DatabaseConnections.this.TAG + " retrieveFromDB: ";
                Log.d(DatabaseConnections.this.TAG, response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray myArray = jObj.getJSONArray("table_data");
                    DatabaseConnections.this.db.deleteSQLiteData();
                    for (int j = 0; j < myArray.length(); j++) {
                        JSONObject jObj1 = myArray.getJSONObject(j);

                        Log.i(DatabaseConnections.this.TAG, "jObj1 length is : " + jObj1.length());
                        ContentValues values = new ContentValues();
                        for (int k = 0; k < jObj1.length(); k++) {
                            values.put(A[k + 4], jObj1.getString(A[k + 4]));
                        }
                        DatabaseConnections.this.db.addToSQLiteDB(values);
                    }
                    Log.i(DatabaseConnections.this.TAG, "Service INSIDE StringRequest!!!");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(DatabaseConnections.this.TAG, "Json error: " + e.getMessage());
                }
            }
        }, new C03835()) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();

                params.put("table_name", table_name);

                return params;
            }
        }, "send_req");
    }
}
