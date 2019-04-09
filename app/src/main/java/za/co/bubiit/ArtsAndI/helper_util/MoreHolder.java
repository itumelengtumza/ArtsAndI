package za.co.bubiit.ArtsAndI.helper_util;

public class MoreHolder {
    private static String[] annoucement = null;
    private static String[] event = null;
    private static String[] media = null;
    private static String[] notice = null;
    private static String[] tourism = null;

    public void setEvent(String[] event) {
        this.event = event;
    }

    public static String[] getEvent() {
        return event;
    }

    public void setAnnoucement(String[] annoucement) {
        this.annoucement = annoucement;
    }

    public static String[] getAnnoucement() {
        return annoucement;
    }

    public void setNotice(String[] notice) {
        this.notice = notice;
    }

    public static String[] getNotice() {
        return notice;
    }

    public void setMedia(String[] media) {
        this.media = media;
    }

    public static String[] getMedia() {
        return media;
    }

    public void setTourism(String[] tourism) {
        this.tourism = tourism;
    }

    public static String[] getTourism() {
        return tourism;
    }
}
