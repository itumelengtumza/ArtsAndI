package za.co.bubiit.ArtsAndI.helper_util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static Context mCtx;
    private static MySingleton mInstance;
    private ImageLoader mImageLoader = new ImageLoader(this.mRequestQueue, new C03841());
    private RequestQueue mRequestQueue = getRequestQueue();

    /* renamed from: za.co.bubiit.ArtsAndI.helper_util.MySingleton$1 */
    class C03841 implements ImageCache {
        private final LruCache<String, Bitmap> cache = new LruCache(20);

        C03841() {
        }

        public Bitmap getBitmap(String url) {
            return (Bitmap) this.cache.get(url);
        }

        public void putBitmap(String url, Bitmap bitmap) {
            this.cache.put(url, bitmap);
        }
    }

    private MySingleton(Context context) {
        mCtx = context;
    }

    public static synchronized MySingleton getInstance(Context context) {
        MySingleton mySingleton;
        synchronized (MySingleton.class) {
            if (mInstance == null) {
                mInstance = new MySingleton(context);
            }
            mySingleton = mInstance;
        }
        return mySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }
}
