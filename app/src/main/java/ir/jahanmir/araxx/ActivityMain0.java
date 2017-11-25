package ir.jahanmir.araxx;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.activeandroid.query.Select;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.adapter.AdapterMainItems;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.ColorTool;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumMainItemsTag;
import ir.jahanmir.araxx.events.EventOnChoseImageClicked;
import ir.jahanmir.araxx.events.EventOnClickedLogoutButton;
import ir.jahanmir.araxx.events.EventOnDiActiveItem;
import ir.jahanmir.araxx.events.EventOnGetAvatarImageResponse;
import ir.jahanmir.araxx.events.EventOnGetErrorGetNews;
import ir.jahanmir.araxx.events.EventOnGetNewsResponse;
import ir.jahanmir.araxx.events.EventOnGetReperesenterURL;
import ir.jahanmir.araxx.events.EventOnGetStartFactorResponse;
import ir.jahanmir.araxx.events.EventOnStartUploadingAvatar;
import ir.jahanmir.araxx.events.EventOnStopUploadingAvatar;
import ir.jahanmir.araxx.model.License;
import ir.jahanmir.araxx.model.MainItems;

import static ir.jahanmir.araxx.G.context;


public class ActivityMain0 extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    //    @Bind(R.id.mask_mainbg)
//    ImageView mask;
//    @Bind(R.id.bg_main)
//    ImageView bgMain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    DialogClass dlgShowPoll;
    ActionBarDrawerToggle mActionBarDrawerToggle;


    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    String imageAddresses = "";

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnBack;

    @Bind(R.id.RcyItems)
    RecyclerView RcyItems;


    @Bind(R.id.layAvatar)
    FrameLayout layAvatar;
    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    @Bind(R.id.imgProfileBtn)
    ImageView imgProfileBtn;

    @Bind(R.id.layExit)
    LinearLayout layExit;

    @Bind(R.id.imgAvatar)
    CircularImageView imgAvatar;


    DialogClass dlgWaiting;

    AdapterMainItems adapterMainItems;
    private boolean isAvatarDownloaded = false;
    StaggeredGridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main000);
        ButterKnife.bind(this);
        dlgWaiting = new DialogClass(this);
        EventBus.getDefault().register(this);

        initToolbar();


//        if (bgMain != null)
//            bgMain.setOnTouchListener(this);

        /** yani mostaghim vared safheye asli shodeim. */
        // bareye check kardane inke bashgah darad ya na.
        if (G.currentLicense == null)
            G.currentLicense = new Select().from(License.class).executeSingle();

