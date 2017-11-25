package ir.jahanmir.araxx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.CustomEditText;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.enums.EnumResponse;
import ir.jahanmir.araxx.events.EventOnErrorInConnectingToServer;
import ir.jahanmir.araxx.events.EventOnGetErrorGetChargeOnlineMainItems;
import ir.jahanmir.araxx.events.EventOnGetErrorGetIspInfo;
import ir.jahanmir.araxx.events.EventOnGetErrorGetUserAccountInfo;
import ir.jahanmir.araxx.events.EventOnGetErrorGetUserInfo;
import ir.jahanmir.araxx.events.EventOnGetErrorIspUrl;
import ir.jahanmir.araxx.events.EventOnGetErrorLogin;
import ir.jahanmir.araxx.events.EventOnGetErrorSendRememberPassword;
import ir.jahanmir.araxx.events.EventOnGetIspInfoLoginResponse;
import ir.jahanmir.araxx.events.EventOnGetIspUrlResponse;
import ir.jahanmir.araxx.events.EventOnGetLoginResponse;
import ir.jahanmir.araxx.events.EventOnGetUserAccountInfoResponse;
import ir.jahanmir.araxx.events.EventOnGetUserInfoResponse;
import ir.jahanmir.araxx.events.EventOnGetUserLicenseResponse;
import ir.jahanmir.araxx.events.EventOnNetworkStatusChange;
import ir.jahanmir.araxx.events.EventOnNoAccessServerResponse;
import ir.jahanmir.araxx.events.EventOnRememberPasswordResponse;
import ir.jahanmir.araxx.gson.ISPInfoLoginResponse;
import ir.jahanmir.araxx.gson.LoginResponse;
import ir.jahanmir.araxx.model.Account;
import ir.jahanmir.araxx.model.User;

import static ir.jahanmir.araxx.G.context;


/**
 * Created by Microsoft on 3/3/2016.
 */
public class ActivityLogin extends AppCompatActivity {

    //    @Bind(R.id.imgIspLogo)
//    ImageView imgIspLogo;
    @Bind(R.id.txtIspName)
    TextView txtIspName;
    @Bind(R.id.edtUsername)
    CustomEditText edtUsername;
    @Bind(R.id.edtPassword)
    CustomEditText edtPassword;
    @Bind(R.id.txtForgetPassword)
    TextView txtForgetPassword;
    @Bind(R.id.txtSearchIsp)
    TextView txtSearchIsp;
    @Bind(R.id.txtRegister)
    TextView txtRegister;
    @Bind(R.id.layLogin)
    LinearLayout layLogin;
    @Bind(R.id.edtUsernameForget)
    CustomEditText edtUsernameForget;
    @Bind(R.id.edtMobileNumberForget)
    CustomEditText edtMobileNumberForget;

    @Bind(R.id.txtBackToLoginPage)
    TextView txtBackToLoginPage;
    @Bind(R.id.layForgetPassword)
    LinearLayout layForgetPassword;
    @Bind(R.id.layContent)
    FrameLayout layContent;

    @Bind(R.id.layBtnForgetPassword)
    FrameLayout layBtnForgetPassword;
    @Bind(R.id.layBtnLogin)
    FrameLayout layBtnLogin;

