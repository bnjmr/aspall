package ir.jahanmir.aspa;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.Downloader;
import ir.jahanmir.aspa.classes.Logger;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.component.PersianTextViewNormal;
import ir.jahanmir.aspa.enums.EnumDownloadID;
import ir.jahanmir.aspa.enums.EnumInternetErrorType;
import ir.jahanmir.aspa.enums.EnumResponse;
import ir.jahanmir.aspa.events.EventOnCanceledDialogUpdatingApplication;
import ir.jahanmir.aspa.events.EventOnChangedDownloadPercent;
import ir.jahanmir.aspa.events.EventOnChoseImageClicked;
import ir.jahanmir.aspa.events.EventOnClickedLogoutButton;
import ir.jahanmir.aspa.events.EventOnClickedYesOnYesNoDialog;
import ir.jahanmir.aspa.events.EventOnDownloadedFileCompleted;
import ir.jahanmir.aspa.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.aspa.events.EventOnGetErrorGetUserAccountInfo;
import ir.jahanmir.aspa.events.EventOnGetErrorRegConnect;
import ir.jahanmir.aspa.events.EventOnGetErrorUserLicense;
import ir.jahanmir.aspa.events.EventOnGetPollResponse;
import ir.jahanmir.aspa.events.EventOnGetRegConnectionResponse;
import ir.jahanmir.aspa.events.EventOnGetUpdateResponse;
import ir.jahanmir.aspa.events.EventOnGetUserAccountInfoResponse;
import ir.jahanmir.aspa.events.EventOnGetUserLicenseResponse;
import ir.jahanmir.aspa.events.EventOnRegisterPoll;
import ir.jahanmir.aspa.events.EventOnSendPollRequest;
import ir.jahanmir.aspa.events.EventOnSetPollResponse;
import ir.jahanmir.aspa.events.EventOnShowDialogUpdatingApplicationRequest;
import ir.jahanmir.aspa.events.EventOnShowUpdateDialog;
import ir.jahanmir.aspa.events.EventOnStartUploadingAvatar;
import ir.jahanmir.aspa.events.EventOnStopUploadingAvatar;
import ir.jahanmir.aspa.model.Account;
import ir.jahanmir.aspa.model.License;
import ir.jahanmir.aspa.model.ModelYesNoDialog;
import ir.jahanmir.aspa.model.News;

import static ir.jahanmir.aspa.G.context;
import static ir.jahanmir.aspa.enums.EnumYesNoKind.RegConnect;