//        if (G.currentLicense != null && !G.currentLicense.Club) {
//            bgMain.setImageResource(R.drawable.bg_main_no_feshfeshe_club);
//            mask.setImageResource(R.drawable.mask_mainbg_no_feshfeshe_club);
//        } else if (G.currentLicense != null && G.currentLicense.Club) {
//            bgMain.setImageResource(R.drawable.bg_main0);
//            mask.setImageResource(R.drawable.mask_mainbg);
//        }

        WebService.sendGetUserAccountInfoRequest();
        initView();
    }

    public void initView() {

        layBtnBack.setOnClickListener(this);
        adapterMainItems = new AdapterMainItems(makeItemsList(), this);
        manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//        RcyItems.setHasFixedSize(true);
        RcyItems.setLayoutManager(manager);
        RcyItems.setAdapter(adapterMainItems);
        layBtnBack.setOnClickListener(this);

        txtName.setText(G.currentUserInfo != null ? G.currentUserInfo.fullName : "");
        layAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        if (G.currentUserInfo.Encoded64ImageString != null
                && !G.currentUserInfo.Encoded64ImageString.equals("")
                && !G.currentUserInfo.Encoded64ImageString.equals("null")) {
            showEncodedImage(G.currentUserInfo.Encoded64ImageString);
        } else {
            WebService.getAvatarImage();
        }

        adapterMainItems = new AdapterMainItems(makeItemsList(), this);
//        RcyItems.setHasFixedSize(true);
        RcyItems.setLayoutManager(manager);
        RcyItems.setAdapter(adapterMainItems);


        layExit.setOnClickListener(this);

    }

    public void showEncodedImage(String encodeString) {
        if (!encodeString.equals("") && !encodeString.equals("NotFound") && !encodeString.equals("null")) {
            G.hasImage = true;
            imgProfileBtn.setImageResource(R.drawable.ic_edit_image);
        } else {
            imgProfileBtn.setImageResource(R.drawable.ic_plus);
            imgAvatar.setImageResource(R.drawable.user);
        }
        final byte[] decodedBytes = Base64.decode(encodeString, Base64.DEFAULT);
//        Glide.with(G.context).load(decodedBytes).crossFade().fitCenter().into(imgAvatar);

        Glide.with(context).load(decodedBytes).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imgAvatar.setImageDrawable(resource);
            }
        });

    }

    private void choseImage() {
        EventBus.getDefault().post(new EventOnChoseImageClicked());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("ActivityMain : onResume()");
        G.currentActivity = this;
        context = this;
        adapterMainItems.notifyDataSetChanged();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//         toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_down));

    }


    public void onEventMainThread(EventOnClickedLogoutButton event) {
        Logger.d("ActivityMain : EventOnClickedLogoutButton is raised");
        G.currentUser.isLogin = false;
        G.currentUser.save();
        startActivity(new Intent(context, ActivityLogin.class));
        finish();
    }


    public void onEventMainThread(final EventOnGetStartFactorResponse event) {
        Logger.d("ActivityMain : EventOnGetStartFactorResponse is raised");
        WebService.sendGetUserAccountInfoRequest();
    }

    public void onEventMainThread(EventOnGetErrorGetNews event) {
        Logger.d("ActivityMain : EventOnGetErrorGetNews is raised");
    }

    public void onEventMainThread(EventOnGetNewsResponse event) {
        Logger.d("ActivityMain : EventOnGetNewsResponse is raised");
    }


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

    public void onEventMainThread(EventOnStartUploadingAvatar avatar) {
        dlgWaiting.DialogWaiting();
    }

    public void onEventMainThread(EventOnStopUploadingAvatar avatar) {
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnGetReperesenterURL reperesenterURL) {
        try {
             String txt = "با این لینک ثبت نام کنید  . . ";
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, txt + "\n\n" + reperesenterURL.getRepresenterURL());
            sendIntent.setType("text/plain");
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            new DialogClass().showMessageDialog(context.getString(R.string.error), context.getString(R.string.send_failure));

        }


