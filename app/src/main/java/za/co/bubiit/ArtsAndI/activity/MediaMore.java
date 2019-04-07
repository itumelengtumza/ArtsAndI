package za.co.bubiit.ArtsAndI.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.io.File;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.fragments.TaskFragment;
import za.co.bubiit.ArtsAndI.fragments.TaskFragment.TaskCallbacks;
import za.co.bubiit.ArtsAndI.helper_util.AppController;
import za.co.bubiit.ArtsAndI.helper_util.MoreHolder;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;

public class MediaMore extends FragmentActivity implements TaskCallbacks {
    private static final String KEY_CURRENT_PROGRESS = "current_progress";
    private static final String KEY_PERCENT_PROGRESS = "percent_progress";
    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private Button cancelBtn;
    private ImageView downloadBtn;
    private boolean isConnected;
    private LinearLayout layout;
    private TextView mPercent;
    private ProgressBar mProgressBar;
    private TaskFragment mTaskFragment;
    private TextView mediaDetails;
    private TextView mediaName;
    private TextView mediaSize;
    private TextView mediaTypeView;
    private ImageView playBtn;
    private TextView postDate;
    private NetworkImageView thumbNail;

    /* renamed from: za.co.bubiit.ArtsAndI.activity.MediaMore$3 */
    class C02483 implements OnClickListener {
        C02483() {
        }

        public void onClick(View v) {
            MediaMore.this.layout.setVisibility(View.GONE);
            MediaMore.this.mTaskFragment.cancel();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.MediaMore$4 */
    class C02494 implements DialogInterface.OnClickListener {
        C02494() {
        }

        public void onClick(DialogInterface dialog, int which) {
            MediaMore.this.openDownloadedFolder();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.MediaMore$5 */
    class C02505 implements DialogInterface.OnClickListener {
        C02505() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        boolean z;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_more);
        final String[] mediaArray = MoreHolder.getMedia();
        this.mediaName = (TextView) findViewById(R.id.mediaName);
        this.mediaTypeView = (TextView) findViewById(R.id.mediaType);
        this.mediaDetails = (TextView) findViewById(R.id.mediaDetails);
        this.mediaSize = (TextView) findViewById(R.id.mediaSize);
        this.postDate = (TextView) findViewById(R.id.postDate);
        this.thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);
        this.playBtn = (ImageView) findViewById(R.id.playBtn);
        this.downloadBtn = (ImageView) findViewById(R.id.downloadBtn);
        this.layout = (LinearLayout) findViewById(R.id.progressDiaog);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(mediaArray[1], ImageLoader.getImageListener(this.thumbNail, R.drawable.no_image, R.drawable.no_image));
        this.thumbNail.setImageUrl(mediaArray[1], imageLoader);
        this.mediaName.setText(mediaArray[2]);
        this.mediaTypeView.setText(mediaArray[4]);
        this.mediaDetails.setText(mediaArray[3]);
        String[] postArray = mediaArray[6].split(" ");
        this.postDate.setText(postArray[0] + " " + postArray[1] + " '" + postArray[2]);
        this.mediaSize.setText(mediaArray[5]);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        this.mPercent = (TextView) findViewById(R.id.percent_progress);
        this.cancelBtn = (Button) findViewById(R.id.cancelBtn);
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            z = false;
        } else {
            z = true;
        }
        this.isConnected = z;
        if (this.isConnected) {
            this.playBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.parse(mediaArray[0]), mediaArray[4]);
                    MediaMore.this.startActivity(intent);
                }
            });
            this.downloadBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Log.i("MediaMore", "INSIDE onClick!!!"+mediaArray[0]);
                    MediaMore.this.layout.setVisibility(View.VISIBLE);
                    MediaMore.this.mTaskFragment.start(mediaArray[0]);
                }
            });
            this.cancelBtn.setOnClickListener(new C02483());
        }
        if (savedInstanceState != null) {
            this.mProgressBar.setProgress(savedInstanceState.getInt(KEY_CURRENT_PROGRESS));
            this.mPercent.setText(savedInstanceState.getString(KEY_PERCENT_PROGRESS));
        }
        FragmentManager fm = getSupportFragmentManager();
        this.mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
        if (this.mTaskFragment == null) {
            this.mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(this.mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PROGRESS, this.mProgressBar.getProgress());
        outState.putString(KEY_PERCENT_PROGRESS, this.mPercent.getText().toString());
    }

    public void onPreExecute() {
    }

    public void onProgressUpdate(int percent) {
        this.mProgressBar.setProgress((this.mProgressBar.getMax() * percent) / 100);
        this.mPercent.setText(percent + "%");
    }

    public void onCancelled() {
        this.mProgressBar.setProgress(0);
        this.mPercent.setText(getString(R.string.zero_percent));
        Toast.makeText(this, R.string.task_cancelled_msg, Toast.LENGTH_LONG).show();
    }

    public void onPostExecute() {
        this.mProgressBar.setProgress(this.mProgressBar.getMax());
        this.mPercent.setText(getString(R.string.one_hundred_percent));
        Builder alertDialog = new Builder(this);
        alertDialog.setTitle((CharSequence) "Download Complete");
        alertDialog.setMessage((CharSequence) "Open Containing Folder?");
        alertDialog.setPositiveButton((CharSequence) "YES", new C02494());
        alertDialog.setNegativeButton((CharSequence) "NO", new C02505());
        alertDialog.show();
    }

    private void openDownloadedFolder() {
        if (new File(Environment.getExternalStorageDirectory() + "/" + ServerConnect.downloadDirectory).exists()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + ServerConnect.downloadDirectory), "application/*");
            startActivity(Intent.createChooser(intent, "Open With"));
            return;
        }
        Toast.makeText(this, "Right now there is no directory. Please download some file first.", Toast.LENGTH_SHORT).show();
    }
}