public class ActivityShowCurrentService extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tv_package_name)
    TextView tvPackageName;
    @Bind(R.id.lay_temp_connection)
    LinearLayout layTempConnection;
    @Bind(R.id.layBtnVaslMovaghat)
    LinearLayout btnTempConnection;
    @Bind(R.id.tv_remaining_days)
    TextView tvRemainingDays;
    @Bind(R.id.lbl_remaining_days)
    TextView lblRemainingDays;
    @Bind(R.id.tv_taraze_mali)
    TextView tvTarazeMali;
    @Bind(R.id.tv_connection_status)
    TextView tvConnectionStatus;
    @Bind(R.id.btn_enter)
    LinearLayout btnEnter;

    @Bind(R.id.txtDay)
    TextView txtDay;

    @Bind(R.id.txtRemaindFeshfeshe)
    TextView txtRemaindFeshfeshe;
    @Bind(R.id.txtServiceTraffic)
    TextView txtServiceTraffic;
    @Bind(R.id.txtCurrentService)
    TextView txtCurrentService;
    @Bind(R.id.txtTransferedTraffic)
    TextView txtTransferedTraffic;
    @Bind(R.id.txtNonTransferTraffic)
    TextView txtNonTransferTraffic;

    @Bind(R.id.txtTaraze_maliB)
    TextView txtTaraze_maliB;


    @Bind(R.id.layCurrentService)
    LinearLayout layCurrentService;
    @Bind(R.id.layServiceTraffic)
    LinearLayout layServiceTraffic;
    @Bind(R.id.layRemaindFeshfeshe)
    LinearLayout layRemaindFeshfeshe;


    @Bind(R.id.layRemaindTrafficSplit)
    LinearLayout layRemaindTrafficSplit;
    @Bind(R.id.txtTrafficSplit)
    TextView txtTrafficSplit;


    @Bind(R.id.txtTransferedTrafficUnit)
    PersianTextViewNormal txtTransferedTrafficUnit;

    @Bind(R.id.txtCurrentTraffic)
    PersianTextViewNormal txtCurrentTraffic;
    @Bind(R.id.txtCurrentTrafficUnit)
    PersianTextViewNormal txtCurrentTrafficUnit;

    @Bind(R.id.txtTaraze_maliUnit)
    PersianTextViewNormal txtTaraze_maliUnit;
    @Bind(R.id.layItems)
    LinearLayout layItems;

    @Bind(R.id.layPacketName)
    LinearLayout layPacketName;
    @Bind(R.id.layRemainTraffic)
    LinearLayout layRemainTraffic;
    @Bind(R.id.lay_remaining_days)
    LinearLayout lay_remaining_days;
    @Bind(R.id.layExpired)
    LinearLayout layExpired;
    @Bind(R.id.layBalance)
    LinearLayout layBalance;
    @Bind(R.id.layConnectionStatus)
    LinearLayout layConnectionStatus;
    @Bind(R.id.layNonTransferTraffic)
    LinearLayout layNonTransferTraffic;
    @Bind(R.id.layTransferedTrafficUnit)
    LinearLayout layTransferedTrafficUnit;

    DialogClass dlgNewWaiting;
    DialogClass dlgUpdate;
    Downloader downloader = null;
    DialogClass gpsDialog;
    DialogClass dlgShowPoll;


    ActionBarDrawerToggle mActionBarDrawerToggle;
    SharedPreferences pref;


    LinearLayout[] firstItems;
    LinearLayout[] secondItems;
    int[] firsItemsBackgrounds;


    int[] secondItemsBackground;
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    String imageAddresses = "";
    int a000 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        G.currentActivity = this;
        setContentView(R.layout.activity_show_current_service);
        ButterKnife.bind(this);
        firstItems = new LinearLayout[]{layPacketName, layRemainTraffic, lay_remaining_days, layExpired, layBalance, layConnectionStatus};
        secondItems = new LinearLayout[]{layServiceTraffic, layRemaindFeshfeshe, layRemaindTrafficSplit, layNonTransferTraffic, layTransferedTrafficUnit};

        firsItemsBackgrounds = new int[]{R.drawable.row_1, R.drawable.row_2, R.drawable.row_3, R.drawable.row_4, R.drawable.row_5, R.drawable.back_row_green};
        secondItemsBackground = new int[]{R.drawable.row_2_1, R.drawable.row_2_2, R.drawable.row_2_3, R.drawable.back_row_blue};
        dlgNewWaiting = new DialogClass(this);
        EventBus.getDefault().register(this);
        dlgUpdate = new DialogClass();
        WebService.sendGetUserAccountInfoRequest();
        WebService.sendGetCountNotify();
        WebService.sendGetPollRequest();
//        layItems.bringToFront();

//        sendEventsToServer();

        WebService.sendGetLocationsRequest();
        pref = getApplicationContext().getSharedPreferences(G.FB_SHARED_PREF, 0);
        String firebaseToken = pref.getString("token", "token");
        Logger.d(firebaseToken);
        if (!pref.getString("token", "token").equals("token")) {
            WebService.sendTokenToServer(pref.getString("token", "token"));
        }


