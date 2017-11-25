package ir.jahanmir.aspa;//package ir.aspacrm.my.app.mahan;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.Toolbar;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.activeandroid.query.Select;
//import com.pnikosis.materialishprogress.ProgressWheel;
//
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import de.greenrobot.event.EventBus;
//import ir.aspacrm.my.app.mahanet.classes.DialogClass;
//import ir.aspacrm.my.app.mahanet.classes.Downloader;
//import ir.aspacrm.my.app.mahanet.classes.Logger;
//import ir.aspacrm.my.app.mahanet.classes.U;
//import ir.aspacrm.my.app.mahanet.classes.WebService;
//import ir.aspacrm.my.app.mahanet.enums.EnumDownloadID;
//import ir.aspacrm.my.app.mahanet.events.EventOnCanceledDialogUpdatingApplication;
//import ir.aspacrm.my.app.mahanet.events.EventOnChangedDownloadPercent;
//import ir.aspacrm.my.app.mahanet.events.EventOnCheckGetPollRequest;
//import ir.aspacrm.my.app.mahanet.events.EventOnClickedLogoutButton;
//import ir.aspacrm.my.app.mahanet.events.EventOnDownloadedFileCompleted;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetIspInfo;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetNews;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetNotifies;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetUserAccountInfo;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorSetPoll;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorUserLicense;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetIspInfoResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetNewsResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetNotifiesResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetPollResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetStartFactorResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetUpdateResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetUserAccountInfoResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnGetUserLicenseResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnNoAccessServerResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnSendPollRequest;
//import ir.aspacrm.my.app.mahanet.events.EventOnSetPollResponse;
//import ir.aspacrm.my.app.mahanet.events.EventOnShowDialogUpdatingApplicationRequest;
//import ir.aspacrm.my.app.mahanet.gson.GetIspInfoResponse;
//import ir.aspacrm.my.app.mahanet.model.Account;
//import ir.aspacrm.my.app.mahanet.model.Info;
//import ir.aspacrm.my.app.mahanet.model.License;
//import ir.aspacrm.my.app.mahanet.model.News;
//import ir.aspacrm.my.app.mahanet.model.Notify;
//import me.leolin.shortcutbadger.ShortcutBadgeException;
//import me.leolin.shortcutbadger.ShortcutBadger;
//
//public class ActivityMain extends AppCompatActivity implements OnClickListener {
//
//
//    @Bind(R.id.progressTraffic)
//    ProgressWheel progressTraffic;
//    @Bind(R.id.progressDays)
//    ProgressWheel progressDays;
//    @Bind(R.id.imgLogout)
//    ImageView imgLogout;
//    @Bind(R.id.imgChangePassword)
//    ImageView imgChangePassword;
//    @Bind(R.id.imgUserInfo)
//    ImageView imgUserInfo;
//    @Bind(R.id.imgNews)
//    ImageView imgNews;
//    @Bind(R.id.imgNotification)
//    ImageView imgNotification;
//    @Bind(R.id.imgCompanyInfo)
//    ImageView imgCompanyInfo;
//    @Bind(R.id.imgFeshfeshe)
//    ImageView imgFeshfeshe;
//    @Bind(R.id.imgClub)
//    ImageView imgClub;
//    @Bind(R.id.layPackageName)
//    FrameLayout layPackageName;
//    @Bind(R.id.layGroupName)
//    FrameLayout layGroupName;
//    @Bind(R.id.txtRemainTraffic)
//    TextView txtRemainTraffic;
//    @Bind(R.id.txtRemainDay)
//    TextView txtRemainDay;
//    @Bind(R.id.layPardakhtha)
//    LinearLayout layPardakhtha;
//    @Bind(R.id.laySuratHesab)
//    LinearLayout laySuratHesab;
//    @Bind(R.id.laySavabeghEtesal)
//    LinearLayout laySavabeghEtesal;
//    @Bind(R.id.layTicket)
//    LinearLayout layTicket;
//    @Bind(R.id.layNemudarMasraf)
//    LinearLayout layNemudarMasraf;
//    @Bind(R.id.layChargeOnline)
//    LinearLayout layChargeOnline;
//    @Bind(R.id.layFeshfesheBashgah)
//    LinearLayout layFeshfesheBashgah;
//    @Bind(R.id.layUnSeenNews)
//    LinearLayout layUnSeenNews;
//    @Bind(R.id.layUnSeenNotify)
//    LinearLayout layUnSeenNotify;
//    @Bind(R.id.txtUnSeenNews)
//    TextView txtUnSeenNews;
//    @Bind(R.id.txtUnSeenNotify)
//    TextView txtUnSeenNotify;
//    @Bind(R.id.layBtnVaslMovaghat)
//    CardView layBtnVaslMovaghat;
//    @Bind(R.id.layLoading)
//    LinearLayout layLoading;
//    @Bind(R.id.layAccountInfo)
//    LinearLayout layAccountInfo;
//    @Bind(R.id.imgDrawerToggle)
//    ImageView imgDrawerToggle;
//
//    TextView txtPackageName;
//    TextView txtGroupName;
//    int level = 0;
//    DialogClass dlgUpdate;
//    DialogClass dlgShowPoll;
//    Downloader downloader = null;
//    boolean downloadedCompleted;
//
//    Toolbar toolbar;
//    DrawerLayout drawerLayout;
//
//    DialogClass dlgWaiting;
//    ActionBarDrawerToggle mActionBarDrawerToggle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
//        initToolbar();
//
//        layLoading.setVisibility(View.INVISIBLE);
//        layBtnVaslMovaghat.setVisibility(View.INVISIBLE);
//
//        layUnSeenNews.setVisibility(View.GONE);
//        layUnSeenNotify.setVisibility(View.GONE);
//
//        txtPackageName = (TextView) layPackageName.findViewById(R.id.txtValue);
//        txtGroupName = (TextView) layGroupName.findViewById(R.id.txtValue);
//
//        imgLogout.setOnClickListener(this);
//        imgChangePassword.setOnClickListener(this);
//        layPardakhtha.setOnClickListener(this);
//        laySuratHesab.setOnClickListener(this);
//        laySavabeghEtesal.setOnClickListener(this);
//        layTicket.setOnClickListener(this);
//        layNemudarMasraf.setOnClickListener(this);
//        layChargeOnline.setOnClickListener(this);
//        imgUserInfo.setOnClickListener(this);
//        imgNews.setOnClickListener(this);
//        imgNotification.setOnClickListener(this);
//        imgCompanyInfo.setOnClickListener(this);
//        imgClub.setOnClickListener(this);
//        imgFeshfeshe.setOnClickListener(this);
//
//        if (getIntent().getExtras() != null) {
//            /** yani az safheye login vared safhe asli shodeim. */
////            AccountInfoResponse jsonAccountInfo = new Gson().fromJson(getIntent().getExtras().getString("JSON_ACCOUNT_INFO"),AccountInfoResponse.class);
//            initializeUserAccountView();
//        } else {
//            /** yani mostaghim vared safheye asli shodeim. */
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // bareye check kardane inke bashgah darad ya na.
//                    if (G.currentLicense == null)
//                        G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();
//
//                    if (G.currentLicense != null && !G.currentLicense.Club)
//                        layFeshfesheBashgah.setVisibility(View.INVISIBLE);
//                    else if (G.currentLicense != null && G.currentLicense.Club) {
//                        layFeshfesheBashgah.setVisibility(View.VISIBLE);
//                    }
//
//                    G.handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            dlgWaiting = new DialogClass();
//                            dlgWaiting.DialogWaitingWithBackground(ActivityMain.this);
//                        }
//                    });
//                    WebService.sendGetUserLicenseRequest();
//                }
//            }).start();
//        }
//        /** check application update */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                sendRequestGetNews();
//                WebService.getUpdateRequest();
//            }
//        }).start();
//
//        layBtnVaslMovaghat.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                WebService.sendRegConnectRequest();
//                layBtnVaslMovaghat.setClickable(false);
//                layLoading.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    private void initToolbar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
////         toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_down));
//        drawerLayout = (DrawerLayout) findViewById(R.id.main_navgdrawer);
//        setupDrawerToggleInActionBar();
//    }
//
//    private void setupDrawerToggleInActionBar() {
//        imgDrawerToggle.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                    drawerLayout.closeDrawer(Gravity.RIGHT);
//                } else {
//                    drawerLayout.openDrawer(Gravity.RIGHT);
//                }
//            }
//        });
//
//        // setup the action bar properties that give us a hamburger menu
//        ActionBar actionBar = this.getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//        }
//
//        //  the toggle allows for the simplest of open/close handling
//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
//                drawerLayout,
//                R.string.open,
//                R.string.close);
//        // drawerListener must be set before syncState is called
//        drawerLayout.setDrawerListener(mActionBarDrawerToggle);
//        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//        mActionBarDrawerToggle.syncState();
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                    drawerLayout.closeDrawer(Gravity.RIGHT);
//                } else {
//                    drawerLayout.openDrawer(Gravity.RIGHT);
//                }
//            }
//        });
//
//    }
//
//
//    public void onEventMainThread(EventOnGetUserLicenseResponse event) {
//        Logger.d("ActivityMain : EventOnGetUserLicenseResponse is raised.");
//
//        if (G.currentLicense == null)
//            G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();
//
//        if (G.currentLicense != null && !G.currentLicense.Club)
//            layFeshfesheBashgah.setVisibility(View.INVISIBLE);
//        else if (G.currentLicense != null && G.currentLicense.Club) {
//            layFeshfesheBashgah.setVisibility(View.VISIBLE);
//        }
//        WebService.sendGetUserAccountInfoRequest();
//    }
//
//    public void onEventMainThread(EventOnGetErrorUserLicense event) {
//        Logger.d("ActivityMain : EventOnGetErrorUserLicense is raised.");
//        initializeUserAccountView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Logger.d("ActivityMain : onResume()");
//        G.currentActivity = this;
////        try {
////            ShortcutBadger.removeCountOrThrow(G.context);
////        } catch (ShortcutBadgeException e) {
////            //e.printStackTrace();
////        }
//        U.cancelNotification(1367);
//        checkNewNews();
//        checkNewNotify();
//    }
//
//    @Override
//    public void onClick(final View view) {
//        Animation clickAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_click);
//        clickAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                switch (view.getId()) {
//                    case R.id.imgLogout:
//                        DialogClass dialogExit = new DialogClass();
//                        dialogExit.showExitDialog();
//                        break;
//                    case R.id.imgChangePassword:
//                        if (G.currentLicense != null) {
//                            if (G.currentLicense.ChangePass) {
//                                DialogClass dialogChangePassword = new DialogClass();
//                                dialogChangePassword.showChangePasswordDialog();
//                            } else {
//                                U.toast("امکان تغییر رمز برای شما فعال نیست.");
//                            }
//                        }
//                        break;
//                    case R.id.layPardakhtha:
//                        startActivity(new Intent(G.context, ActivityPayments.class));
//                        break;
//                    case R.id.laySuratHesab:
//                        startActivity(new Intent(G.context, ActivityShowFactors.class));
//                        break;
//                    case R.id.laySavabeghEtesal:
//                        startActivity(new Intent(G.context, ActivityShowConnections.class));
//                        break;
//                    case R.id.layTicket:
//                        if (G.currentLicense != null) {
//                            if (G.currentLicense.ticket) {
//                                startActivity(new Intent(G.context, ActivityShowTickets.class));
//                            } else {
//                                U.toast("امکان ارسال تیکت برای شما فعال نمی باشد.");
//                            }
//                        }
//                        break;
//                    case R.id.layNemudarMasraf:
//                        startActivity(new Intent(G.context, ActivityShowGraph.class));
//                        break;
//                    case R.id.layChargeOnline:
//                        if (G.currentLicense != null) {
//                            if (G.currentLicense.Chargeonline) {
//                                startActivity(new Intent(G.context, ActivityChargeOnline.class));
//                            } else {
//                                U.toast("امکان شارژ آنلاین برای شما فعال نمیباشد.");
//                            }
//                        }
//                        break;
//                    case R.id.imgUserInfo:
//                        startActivity(new Intent(G.context, ActivityShowUserInfo.class));
//                        break;
//                    case R.id.imgNews:
//                        startActivity(new Intent(G.context, ActivityShowNews.class));
//                        break;
//                    case R.id.imgNotification:
//                        startActivity(new Intent(G.context, ActivityShowNotify.class));
//                        break;
//                    case R.id.imgCompanyInfo:
//                        WebService.sendGetIspInfoRequest();
//                        break;
//                    case R.id.imgFeshfeshe:
//                        startActivity(new Intent(G.context, ActivityShowFeshfeshe.class));
//                        break;
//                    case R.id.imgClub:
//                        startActivity(new Intent(G.context, ActivityShowClubScores.class));
//                        break;
//                }
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.startAnimation(clickAnimation);
//    }
//
//    /**
//     * gereftane about sherkat bad az darkhaste etelaate sherkat dar ghesmate menu paein
//     * bad az gereftane javab az webserice, vared in ghesmat mishavim.
//     */
//    public void onEventMainThread(EventOnGetIspInfoResponse event) {
//        Logger.d("ActivityMain : EventOnGetIspInfoResponse is raised");
//        GetIspInfoResponse response = event.getIspInfo();
//        if (response.Result) {
//            DialogClass dlgShowCompanyInfo = new DialogClass();
//            dlgShowCompanyInfo.showCompanyDetailDialog(response);
//        } else {
//            U.toast("خطا در دریافت اطلاعات از سرور");
//        }
//    }
//
//    public void onEventMainThread(EventOnGetErrorGetIspInfo event) {
//        Logger.d("ActivityMain : EventOnGetErrorGetIspInfo is raised");
//        G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();
//        G.currentLicense = new Select().from(License.class).where("userId = ? ", G.currentUser.userId).executeSingle();
//        initializeUserAccountView();
//    }
//
//    /**
//     * geteftane etelaate moshtarak baraye namayesh dar safhe aval
//     * bad az gereftane etelate moshtarak shamel hajme baghi mande va ruzaye baghi mande
//     * darkhate ersal inke moshtarak tavasote mobile vared hesabe karbari khod shode ast ersal mishavad.
//     * sepass darkhaste gereftane khabar'haye jadid ra midahim.
//     */
//    public void onEventMainThread(EventOnGetUserAccountInfoResponse event) {
//        Logger.d("ActivityMain : EventOnGetUserAccountInfoResponse is raised");
//        initializeUserAccountView();
//        /** Send this request to get point for current User if set
//         * from managment.*/
//        WebService.sendVisitMobileRequest();
//    }
//
//    public void onEventMainThread(EventOnGetErrorGetUserAccountInfo event) {
//        Logger.d("ActivityMain : EventOnGetErrorGetUserAccountInfo is raised");
//        G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();
//        G.currentLicense = new Select().from(License.class).where("userId = ? ", G.currentUser.userId).executeSingle();
//        initializeUserAccountView();
//    }
//
//    /**
//     * bad az gereftane khabar haye jadid check mikonim bebinim chandta az khabar
//     * haye daryafti jadid bude astta eghdam be namayeshe tedade anha be karbar bashim.
//     * sepass eghdam be ersal darkhaste tedade
//     */
//    public void onEventMainThread(EventOnGetNewsResponse event) {
//        Logger.d("ActivityMain : EventOnGetNewsResponse is raised");
//        /** mohasebe khabare jadid daryafti*/
//        checkNewNews();
//        /**be dast avardane akharin notify daryafi va ersal code aan baraye
//         * gerfetane notifay'haye jadid ehtemalli*/
//        sendRequestGetNotify();
//    }
//
//    public void onEventMainThread(EventOnGetErrorGetNews event) {
//        Logger.d("ActivityMain : EventOnGetErrorGetNews is raised");
//        /** mohasebe khabare jadid daryafti*/
//        checkNewNews();
//        /** mohasebe notify'haye jadid daryafti*/
//        checkNewNotify();
//    }
//
//    public void onEventMainThread(EventOnGetNotifiesResponse event) {
//        Logger.d("ActivityMain : EventOnGetNotifiesResponse is raised");
//        /** mohasebe notify'haye jadid daryafti*/
//        checkNewNotify();
//    }
//
//    public void onEventMainThread(EventOnGetErrorGetNotifies event) {
//        Logger.d("ActivityMain : EventOnGetErrorGetNotifies is raised");
//        /** mohasebe notify'haye jadid daryafti*/
//        checkNewNotify();
//    }
//
//    public void onEventMainThread(EventOnNoAccessServerResponse event) {
//        if (dlgShowPoll != null)
//            dlgShowPoll.showErrorOnPollDialog("لطفا ارتباط اینترنتی خود را چک کرده و سپس مجددا تلاش کنید.");
//    }
//
//    public void onEventMainThread(EventOnClickedLogoutButton event) {
//        Logger.d("ActivityMain : EventOnClickedLogoutButton is raised");
//        G.currentUser.isLogin = false;
//        G.currentUser.save();
//        startActivity(new Intent(G.context, ActivityLogin.class));
//        finish();
//    }
//
//    public void onEventMainThread(EventOnSendPollRequest event) {
//        Logger.d("ActivityMain : EventOnSendPollRequest is raised");
//        WebService.sendSetPollRequest(event.getPollId(), event.getOptionId(), event.getDes());
//    }
//
//    public void onEventMainThread(EventOnSetPollResponse event) {
//        Logger.d("ActivityMain : EventOnSetPollResponse is raised");
//        if (event.getStatus()) {
//            /** dar surati ke pasukhe ma be dorosti sabt shode bashad.*/
//            if (dlgShowPoll != null)
//                dlgShowPoll.cancelPollDialog();
//            U.toast("نظر شما با موفقیت ثبت شد.");
//        } else {
//            if (dlgShowPoll != null)
//                dlgShowPoll.showErrorOnPollDialog("ثبت نظر با مشکل مواجه شد، لطفا دوباره تلاش کنید.");
//        }
//
//    }
//
//    public void onEventMainThread(EventOnGetErrorSetPoll event) {
//        Logger.d("ActivityMain : EventOnGetErrorSetPoll is raised");
//        if (dlgShowPoll != null)
//            dlgShowPoll.showErrorOnPollDialog("ثبت نظر با مشکل مواجه شد، لطفا دوباره تلاش کنید.");
//    }
//
//    public void onEventMainThread(EventOnGetPollResponse event) {
//        Logger.d("ActivityMain : EventOnGetPollResponse is raised");
//        if (event.getPollResponse().Result) {
//            dlgShowPoll = new DialogClass();
//            dlgShowPoll.showPollDialog(event.getPollResponse());
//        }
//    }
//
//    public void onEventMainThread(EventOnCheckGetPollRequest event) {
//        Logger.d("ActivityMain : EventOnCheckGetPollRequest is raised");
//        /** darkhate check karane inke nazar sanji vojud darad ya na.*/
//        WebService.sendGetPollRequest();
//    }
//
//    /* Update EventBus Method */
//    public void onEventMainThread(EventOnGetUpdateResponse event) {
//        Logger.d("ActivityMain : EventOnGetUpdateResponse is raised");
//        try {
//            String version = event.getUpdateResponse().Ver;
//            if (version.length() == 0)
//                version = "0.0";
//            if (Float.parseFloat(version) > Float.parseFloat(U.getAppVersionName())) {
//                dlgUpdate = new DialogClass();
//                dlgUpdate.showUpdateApplicationDialog(event.getUpdateResponse().Ver, event.getUpdateResponse().Force, event.getUpdateResponse().Url);
//            } else {
//                /** darkhate check karane inke nazar sanji vojud darad ya na.*/
//                WebService.sendGetPollRequest();
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    public void onEventMainThread(EventOnShowDialogUpdatingApplicationRequest event) {
//        Logger.d("ActivityMain : EventOnShowDialogUpdatingApplicationRequest is raised");
//        if (dlgUpdate != null) {
//            dlgUpdate.showUpdatingApplicationDialog(event.getNewVersion(), event.isForce(), event.getUrl());
//            downloader = new Downloader();
//            downloader.requestDownload(event.getUrl(), EnumDownloadID.ACTIVITY_MAIN);
//        }
//    }
//
//    public void onEventMainThread(EventOnCanceledDialogUpdatingApplication event) {
//        Logger.d("ActivityMain : EventOnCanceledDialogUpdatingApplication is raised");
//        if (dlgUpdate != null && event.isForce()) {
//            /** dar surati ke download update ejbari bashad ba cancel kardane dialog updating baz bayad
//             * dialog update application namayesh dade shavad.*/
//            dlgUpdate.showUpdateApplicationDialog(event.getNewVersion(), event.isForce(), event.getUrl());
//            /** cancel current doanload and delete raw downloaded file*/
//            downloader.cancelDownload();
//        }
//    }
//
//    public void onEventMainThread(EventOnChangedDownloadPercent event) {
//        Logger.d("ActivityMain : EventOnChangedDownloadPercent is raised");
//        if (dlgUpdate != null) {
//            dlgUpdate.changeProgressPercent(event.getPercent());
//        }
//    }
//
//    public void onEventMainThread(EventOnDownloadedFileCompleted event) {
//        Logger.d("ActivityMain : EventOnDownloadedFileCompleted is raised");
//        if (dlgUpdate != null) {
//            downloadedCompleted = true;
//            dlgUpdate.showInstallButton();
//        }
//    }
//
//    public void onEventMainThread(final EventOnGetStartFactorResponse event) {
//        Logger.d("ActivityMain : EventOnGetStartFactorResponse is raised");
//        WebService.sendGetUserAccountInfoRequest();
//    }
//
//    /*-------------------------------------------------------------------------------------------------------------*/
//    public void initializeUserAccountView() {
//
//        if (dlgWaiting != null) {
//            dlgWaiting.cancelDialogWaitingWithBackground();
//        }
//        if (G.currentLicense == null)
//            G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();
//
//        if (G.currentLicense != null && !G.currentLicense.Club)
//            layFeshfesheBashgah.setVisibility(View.INVISIBLE);
//        else if (G.currentLicense != null && G.currentLicense.Club) {
//            layFeshfesheBashgah.setVisibility(View.VISIBLE);
//        }
//
//        if (G.currentAccount == null)
//            G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();
//
//
//        if (G.currentAccount.regConnect) {
//            /** namayesh halate vasl movaghat.*/
//            layAccountInfo.setVisibility(View.INVISIBLE);
//            layLoading.setVisibility(View.INVISIBLE);
//            layBtnVaslMovaghat.setVisibility(View.VISIBLE);
//        } else {
//            layAccountInfo.setVisibility(View.VISIBLE);
//            layLoading.setVisibility(View.INVISIBLE);
//            layBtnVaslMovaghat.setVisibility(View.INVISIBLE);
//        }
//
//        txtPackageName.setText("" + G.currentAccount.pkgName);
//        txtGroupName.setText("" + G.currentAccount.grpName);
//
//        if (G.currentAccount.rHour == -11111) {
//            if (G.currentAccount.rDay == -11111) {
//                txtRemainDay.setText("نامحدود");
//                setProgressValue(progressDays, 100);
//            } else {
//                txtRemainDay.setText("" + G.currentAccount.rDay + "\n" + "روز");
//                setProgressValue(progressDays, G.currentAccount.dPerc);
//            }
//        } else {
//            if (G.currentAccount.rHour == -11111) {
//                txtRemainDay.setText("نامحدود");
//                setProgressValue(progressDays, 100);
//            } else {
//                txtRemainDay.setText("" + G.currentAccount.rHour + "\n" + "ساعت");
//                setProgressValue(progressDays, G.currentAccount.dPerc);
//            }
//        }
//        if (G.currentAccount.rTraffic == -11111) {
//            txtRemainTraffic.setText("نامحدود");
//            setProgressValue(progressTraffic, 100);
//        } else {
//            txtRemainTraffic.setText(G.currentAccount.rTraffic + "\n" + "مگابایت");
//            setProgressValue(progressTraffic, G.currentAccount.tPerc);
//        }
//        /** mohasebe khabare jadid daryafti*/
//        checkNewNews();
//        checkNewNotify();
//    }
//
//    public void setProgressValue(final ProgressWheel progressWheel, final int percent) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 1; i <= percent; i++) {
//                    final int finalI = i;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressWheel.setInstantProgress((float) finalI / 100);
//                        }
//                    });
//                    try {
//                        Thread.sleep(30);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//
//    private void sendRequestGetNews() {
//        News lastNews = new Select()
//                .from(News.class)
//                .where("UserId = ? ", G.currentUser.userId)
//                .orderBy("code desc")
//                .limit(1)
//                .executeSingle();
//        if (lastNews == null) {
//            WebService.sendGetNewsRequest(0);
//        } else {
//            WebService.sendGetNewsRequest(lastNews.newsID);
//        }
//    }
//
//    private void sendRequestGetNotify() {
//        Notify lastNotify = new Select()
//                .from(Notify.class)
//                .where("UserId = ? ", G.currentUser.userId)
//                .orderBy("NotifyCode desc")
//                .limit(1)
//                .executeSingle();
//        if (lastNotify == null) {
//            WebService.sendGetNotifiesRequest(0, false);
//        } else {
//            WebService.sendGetNotifiesRequest(lastNotify.notifyCode, false);
//        }
//    }
//
//    private void checkNewNews() {
//        List<News> unSeenNews = new Select()
//                .from(News.class)
//                .where("UserId = ? AND IsSeen = ?", G.currentUser.userId, false)
//                .execute();
//        int countUnSeenNews = unSeenNews.size();
//        if (countUnSeenNews != 0) {
//            txtUnSeenNews.setText("" + countUnSeenNews);
//            layUnSeenNews.setVisibility(View.VISIBLE);
//        } else {
//            layUnSeenNews.setVisibility(View.GONE);
//        }
//    }
//
//    private void checkNewNotify() {
//        List<Notify> unSeenNotify = new Select()
//                .from(Notify.class)
//                .where("UserId = ? AND IsSeen = ?", G.currentUser.userId, false)
//                .execute();
//        int countUnSeenNotify = unSeenNotify.size();
//        if (countUnSeenNotify != 0) {
//            txtUnSeenNotify.setText("" + countUnSeenNotify);
//            try {
//                ShortcutBadger.applyCountOrThrow(G.context, countUnSeenNotify); //for 1.1.4+
//            } catch (ShortcutBadgeException e) {
//                //e.printStackTrace();
//                Logger.d("" + e.getMessage());
//            }
//
//            layUnSeenNotify.setVisibility(View.VISIBLE);
//        } else {
//            layUnSeenNotify.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Logger.d("ActivityMain : onDestroy()");
//        EventBus.getDefault().unregister(this);
//    }
//
//}