    @Bind(R.id.txtAspaCrm)
    PersianTextViewNormal txtAspaCrm;

    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.layBtnRegister)
    LinearLayout layBtnRegister;

    String ispUrl = "";
    long ispId;
    boolean isShowPassword = false;

    DialogClass dlgWaiting;
    Animation animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login0);
        ButterKnife.bind(this);
        dlgWaiting = new DialogClass(this);
        txtRegister.setVisibility(View.GONE);
        txtIspName.setText("" + U.getApplicationName());
        txtAspaCrm.setText("سامانه اسپا ، نسخه "+G.versionName);
        txtAspaCrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://aspacrm.ir"));
                context.startActivity(i);
            }
        });

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.dark_dark_grey));

        ((TextView) (layBtnForgetPassword.findViewById(R.id.txtValue))).setText("درخواست رمز عبور جدید");
        ((ImageView) (layBtnForgetPassword.findViewById(R.id.img))).setVisibility(View.GONE);
        ((TextView) (layBtnLogin.findViewById(R.id.txtValue))).setText("ورود");

        /** draw line other textview */
        txtForgetPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtSearchIsp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtRegister.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtBackToLoginPage.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        if (getIntent().getExtras() != null) {
            /** dar surati ke az safhe search isp vared safhe login shavim.*/
            ispUrl = getIntent().getExtras().getString("ISP_URL");
            ispId = getIntent().getExtras().getLong("ISP_ID");
//            imgIspLogo.setImageResource(R.mipmap.ic_launcher);
            WebService.sendGetIspInfoLoginRequest(ispUrl);
        } else {
            /** dar surati ke karbar yakbar login karde bashad va mostaghiman az safheye login vared barname shavim.*/
            User lastUserLogin = new Select().from(User.class).where("isLastLogin = ? ", true).executeSingle();
            if (lastUserLogin != null) {
                ispId = lastUserLogin.ispId;
            } else {
                ispId = getResources().getInteger(R.integer.customer_id);
            }
        }
        Logger.d("ActivityMain : ISP_ID is " + ispId);
        /** Send get selected isp url */
        WebService.sendGetIspUrlRequest(ispId);

        layBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*hide soft keyboard*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                sendLoginRequest();
            }
        });
        edtPassword.setDrawableClickListener(new CustomEditText.DrawableClickListener() {
            public void onClick(CustomEditText.DrawableClickListener.DrawablePosition target) {
                switch (target) {
                    case LEFT:
                        if (!isShowPassword) {
                            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility_off_white_24dp, 0, R.drawable.ic_lock_white_24dp, 0);
                        } else {
                            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility_white_24dp, 0, R.drawable.ic_lock_white_24dp, 0);
                        }
                        isShowPassword = !isShowPassword;
                        edtPassword.setSelection(edtPassword.length());
                        break;
                    default:
                        break;
                }
            }
        });

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgetPasswordLayout();
            }
        });
        txtBackToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginLayout();
            }
        });
        txtSearchIsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivitySearchISP.class));
                finish();
            }
        });
        layBtnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*hide soft keyboard*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                sendForgetPassword();
            }
        });
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
                    sendLoginRequest();
                    return true;
                }
                return false;
            }
        });
        edtMobileNumberForget.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtMobileNumberForget.getWindowToken(), 0);
                    sendForgetPassword();
                    sendForgetPassword();
                    return true;
                }
                return false;
            }
        });
        if (G.customerId != 0)
            txtSearchIsp.setVisibility(View.GONE);


        layBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegisterForm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        G.currentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void sendForgetPassword() {
        String username = edtUsernameForget.getText().toString().trim();
        String mobile = edtMobileNumberForget.getText().toString().trim();
        if (username.length() == 0) {
            DialogClass.showMessageDialog("خطا","نام کاربری را وارد کنید");
            return;
        } else if (mobile.length() == 0) {
            DialogClass.showMessageDialog("خطا","پسورد خود را وارد کنید");
            return;
        } else {
            WebService.sendRememberPassRequest(username, mobile, G.currentUser.ispUrl);
            dlgWaiting.DialogWaiting();
        }
    }

    private void sendLoginRequest() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (username.length() == 0) {
            DialogClass.showMessageDialog("خطا","نام کاربری را وارد کنید");

            return;
        } else if (password.length() == 0) {
            DialogClass.showMessageDialog("خطا","پسورد خود را وارد کنید");

            return;
        } else {
            if (!G.currentUser.ispUrl.equals("")) {
                WebService.sendLoginRequest(G.currentUser.ispUrl, ispId, username, password);
                dlgWaiting.DialogWaiting();
            } else {
                DialogClass dlgError = new DialogClass();
                dlgError.showMessageDialog("خطا", "عدم دریافت اطلاعات سرور به دلیل نداشتن اینترنت، لطفا اینترنت خود را فعال کنید و مجددا تلاش کنید.");
            }

        }
    }

    private void showLoginLayout() {
        layLogin.startAnimation(animFadeIn);
        layForgetPassword.startAnimation(animFadeOut);
        layLogin.setVisibility(View.VISIBLE);
        layForgetPassword.setVisibility(View.GONE);
        layLogin.bringToFront();

//        G.handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                layLogin.setVisibility(View.VISIBLE);
//                layForgetPassword.setVisibility(View.INVISIBLE);
//            }
//        }, 500);
//
//        ObjectAnimator rotateY = ObjectAnimator.ofFloat(layContent, "rotationY", 180f, 0.0f);
//        ObjectAnimator scaleDownXToSmall = ObjectAnimator.ofFloat(layContent, "scaleX", 0.85f);
//        ObjectAnimator scaleDownYToSmall = ObjectAnimator.ofFloat(layContent, "scaleY", 0.85f);
//        ObjectAnimator scaleDownXToBig = ObjectAnimator.ofFloat(layContent, "scaleX", 1f);
//        ObjectAnimator scaleDownYToBig = ObjectAnimator.ofFloat(layContent, "scaleY", 1f);
//        rotateY.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleDownYToSmall.setDuration(1000);
//
//
//        AnimatorSet firstAnim = new AnimatorSet();
//        firstAnim.play(scaleDownXToSmall).with(scaleDownYToSmall).before(rotateY);
//
//        AnimatorSet secondAnim = new AnimatorSet();
//        secondAnim.play(scaleDownXToBig).with(scaleDownYToBig).after(firstAnim);
//        secondAnim.start();
    }

    private void showForgetPasswordLayout() {

        Animation animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        layLogin.startAnimation(animFadeOut);
        layForgetPassword.startAnimation(animFadeIn);
        layLogin.setVisibility(View.GONE);
        layForgetPassword.setVisibility(View.VISIBLE);
        layForgetPassword.bringToFront();

//        G.handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                layLogin.setVisibility(View.INVISIBLE);
//                layForgetPassword.setVisibility(View.VISIBLE);
//            }
//        }, 500);
//        Animation animFadeIn = AnimationUtils.loadAnimation(G.context,R.anim.fade_in);
//
//
//        ObjectAnimator rotateY = ObjectAnimator.ofFloat(layContent, "rotationY", 0.0f, 180f);
//        ObjectAnimator scaleDownXToSmall = ObjectAnimator.ofFloat(layContent, "scaleX", 0.85f);
//        ObjectAnimator scaleDownYToSmall = ObjectAnimator.ofFloat(layContent, "scaleY", 0.85f);
//        ObjectAnimator scaleDownXToBig = ObjectAnimator.ofFloat(layContent, "scaleX", 1f);
//        ObjectAnimator scaleDownYToBig = ObjectAnimator.ofFloat(layContent, "scaleY", 1f);
//        rotateY.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleDownYToSmall.setDuration(1000);
//        AnimatorSet firstAnim = new AnimatorSet();
//        firstAnim.play(scaleDownXToSmall).with(scaleDownYToSmall).before(rotateY);
//
//        AnimatorSet secondAnim = new AnimatorSet();
//        secondAnim.play(scaleDownXToBig).with(scaleDownYToBig).after(firstAnim);
//        secondAnim.start();
    }

    public void onEventMainThread(EventOnGetIspUrlResponse event) {
        Logger.d("ActivityLogin : EventOnGetIspUrlResponse is raised.");
        /** Send get isp info */
        WebService.sendGetIspInfoLoginRequest(event.getIspUrl());
    }

    public void onEventMainThread(EventOnGetErrorIspUrl event) {
        Logger.d("ActivityLogin : EventOnGetErrorIspUrl is raised.");
        DialogClass dlgErrorMessage = new DialogClass();
        dlgErrorMessage.showMessageDialog("خطا", "در دریافت اطلاعات آی اس پی مورد نظر مشکلی به وجود آمده است");
    }

    public void onEventMainThread(EventOnGetIspInfoLoginResponse event) {
        Logger.d("ActivityLogin : EventOnGetIspInfo is raised.");
        ISPInfoLoginResponse ispInfo = event.getIspInfo();
//        Picasso.with(G.context)
//                .load(ispInfo.ImageURL)
//                .error(R.mipmap.ic_launcher)
//                .into(imgIspLogo);
        txtIspName.setText("" + ispInfo.Name);
//        if (ispInfo.OnlineReg != 0)
//            txtRegister.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(EventOnGetLoginResponse event) {
        Logger.d("ActivityLogin : EventOnGetLoginResponse is raised.");
        LoginResponse response = event.getLogin();
        if (response.Result == EnumResponse.OK) {
            /** chon User jadid dar hale login ast tamame User'haye ghabli ra az halate isLastLogin kharej mikonim.*/
            List<User> lastUsersLogin = new Select().from(User.class).where("isLastLogin = ?", true).execute();
            for (User user : lastUsersLogin) {
                user.isLastLogin = false;
                user.save();
            }
            G.currentUser.isLogin = true;
            G.currentUser.ispUrl = event.getIspUrl();
            G.currentUser.ispId = event.getIspId();
            G.currentUser.Token = response.Token;
            G.currentUser.isLastLogin = true;
            G.currentUser.save();

            /** after get login request get User license */
            WebService.sendGetUserLicenseRequest();
        } else {
            dlgWaiting.DialogWaitingClose();
            DialogClass dlg = new DialogClass();
            dlg.showMessageDialog("خطا", response.Message);
        }

        layBtnLogin.setClickable(true);
    }

    public void onEventMainThread(EventOnGetUserLicenseResponse event) {
        Logger.d("ActivityLogin : EventOnGetUserLicenseResponse is raised.");
        WebService.sendGetUserInfoRequest();
    }

    public void onEventMainThread(EventOnGetUserInfoResponse event) {
        Logger.d("ActivityLogin : EventOnGetUserInfoResponse is raised.");

        WebService.sendGetUserAccountInfoRequest();
    }

    public void onEventMainThread(EventOnGetUserAccountInfoResponse event) {
        Logger.d("ActivityLogin : EventOnGetUserAccountInfoResponse is raised.");
//        String s = new Gson().toJson(event.getAccountInfo());
        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);

        Account account = event.getAccountInfo();
        account.Avatar = "";
        String s = gsonBuilder.create().toJson(account);

        Account response = event.getAccountInfo();
        if (response.Result == EnumResponse.OK) {
            Intent intent = new Intent(context, ActivityShowCurrentService.class);
            intent.putExtra("JSON_ACCOUNT_INFO", s);
            startActivity(intent);
            finish();
//            dlgWaiting.DialogWaitingClose();

        } else {
            dlgWaiting.DialogWaitingClose();
            DialogClass.showMessageDialog("خطا","خطا در دریافت اطلاعات اکانت کاربری");
        }
    }

    public void onEventMainThread(EventOnRememberPasswordResponse event) {
        Logger.d("ActivityLogin : EventOnRememberPasswordResponse is raised.");
        dlgWaiting.DialogWaitingClose();
        if (event.getStatus() == EnumResponse.OK) {
            Logger.d("ActivityLogin : EventOnRememberPasswordResponse status is true");
            DialogClass dlg = new DialogClass();
            dlg.showMessageDialog("تایید", event.getMessage());
        } else {
            Logger.d("ActivityLogin : EventOnRememberPasswordResponse status is false");
            DialogClass.showMessageDialog("خطا",event.getMessage());

        }
    }

    public void onEventMainThread(EventOnGetErrorGetIspInfo event) {
        Logger.d("ActivityLogin : EventOnGetErrorGetIspInfo is raised.");
//        imgIspLogo.setImageResource(R.mipmap.ic_launcher);
        txtIspName.setText("" + U.getApplicationName());
    }

    public void onEventMainThread(EventOnGetErrorSendRememberPassword event) {
        Logger.d("ActivityLogin : EventOnGetErrorSendRememberPassword is raised.");
        dlgWaiting.DialogWaitingClose();
        DialogClass.showMessageDialog("خطا","خطا در دریافت اطلاعات از سرور لطفا مجددا تلاش کنید.");

    }

    public void onEventMainThread(EventOnGetErrorLogin event) {
        Logger.d("ActivityLogin : EventOnGetErrorLogin is raised.");
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnGetErrorGetChargeOnlineMainItems event) {
        Logger.d("ActivityLogin : EventOnGetErrorGetUserLicense is raised.");
        dlgWaiting.DialogWaitingClose();

    }

    public void onEventMainThread(EventOnGetErrorGetUserInfo event) {
        Logger.d("ActivityLogin : EventOnGetErrorGetUserInfo is raised.");

        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnGetErrorGetUserAccountInfo event) {
        Logger.d("ActivityLogin : EventOnGetErrorGetUserAccountInfo is raised.");
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityLogin : EventOnNoAccessServerResponse is raised.");
        dlgWaiting.DialogWaitingClose();
    }

    public void onEventMainThread(EventOnNetworkStatusChange event) {
        Logger.d("ActivityLogin : EventOnNetworkStatusChange is raised.");
        WebService.sendGetIspUrlRequest(ispId);
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        dlgWaiting.DialogWaitingClose();
    }

    /*hide keyboard when touch screen*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            if (getCurrentFocus().getWindowToken() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
        return true;
    }


}