//        getUpdate getUpdate = new getUpdate();
//        getUpdate.setDes("j,qdphj");
//        getUpdate.setForce(false);
//        getUpdate.setMessage("messaaaaage");
//        getUpdate.setUrl("https://www.google.com/");
//        getUpdate.setVer(1.2f);
//        EventBus.getDefault().post(new EventOnShowUpdateDialog(getUpdate));
        if (!G.isCheckUpdate) {
            WebService.sendGetUpdate();
            G.isCheckUpdate = true;
        }

        setOnClickListeners();


        dlgNewWaiting.DialogWaitingClose();
        layTempConnection.setVisibility(View.GONE);

        if (getIntent().getExtras() != null) {
            /** yani az safheye login vared safhe asli shodeim. */
//            AccountInfoResponse jsonAccountInfo = new Gson().fromJson(getIntent().getExtras().getString("JSON_ACCOUNT_INFO"),AccountInfoResponse.class);
            initializeUserAccountView();
        } else {
            /** yani mostaghim vared safheye asli shodeim. */

                    // bareye check kardane inke bashgah darad ya na.
                    if (G.currentLicense == null)
                        G.currentLicense = new Select().from(License.class).executeSingle();


                    dlgNewWaiting.DialogWaiting();

                    WebService.sendGetUserLicenseRequest();


            /** check application update */

                    sendRequestGetNews();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    //    private void sendEventsToServer() {
//        List<LocationsToSend> locationsToSend = new Select().from(LocationsToSend.class).execute();
//        for (int i = 0; i <locationsToSend.size() ; i++) {
//            LocationsToSend Send= locationsToSend.get(i);
//            if(Send.isOffline()){
//                WebService.sendAddScoreRequest();
//            }
//        }
//
//    }


    /////////////////

    public void onEventMainThread(EventOnChoseImageClicked event) {
        Logger.d("ActivityMain : EventOnChoseImageClicked is raised");

        if (G.hasImage) {
            final MaterialDialog dialogChoiceImage = new MaterialDialog.Builder(G.currentActivity)
                    .customView(R.layout.dialog_choice_image_type, false)
                    .build();
            View v = dialogChoiceImage.getCustomView();
            dialogChoiceImage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LinearLayout layChoiceGallery = (LinearLayout) v.findViewById(R.id.layChoiceGallery);
            LinearLayout layChoiceCamera = (LinearLayout) v.findViewById(R.id.layChoiceCamera);
            layChoiceCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    WebService.removeAvatarImage();
//                    final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
//                    startActivityForResult(intent, CAPTURE_IMAGE);
                    dialogChoiceImage.dismiss();
                }
            });
            layChoiceGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
                    dialogChoiceImage.dismiss();
                }
            });
            dialogChoiceImage.show();
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                imageAddresses = (getPath(context, data.getData()));

            } else if (requestCode == CAPTURE_IMAGE) {
                imageAddresses = (getImagePath());
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
            if (imageAddresses != null && !imageAddresses.equals("")) {
                String encodedImage = getEncoded64ImageStringFromBitmap(imageAddresses);
                WebService.uploadAvatarImage(encodedImage);
            }

        }
    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }

    public String getImagePath() {
        return imgPath;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }


            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getEncoded64ImageStringFromBitmap(String path) {
        Bitmap bm = null;
        ByteArrayOutputStream baos = null;
        byte[] b = null;
        String encodeString = null;

        double finalWeith;
        double finalHeight;
        try {
            bm = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();


            int width = bm.getWidth();
            int height = bm.getHeight();

            if (width > height) {
                double mainScale = width / height;
                finalWeith = 150;
                finalHeight = 150 / mainScale;
            } else {
                double mainScale = height / width;
                finalHeight = 150;
                finalWeith = 150 / mainScale;
            }

            int ScaleSize = 150 / height;

            bm = Bitmap.createScaledBitmap(
                    bm, (int) (finalWeith), (int) (finalHeight), false);


            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            b = baos.toByteArray();
            encodeString = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.d("getEncoded64ImageStringFromBitmap is " + encodeString);
        return encodeString;
    }


    ////////////////

    @Override
    protected void onResume() {
        super.onResume();

        WebService.getAvatarImage();
        G.currentActivity = this;
        context = this;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void sendRequestGetNews() {
        News lastNews = new Select()
                .from(News.class)
                .orderBy("code desc")
                .limit(1)
                .executeSingle();
        if (lastNews == null) {
            WebService.sendGetNewsRequest(0);
        } else {
            WebService.sendGetNewsRequest(lastNews.newsID);
        }
    }

    public void initializeUserAccountView() {

        try {


            if (dlgNewWaiting != null) {
                dlgNewWaiting.DialogWaitingClose();
            }
            if (G.currentLicense == null)
                G.currentLicense = new Select().from(License.class).executeSingle();

            if (G.currentAccount == null)
                G.currentAccount = new Select().from(Account.class).executeSingle();

            if (G.currentAccount.currentTraffic == -11111) {
                txtCurrentTraffic.setText("نامحدود");
                txtCurrentTrafficUnit.setVisibility(View.GONE);

            } else {
                txtCurrentTraffic.setText(G.currentAccount.currentTraffic + "");
            }

            if (G.currentAccount.RHour == -11111) {
                if (G.currentAccount.Rday == -11111) {
                    tvRemainingDays.setText("نامحدود");
                    lblRemainingDays.setVisibility(View.GONE);
                    txtDay.setVisibility(View.GONE);
                } else {
                    tvRemainingDays.setText(G.currentAccount.Rday + "");
                    txtDay.setText(" روز ");
                    if (G.currentAccount.Rday == 0) {
                        lay_remaining_days.setVisibility(View.GONE);
                    }
                }
            } else {
                tvRemainingDays.setText(G.currentAccount.RHour + "");
                txtDay.setText(" ساعت ");
            }

            if (G.currentAccount.RegConnect) {
//                lay_remaining_days.setVisibility(View.GONE);
//                layExpired.setVisibility(View.VISIBLE);
                dlgNewWaiting.DialogWaitingClose();
                layTempConnection.setVisibility(View.VISIBLE);
                btnTempConnection.setVisibility(View.VISIBLE);

            }
            if (G.currentAccount.IsExpire) {
                layExpired.setVisibility(View.VISIBLE);
            }
            String pattern = "#,###";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            tvTarazeMali.setText(" " + myFormatter.format(G.currentAccount.Balance) + "");
            if (G.currentAccount.Balance < 0) {
                layBalance.setVisibility(View.VISIBLE);
                int a = G.currentAccount.Balance + (-2 * G.currentAccount.Balance);
                tvTarazeMali.setText(" " + myFormatter.format(a) + "");
            }
            txtTaraze_maliUnit.setText(G.currentUserInfo.unit + " ");


            if (G.currentAccount.Online) {
                tvConnectionStatus.setText(getString(R.string.online));
            } else {
                tvConnectionStatus.setText(getString(R.string.offline));
            }


            if (G.currentLicense.Feshfeshe && G.currentAccount.remaindFeshfeshe != 0) {
//            layCurrentService.setVisibility(View.VISIBLE);
                layRemaindFeshfeshe.setVisibility(View.VISIBLE);
                txtCurrentService.setVisibility(View.VISIBLE);
                txtRemaindFeshfeshe.setVisibility(View.VISIBLE);
                txtCurrentService.setText(" فشفشه ");
                txtRemaindFeshfeshe.setText(G.currentAccount.remaindFeshfeshe + "");
            }
            if (G.currentLicense.trafficSplit && G.currentAccount.remaindTrafficSplit != 0) {
                layRemaindTrafficSplit.setVisibility(View.VISIBLE);
                layServiceTraffic.setVisibility(View.VISIBLE);
                txtTrafficSplit.setVisibility(View.VISIBLE);
                txtTrafficSplit.setText(G.currentAccount.remaindTrafficSplit + "");
            }
            txtServiceTraffic.setText(G.currentAccount.serviceTraffic + "");
            txtNonTransferTraffic.setText(G.currentAccount.nonTransferedTraffic + "");
            txtTransferedTraffic.setText(G.currentAccount.transferedTraffic + "");


            tvPackageName.setText(G.currentAccount.PkgName.trim().length() < 10 ?
                    String.format(getString(R.string.current_package_name), G.currentAccount.PkgName)
                    : String.format(getString(R.string.current_package_name), "\n" + G.currentAccount.PkgName));
            int j = 0;
            for (int i = 0; i < firstItems.length; i++) {
                if (firstItems[i].getVisibility() == View.VISIBLE) {
                    firstItems[i].setBackgroundResource(firsItemsBackgrounds[j]);
                    j++;
                }
            }
//            layRemaindFeshfeshe.setVisibility(View.GONE);
            int k = 0;
            for (int i = 0; i < secondItems.length; i++) {
                if (secondItems[i].getVisibility() == View.VISIBLE) {
                    secondItems[i].setBackgroundResource(secondItemsBackground[k]);
                    k++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onEventMainThread(EventOnGetErrorRegConnect event) {
        Logger.d("ActivityMain : EventOnGetErrorRegConnect is raised");
        dlgNewWaiting.DialogWaitingClose();
        btnTempConnection.setClickable(true);

        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            U.toast("ارتباط اینترنتی خود را چک کنید.");
        } else {
            U.toast("خطا در دریافت اطلاعات از سمت سرور، لطفا مجددا تلاش کنید.");
        }
    }

    /**
     * gereftane natije darkhaste etesal movaghat.
     */
    public void onEventMainThread(EventOnGetRegConnectionResponse event) {
        Logger.d("ActivityMain : EventOnGetRegConnectionResponse is raised");
        dlgNewWaiting.DialogWaitingClose();
        btnTempConnection.setVisibility(View.GONE);

        if ((event.getRegConnectResponses().get(0)).Result == EnumResponse.OK) {
            WebService.sendGetUserAccountInfoRequest();
//            WebService.sendGetUserAccountInfoRequest();
            if ((event.getRegConnectResponses().get(0)).Message.length() != 0) {
                DialogClass.showMessageDialog("اتصال موقت", (event.getRegConnectResponses().get(0)).Message);
            }
//            try {
//                Intent intent = new Intent(this, ActivityShowCurrentService.class);
//                startActivity(intent);
//                finish();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } else {
            DialogClass.showMessageDialog("اتصال موقت", (event.getRegConnectResponses().get(0)).Message);
        }
    }

    public void onEventMainThread(EventOnGetErrorUserLicense event) {
        Logger.d("ActivityMain : EventOnGetErrorUserLicense is raised.");
        initializeUserAccountView();
    }

    public void onEventMainThread(EventOnGetUserLicenseResponse event) {
        Logger.d("ActivityMain : EventOnGetUserLicenseResponse is raised.");

        if (G.currentLicense == null)
            G.currentLicense = new Select().from(License.class).executeSingle();

        WebService.sendGetUserAccountInfoRequest();
    }

    /**
     * geteftane etelaate moshtarak baraye namayesh dar safhe aval
     * bad az gereftane etelate moshtarak shamel hajme baghi mande va ruzaye baghi mande
     * darkhate ersal inke moshtarak tavasote mobile vared hesabe karbari khod shode ast ersal mishavad.
     * sepass darkhaste gereftane khabar'haye jadid ra midahim.
     */
    public void onEventMainThread(EventOnGetUserAccountInfoResponse event) {
        Logger.d("ActivityMain : EventOnGetUserAccountInfoResponse is raised");
        initializeUserAccountView();
        /** Send this request to get point for current User if set
         * from managment.*/
        WebService.sendVisitMobileRequest();
    }

    public void onEventMainThread(EventOnGetErrorGetUserAccountInfo event) {
        Logger.d("ActivityMain : EventOnGetErrorGetUserAccountInfo is raised");
        G.currentAccount = new Select().from(Account.class).executeSingle();
        G.currentLicense = new Select().from(License.class).executeSingle();
        initializeUserAccountView();
    }

    /* Update EventBus Method */
    public void onEventMainThread(EventOnGetUpdateResponse event) {
        Logger.d("ActivityMain : EventOnGetUpdateResponse is raised");
        try {
            float ver = Float.parseFloat(G.versionName);
            if (ver < event.getUpdateResponse().getVer()) {
                EventBus.getDefault().post(new EventOnShowUpdateDialog(event.getUpdateResponse()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onEventMainThread(EventOnShowUpdateDialog dialog) {
        dlgUpdate.showUpdateDialog(dialog.getUpdate());
    }

    public void onEventMainThread(EventOnShowDialogUpdatingApplicationRequest event) {
        Logger.d("ActivityMain : EventOnShowDialogUpdatingApplicationRequest is raised");
        if (dlgUpdate != null) {
            dlgUpdate.showUpdatingApplicationDialog(event.getNewVersion(), event.isForce(), event.getUrl());
            downloader = new Downloader();
            downloader.requestDownload(event.getUrl(), EnumDownloadID.ACTIVITY_MAIN);
        }
    }

    public void onEventMainThread(EventOnCanceledDialogUpdatingApplication event) {
        Logger.d("ActivityMain : EventOnCanceledDialogUpdatingApplication is raised");
        if (dlgUpdate != null && event.isForce()) {
            /** dar surati ke download update ejbari bashad ba cancel kardane dialog updating baz bayad
             * dialog update application namayesh dade shavad.*/
            dlgUpdate.showUpdateApplicationDialog(event.getNewVersion(), event.isForce(), event.getUrl());
            /** cancel current doanload and delete raw downloaded file*/
            downloader.cancelDownload();
        }
    }

    public void onEventMainThread(EventOnChangedDownloadPercent event) {
        Logger.d("ActivityMain : EventOnChangedDownloadPercent is raised");
        if (dlgUpdate != null) {
            dlgUpdate.changeProgressPercent(event.getPercent());
        }
    }

    public void onEventMainThread(EventOnDownloadedFileCompleted event) {
        Logger.d("ActivityMain : EventOnDownloadedFileCompleted is raised");
        if (dlgUpdate != null) {
            dlgUpdate.showInstallButton();
        }
    }

    public void onEventMainThread(EventOnClickedLogoutButton event) {
        Logger.d("ActivityMain : EventOnClickedLogoutButton is raised");
        G.currentUser.isLogin = false;
        G.currentUser.save();
        startActivity(new Intent(context, ActivityLogin.class));
        finish();
    }

    public void onEventMainThread(EventOnStartUploadingAvatar avatar) {
        dlgNewWaiting.DialogWaiting();

    }

    public void onEventMainThread(EventOnStopUploadingAvatar avatar) {
        dlgNewWaiting.DialogWaitingClose();

    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgNewWaiting.DialogWaitingClose();

    }

    public void onEventMainThread(EventOnClickedYesOnYesNoDialog yesNoDialog) {
        switch (yesNoDialog.getYesNoKind()) {
            case RegConnect:
                WebService.sendRegConnectRequest();
                btnTempConnection.setClickable(false);
                dlgNewWaiting.DialogWaiting();

                break;
        }
    }


    public void onEventMainThread(EventOnSendPollRequest event) {
        Logger.d("ActivityMain : EventOnSendPollRequest is raised");
//        WebService.sendSetPollRequest(event.getPollId(), event.getOptionId(), event.getDes());
    }

    public void onEventMainThread(EventOnRegisterPoll poll) {
        dlgNewWaiting.DialogWaiting();

        WebService.sendSetPollRequest(poll.getPollCode(), poll.getOptionId(), poll.getDes());
    }

    public void onEventMainThread(EventOnGetPollResponse event) {
        Logger.d("ActivityMain : EventOnGetPollResponse is raised");
        if (event.getPollResponse().Result == EnumResponse.OK) {
            dlgShowPoll = new DialogClass();
            dlgShowPoll.showPollDialog(event.getPollResponse());
        }
    }

    public void onEventMainThread(EventOnSetPollResponse response) {
        dlgNewWaiting.DialogWaitingClose();
        WebService.sendGetPollRequest();
        if (response.getStatus() == EnumResponse.OK) {
            Toast.makeText(this, "نظر سنجی با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
            dlgShowPoll.cancelPollDialog();
        } else {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void setOnClickListeners() {
        btnTempConnection.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBtnVaslMovaghat:
                DialogClass dialogClass = new DialogClass();
                ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("هشدار", "آیا مطمئن هستید میخواهید وصل موقت را فعال کنید؟", "", RegConnect);
                dialogClass.showYesNoDialog(modelYesNoDialog);

                break;
            case R.id.btn_enter:
                startActivity(new Intent(G.currentActivity, ActivityMain0.class));
//                overridePendingTransition(0, R.anim.right_to_left);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("ActivityMain : onDestroy()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
