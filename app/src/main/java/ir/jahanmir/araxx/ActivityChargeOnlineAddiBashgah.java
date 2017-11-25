package ir.jahanmir.araxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumChargeOnlineMenuItem;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetChargeOnlineForCountCategoryResponse;


public class ActivityChargeOnlineAddiBashgah extends AppCompatActivity {


    @Bind(R.id.layBtnAaddi)
    LinearLayout layBtnAaddi;
    @Bind(R.id.layAaddi)
    LinearLayout layAaddi;
    //    @Bind(R.id.layBtnBashgah)
//    LinearLayout layBtnBashgah;
    @Bind(R.id.layBashgah)
    LinearLayout layBashgah;
//    @Bind(R.id.layLoading)
//    LinearLayout layLoading;
//    @Bind(R.id.txtShowMessage)
//    TextView txtShowMessage;
//    @Bind(R.id.layBtnClose)
//    FrameLayout layBtnClose;
//    @Bind(R.id.txtPageTitle)
//    TextView txtPageTitle;
@Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;

    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    DialogClass dlgWaiting;

    int whichMenuItem;
    int isClub;
    int GROUP_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_addi_bashgah);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        G.context = this;
        G.currentActivity = this;
        initToolbar();
        dlgWaiting = new DialogClass(ActivityChargeOnlineAddiBashgah.this);

        Intent intent = getIntent();
        whichMenuItem = intent.getIntExtra("WHICH_MENU_ITEM", -1);
        isClub = intent.getIntExtra("IS_CLUB", -1);
        GROUP_CODE = intent.getIntExtra("GROUP_CODE", 0);

        initView();
        WebService.sendChargeOnlineForCountCategoryRequest(isClub, GROUP_CODE, whichMenuItem);
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_charg_online));
        imgIcon.setImageResource(R.drawable.ic_charge_online);
        txtName.setText(U.getMenuItemName(whichMenuItem));

    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;

        dlgWaiting.DialogWaitingClose();
    }

    private void initView() {
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        dlgWaiting.DialogWaitingClose();
//        whichMenuItem = getArguments().getInt("WHICH_MENU_ITEM");

//        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));
//        U.getMenuItemIcon(imgtoolbar, whichMenuItem);

//        layBtnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
////        });
    }

    public void onEventMainThread(final EventOnGetChargeOnlineForCountCategoryResponse event) {
        // agar tedade categoryha barabar ba 1 bood activitycategory nmayesh dade nemishavad   va group_code -1 ersal mishavad
        dlgWaiting.DialogWaitingClose();

        final int categorySize = event.getChargeOnlineCategoryList().size();
        final int whichMenuItem = event.getWhichMenuItem();
        final Long groupCode = event.getGroupCode();
        final int isClub = event.isClub();


        layAaddi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgWaiting.DialogWaiting();
                switch (whichMenuItem) {
                    case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                        WebService.sendChargeOnlineForTamdidRequest(0);
                        break;

                    case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                        Intent intent = new Intent(G.context, ActivityChargeOnlineGroup.class);
                        intent.putExtra("IS_CLUB", 0);
                        intent.putExtra("WHICH_MENU_ITEM", EnumChargeOnlineMenuItem.TAGHIR_SERVICE);
                        startActivity(intent);
                        finish();

                        break;
                    case EnumChargeOnlineMenuItem.TRAFFIC:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 0);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                            startActivity(intentTRAFFIC);
                            finish();

                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 0);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                            finish();

                        }
                        break;
                    case EnumChargeOnlineMenuItem.IP:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 0);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                            startActivity(intentTRAFFIC);
                            finish();

                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 0);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                            finish();

                        }
                        break;
                    case EnumChargeOnlineMenuItem.FESHFESHE:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 0);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                            startActivity(intentTRAFFIC);
                            finish();

                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 0);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                            finish();

                        }
                        break;
                    case EnumChargeOnlineMenuItem.TAGHSIM_TERAFIC:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 0);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", groupCode);
                            startActivity(intentTRAFFIC);
                            finish();

                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 0);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                            finish();
                        }
                        break;
                }
            }
        });


        layBashgah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgWaiting.DialogWaiting();
                switch (whichMenuItem) {
                    case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                        WebService.sendChargeOnlineForTamdidRequest(0);
                        break;

                    case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                        Intent intent = new Intent(G.context, ActivityChargeOnlineGroup.class);
                        intent.putExtra("IS_CLUB", 1);
                        intent.putExtra("WHICH_MENU_ITEM", EnumChargeOnlineMenuItem.TAGHIR_SERVICE);
                        startActivity(intent);
                        break;
                    case EnumChargeOnlineMenuItem.TRAFFIC:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 1);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                            startActivity(intentTRAFFIC);
                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 1);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                        }
                        break;
                    case EnumChargeOnlineMenuItem.IP:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 1);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                            startActivity(intentTRAFFIC);
                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 1);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                        }
                        break;
                    case EnumChargeOnlineMenuItem.FESHFESHE:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 1);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                            startActivity(intentTRAFFIC);
                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 1);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                        }
                        break;
                    case EnumChargeOnlineMenuItem.TAGHSIM_TERAFIC:
                        if (categorySize > 1) {
                            Intent intentTRAFFIC = new Intent(G.context, ActivityChargeOnlineCategory.class);
                            intentTRAFFIC.putExtra("IS_CLUB", 1);
                            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                            intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                            startActivity(intentTRAFFIC);
                        } else {
                            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                            i.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                            i.putExtra("IS_CLUB", 1);
                            i.putExtra("GROUP_CODE", groupCode);
                            i.putExtra("CATEGORY_CODE", -1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            G.context.startActivity(i);
                        }
                        break;
                }
            }
        });


    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaiting();
    }
}


