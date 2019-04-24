package za.co.bubiit.ArtsAndI.helper_util;

public class ServerConnect {
    public static final String URL_PUBLISHER =  "https://abc-group.co.za/android_api2/update.php";
    private static final String URL_ANNOUCEMENT_PUBLISHER = "https://abc-group.co.za/android_api2/annoucementsPublisher.php";
    private static final String URL_ANNOUCEMENT_REGISTER = "https://abc-group.co.za/android_api2/annoucementsRegister.php";
    private static final String URL_EVENT_PUBLISHER = "https://abc-group.co.za/android_api2/eventPublisher.php";
    private static final String URL_EVENT_REGISTER = "https://abc-group.co.za/android_api2/eventRegister.php";
    public static final String URL_LOGIN = "https://abc-group.co.za/android_api2/login.php";
    private static final String URL_MEDIA_PUBLISHER = "https://abc-group.co.za/android_api2/mediaPublisher.php";
    private static final String URL_NOTICE_PUBLISHER = "https://abc-group.co.za/android_api2/noticePublisher.php";
    private static final String URL_NOTICE_REGISTER = "https://abc-group.co.za/android_api2/noticeRegister.php";
    public static final String URL_PASSWORD_RESET = "https://abc-group.co.za/android_api2/resetPassword.php";
    public static final String URL_PASSWORD_RESET_REQUEST = "https://abc-group.co.za/android_api2/resetPasswordRequest.php";
    public static final String URL_REGISTER = "https://abc-group.co.za/android_api2/userRegister.php";
    private static final String URL_USER_PUBLISHER = "https://abc-group.co.za/android_api2/userPublisher.php";
    public static final String[] annoucement = new String[]{URL_ANNOUCEMENT_REGISTER, URL_ANNOUCEMENT_PUBLISHER, "DB14", "annoucements", "id", "imageurl", "tittle", "text", "postdate"};
    public static final String downloadDirectory = "Africa & I";
    public static final String[] event = new String[]{URL_EVENT_REGISTER, URL_EVENT_PUBLISHER, "DB11", "events", "id", "email", "imageurl", "name", "startdate", "enddate", "city", "country", "venue", "details", "contact", "postdate", "officialname"};
    public static final String[] login = new String[]{URL_LOGIN, URL_USER_PUBLISHER, "DB13", "users", "email", "password"};
    public static final String mainUrl = "https://abc-group.co.za/android_api2/media";
    public static final String[] media = new String[]{"Dont Matter", URL_MEDIA_PUBLISHER, "DB16", "media", "id", "mediaurl", "mediaimage", "medianame", "mediadetails", "mediatype", "mediasize", "postdate"};
    public static final String[] notice = new String[]{URL_NOTICE_REGISTER, URL_NOTICE_PUBLISHER, "DB12", "notices", "id", "email", "organization", "notice", "deadline", "division", "contact", "postdate"};
    public static final String[] passwordReset = new String[]{URL_PASSWORD_RESET, URL_USER_PUBLISHER, "DB13", "users", "name", "email", "country", "code", "password"};
    public static final String[] passwordResetRequest = new String[]{URL_PASSWORD_RESET_REQUEST, URL_USER_PUBLISHER, "DB17", "users", "email"};
    public static final String[] user = new String[]{URL_REGISTER, URL_USER_PUBLISHER, "DB10", "users", "name", "email", "country"};
}
