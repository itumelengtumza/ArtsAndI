package za.co.bubiit.ArtsAndI.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;

public class TaskFragment extends Fragment {
    private static final boolean DEBUG = true;
    private static final String TAG = TaskFragment.class.getSimpleName();
    private TaskCallbacks mCallbacks;
    private boolean mRunning;
    private DummyTask mTask;

    private class DummyTask extends AsyncTask<Void, Integer, Void> {
        private String f_url;

        public DummyTask(String f_url) {
            this.f_url = f_url;
        }

        protected void onPreExecute() {
            TaskFragment.this.mCallbacks.onPreExecute();
            TaskFragment.this.mRunning = TaskFragment.DEBUG;
        }

        protected Void doInBackground(Void... f) {
            Exception e;
            String downloadFileName = this.f_url.replace(ServerConnect.mainUrl, "");
            try {
                URL url = new URL(this.f_url);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                File apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + ServerConnect.downloadDirectory);
                try {
                    if (!apkStorage.exists()) {
                        apkStorage.mkdir();
                    }
                    File outputFile = new File(apkStorage, downloadFileName);
                    try {
                        if (!outputFile.exists()) {
                            outputFile.createNewFile();
                        }
                        InputStream input = new BufferedInputStream(url.openStream(), 8192);
                        OutputStream output = new FileOutputStream(outputFile);
                        byte[] data = new byte[1024];
                        int total = 0;
                        while (true) {
                            int count = input.read(data);
                            if (count == -1) {
                                break;
                            }
                            total += count;
                            publishProgress(new Integer[]{Integer.valueOf((total * 100) / lenghtOfFile)});
                            output.write(data, 0, count);
                            if (isCancelled()) {
                                break;
                            }
                        }
                        output.flush();
                        output.close();
                        input.close();
                    } catch (Exception e2) {
                        e = e2;
                        Log.e("Error: ", e.getMessage());
                        return null;
                    }
                } catch (Exception e3) {
                    e = e3;
                    Log.e("Error: ", e.getMessage());
                    return null;
                }
            } catch (Exception e4) {
                e = e4;
                Log.e("Error: ", e.getMessage());
                return null;
            }
            return null;
        }

        protected void onProgressUpdate(Integer... percent) {
            TaskFragment.this.mCallbacks.onProgressUpdate(percent[0].intValue());
        }

        protected void onCancelled() {
            TaskFragment.this.mCallbacks.onCancelled();
            TaskFragment.this.mRunning = false;
        }

        protected void onPostExecute(Void ignore) {
            TaskFragment.this.mCallbacks.onPostExecute();
            TaskFragment.this.mRunning = false;
        }
    }

    public interface TaskCallbacks {
        void onCancelled();

        void onPostExecute();

        void onPreExecute();

        void onProgressUpdate(int i);
    }

    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach(Activity)");
        super.onAttach(activity);
        if (activity instanceof TaskCallbacks) {
            this.mCallbacks = (TaskCallbacks) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement the TaskCallbacks interface.");
    }

    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setRetainInstance(DEBUG);
    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
        cancel();
    }

    public void start(String f_url) {
        if (!this.mRunning) {
            this.mTask = new DummyTask(f_url);
            this.mTask.execute(new Void[0]);
            this.mRunning = DEBUG;
        }
    }

    public void cancel() {
        if (this.mRunning) {
            this.mTask.cancel(false);
            this.mTask = null;
            this.mRunning = false;
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(Bundle)");
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
    }

    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();
    }

    public void onPause() {
        Log.i(TAG, "onPause()");
        super.onPause();
    }

    public void onStop() {
        Log.i(TAG, "onStop()");
        super.onStop();
    }
}
