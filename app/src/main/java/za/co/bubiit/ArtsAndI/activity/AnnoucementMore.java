package za.co.bubiit.ArtsAndI.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.AppController;
import za.co.bubiit.ArtsAndI.helper_util.MoreHolder;

public class AnnoucementMore extends AppCompatActivity {
    private TextView annoucementText;
    private TextView annoucementTittle;
    private TextView postDate;
    private TextView postTime;
    private NetworkImageView thumbNail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_annoucements_more);
        String[] annoucementArray = MoreHolder.getAnnoucement();
        this.annoucementTittle = (TextView) findViewById(R.id.annoucementTittle);
        this.annoucementText = (TextView) findViewById(R.id.annoucementText);
        this.postDate = (TextView) findViewById(R.id.postDate);
        this.postTime = (TextView) findViewById(R.id.postTime);
        this.annoucementTittle.setText(annoucementArray[1]);
        this.thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(annoucementArray[0], ImageLoader.getImageListener(this.thumbNail, R.drawable.no_image, R.drawable.no_image));
        this.thumbNail.setImageUrl(annoucementArray[0], imageLoader);
        this.annoucementText.setText(annoucementArray[2]);
        String[] postdateArray = annoucementArray[3].split(" ");
        this.postDate.setText(postdateArray[0] + " " + postdateArray[1] + " '" + postdateArray[2]);
        this.postTime.setText(postdateArray[3]);
    }
}
