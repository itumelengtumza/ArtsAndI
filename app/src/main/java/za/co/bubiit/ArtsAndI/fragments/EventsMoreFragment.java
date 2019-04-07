package za.co.bubiit.ArtsAndI.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.AppController;
import za.co.bubiit.ArtsAndI.helper_util.MoreHolder;

public class EventsMoreFragment extends Fragment {
    private TextView city;
    private TextView contact;
    private TextView country;
    private TextView date;
    private TextView details;
    private TextView event;
    private TextView officialName;
    private TextView postDate;
    private TextView postTime;
    private TextView senderEmail;
    //private NetworkImageView thumbNail;
    private TextView time;
    private TextView venue;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_more, container, false);
        String[] eventArray = MoreHolder.getEvent();
        this.senderEmail = (TextView) view.findViewById(R.id.senderEmail);
        this.event = (TextView) view.findViewById(R.id.event);
        this.date = (TextView) view.findViewById(R.id.dateText);
        this.time = (TextView) view.findViewById(R.id.timeText);
        this.city = (TextView) view.findViewById(R.id.city);
        this.country = (TextView) view.findViewById(R.id.country);
        this.venue = (TextView) view.findViewById(R.id.venue);
        this.details = (TextView) view.findViewById(R.id.details);
        this.contact = (TextView) view.findViewById(R.id.contact);
        this.postDate = (TextView) view.findViewById(R.id.postDate);
        this.postTime = (TextView) view.findViewById(R.id.postTime);
        this.officialName = (TextView) view.findViewById(R.id.contactOfficial);
        //this.thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
        this.senderEmail.setText(eventArray[0]);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        //imageLoader.get(eventArray[1], ImageLoader.getImageListener(this.thumbNail, R.drawable.no_image, R.drawable.no_image));
        //this.thumbNail.setImageUrl(eventArray[1], imageLoader);
        this.event.setText(eventArray[2]);
        String[] startdateArray = eventArray[3].split(" ");
        String[] enddateArray = eventArray[4].split(" ");
        if (!startdateArray[0].equals(enddateArray[0]) && startdateArray[1].equals(enddateArray[1]) && startdateArray[2].equals(enddateArray[2])) {
            this.date.setText(startdateArray[0] + "-" + enddateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2]);
        } else if (startdateArray[0].equals(enddateArray[0]) && startdateArray[1].equals(enddateArray[1]) && startdateArray[2].equals(enddateArray[2])) {
            this.date.setText(startdateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2]);
        } else {
            this.date.setText(startdateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2] + " - " + enddateArray[0] + " " + enddateArray[1] + " '" + enddateArray[2]);
        }
        this.time.setText(startdateArray[3] + " - " + enddateArray[3]);
        this.city.setText(eventArray[5]);
        this.country.setText(eventArray[6]);
        this.venue.setText(eventArray[7]);
        this.details.setText(eventArray[8]);
        this.contact.setText(eventArray[9]);
        String[] postdateArray = eventArray[10].split(" ");
        this.postDate.setText(postdateArray[0] + " " + postdateArray[1] + " '" + postdateArray[2]);
        this.postTime.setText(postdateArray[3]);
        this.officialName.setText(eventArray[11]);
        return view;
    }
}