//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.setType("Text/plain");
//        sendIntent.putExtra(Intent.EXTRA_TEXT, txt + "\n\n" + reperesenterURL.getRepresenterURL());
//        try {
//            context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.choose_sender)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        } catch (android.content.ActivityNotFoundException ex) {
//            new DialogClass().showMessageDialog(context.getString(R.string.error), context.getString(R.string.send_failure));
//        }


    }

    public void onEventMainThread(EventOnDiActiveItem item){
        DialogClass.showMessageDialog("خطا","این آیتم برای شما فعال نمیباشد");
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

    public void onEventMainThread(EventOnGetAvatarImageResponse response) {
        if (G.currentUserInfo.Encoded64ImageString != null
                && !G.currentUserInfo.Encoded64ImageString.equals("")
                && !G.currentUserInfo.Encoded64ImageString.equals("null")) {
            showEncodedImage(G.currentUserInfo.Encoded64ImageString);
        } else {
            if (!isAvatarDownloaded) {
                WebService.getAvatarImage();
                isAvatarDownloaded = true;
            }
        }
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
    public void onBackPressed() {
        startActivity(new Intent(ActivityMain0.this, ActivityShowCurrentService.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("ActivityMain : onDestroy()");
        EventBus.getDefault().unregister(this);
    }


    private List<MainItems> makeItemsList() {

        List<MainItems> mainItemsList = new ArrayList<>();
        MainItems empty = new MainItems();
        empty.setTag(EnumMainItemsTag.empty);
        empty.setName(getString(R.string.onlineSharj));
        empty.setImage(R.drawable.ic_charge_online);


        MainItems profile = new MainItems();
        profile.setTag(EnumMainItemsTag.profile);
        profile.setName(G.currentUserInfo.fullName);
        profile.setImage(R.drawable.main_profile);
        mainItemsList.add(profile);
        //1111111111
        mainItemsList.add(empty);
        //2222222222222222
//        if (G.currentLicense.Ticket) {
        MainItems support = new MainItems();
        support.setTag(EnumMainItemsTag.support);
        support.setName(getString(R.string.support));
        support.setImage(R.drawable.ic_support);
        mainItemsList.add(support);
//        }

        //33333333333333333
        MainItems payments = new MainItems();
        payments.setTag(EnumMainItemsTag.payments);
        payments.setName(getString(R.string.payReport));
        payments.setImage(R.drawable.ic_payments);
        mainItemsList.add(payments);
        //4444444444444444
        mainItemsList.add(empty);
//        mainItemsList.add(empty);
        //6666666666666
//

        MainItems messages = new MainItems();
        messages.setTag(EnumMainItemsTag.Messages);
        messages.setName(getString(R.string.messages));
        messages.setImage(R.drawable.ic_flag);
        mainItemsList.add(messages);
//        ///////////////////////

        //        if (G.currentLicense.connection) {
        MainItems connections = new MainItems();
        connections.setTag(EnumMainItemsTag.connections);
        connections.setName(getString(R.string.connectionReport));
        connections.setImage(R.drawable.ic_connections);
        mainItemsList.add(connections);
//        }

        //        if (G.currentLicense.Club) {
        MainItems scores = new MainItems();
        scores.setTag(EnumMainItemsTag.scores);
        scores.setName(getString(R.string.pointReports));
        scores.setImage(R.drawable.ic_scores);
        mainItemsList.add(scores);
//        }
//        //////////////////////////

        //        if (G.currentLicense.ChangePass) {
        MainItems changePass = new MainItems();
        changePass.setTag(EnumMainItemsTag.changePassword);
        changePass.setName(getString(R.string.change_password));
        changePass.setImage(R.drawable.ic_changepassword);
        mainItemsList.add(changePass);
//        }

        //        if (G.currentLicense.Chargeonline) {
        MainItems chargeOnline = new MainItems();
        chargeOnline.setTag(EnumMainItemsTag.chargeOnline);
        chargeOnline.setName(getString(R.string.onlineSharj));
        chargeOnline.setImage(R.drawable.main_charg_online);
        mainItemsList.add(chargeOnline);
//        }

        /////////////////
        mainItemsList.add(empty);
        //////////////////


        MainItems factores = new MainItems();
        factores.setTag(EnumMainItemsTag.factors);
        factores.setName(getString(R.string.factors));
        factores.setImage(R.drawable.main_factors);
        mainItemsList.add(factores);

        //7777777777777777
        mainItemsList.add(empty);
        mainItemsList.add(empty);
        mainItemsList.add(empty);
        //9999999999999999999
        //9999999999999999999
//

        //        if (G.currentLicense.Feshfeshe) {
        MainItems feshfeshe = new MainItems();
        feshfeshe.setTag(EnumMainItemsTag.feshfeshe);
        feshfeshe.setName(getString(R.string.feshfeshe));
        feshfeshe.setImage(R.drawable.ic_feshfeshe);
        mainItemsList.add(feshfeshe);
//        }

        //        if (G.currentLicense.trafficSplit) {
        MainItems trafficSplite = new MainItems();
        trafficSplite.setName(getString(R.string.traffic_splite));
        trafficSplite.setImage(R.drawable.ic_traffic_split);
        trafficSplite.setTag(EnumMainItemsTag.trafficeSplite);
        mainItemsList.add(trafficSplite);
//        }


//
//

//        //////////////////////////
//
//

//
//
//        if (G.currentLicense.ChangePass) {
//            MainItems changePass = new MainItems();
//            changePass.setTag(EnumMainItemsTag.changePassword);
//            changePass.setName(getString(R.string.change_password));
//            changePass.setImage(R.drawable.ic_changepassword);
//            mainItemsList.add(changePass);
//        }
//        ///////////////////////
//

//        MainItems testSpeed = new MainItems();
//        testSpeed.setName(getString(R.string.speedTest));
//        testSpeed.setImage(R.drawable.ic_test_speed);
//        testSpeed.setTag(EnumMainItemsTag.testSpeed);
//        mainItemsList.add(testSpeed);

        MainItems uploadDoc = new MainItems();
        uploadDoc.setName(getString(R.string.upload_doc));
        uploadDoc.setImage(R.drawable.ic_upload_doc);
        uploadDoc.setTag(EnumMainItemsTag.upload_doc);
        mainItemsList.add(uploadDoc);


        MainItems invite = new MainItems();
        invite.setName(getString(R.string.inviteFriends));
        invite.setImage(R.drawable.ic_invite);
        invite.setTag(EnumMainItemsTag.inviteFriends);
        mainItemsList.add(invite);

        MainItems news = new MainItems();
        news.setTag(EnumMainItemsTag.news);
        news.setName("اخبار");
        news.setImage(R.drawable.ic_news);
        mainItemsList.add(news);

        MainItems exit = new MainItems();
        exit.setName(getString(R.string.exit));
        exit.setImage(R.drawable.ic_exit);
        exit.setTag(EnumMainItemsTag.exit);
        mainItemsList.add(exit);



//        ///////////////////////////
//

//        ///////////////////////
//        mainItemsList.add(empty);
//        ////////////
//
//

//        /////////////////////////
//

        /////////////////////////

        return mainItemsList;
    }


    public boolean onTouch(View v, MotionEvent ev) {

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();

        // If we cannot find the imageView, return.
        ImageView bgMain = (ImageView) v.findViewById(R.id.bg_main);
        if (bgMain == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Logger.d("action down");
                break;

            case MotionEvent.ACTION_UP:
                Logger.d("action up");
                ColorTool ct = new ColorTool();
                int tolerance = 25;
                int touchColor = getHotspotColor(R.id.mask_mainbg, evX, evY);

                if (ct.closeMatch(Color.BLUE, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityPayments.class));
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowNotify.class));
                else if (ct.closeMatch(Color.GREEN, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowClubScores.class));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.purple))), touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowUserInfo.class));
                else if (ct.closeMatch(Color.DKGRAY, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowConnections.class));
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowGraph.class));
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance))
                    new DialogClass().showMessageDialog(getString(R.string.alert), getString(R.string.item_available_in_future));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.orange))), touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowTickets.class));
                else if (ct.closeMatch(Color.WHITE, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowFactors.class));
                else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance))
                    new DialogClass().showMessageDialog(getString(R.string.alert), getString(R.string.item_available_in_future));
//                    startActivity(new Intent(context, ActivityShowNews.class));
                else if (ct.closeMatch(Color.RED, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowFeshfeshe.class));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.brown))), touchColor, tolerance)) {
                    startActivity(new Intent(context, ActivityShowCurrentService.class));
//                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    finish();
                } else if (ct.closeMatch(Color.LTGRAY, touchColor, tolerance)) {
                    if (G.currentLicense != null) {
                        if (G.currentLicense.Chargeonline) {
                            startActivity(new Intent(context, ActivityChargeOnline.class));
                        } else {
                            U.toast("امکان شارژ آنلاین برای شما فعال     نمیباشد.");
                        }
                    }
                }

                break;
        } // end switch
        return true;
    }

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Logger.d("Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Logger.d("Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layExit:
                DialogClass dialogExit = new DialogClass();
                dialogExit.showExitDialog();
                break;
            case R.id.layBtnClose:
                startActivity(new Intent(context, ActivityShowCurrentService.class));
                break;


        }
    }
}
