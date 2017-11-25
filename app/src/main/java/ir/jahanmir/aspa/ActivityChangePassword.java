package ir.jahanmir.aspa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.U;
import ir.jahanmir.aspa.classes.WebService;
import ir.jahanmir.aspa.component.CustomEditText;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.enums.EnumResponse;
import ir.jahanmir.aspa.events.EventOnChangePasswordResponse;


public class ActivityChangePassword extends AppCompatActivity {

    @Bind(R.id.edtOldPass)
    CustomEditText edtOldPass;
    @Bind(R.id.edtNewPassword)
    CustomEditText edtNewPassword;
    @Bind(R.id.edtConfirmNewPassword)
    CustomEditText edtConfirmNewPassword;
    @Bind(R.id.layBtnDone)
    LinearLayout layBtnDone;

    @Bind(R.id.layLoading)
    LinearLayout layLoading;

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;


    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    DialogClass dialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aativity_change_password);
        ButterKnife.bind(this);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_chanepass));
        imgIcon.setImageResource(R.drawable.ic_changepassword);
        txtName.setText("تغییر رمز");

    }

    private void initView() {
        layLoading.setVisibility(View.GONE);
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialogClass = new DialogClass();
        layBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = edtOldPass.getText().toString();
                String newPass = edtNewPassword.getText().toString();
                String confirmNewPass = edtConfirmNewPassword.getText().toString();

                if (oldpass.equals("")) {
                    dialogClass.showMessageDialog("خطا", "لطفا رمز عبور فعلی خود را وارد کنید .");

                } else if (newPass.equals("")) {
                    dialogClass.showMessageDialog("خطا", "لطفا رمز عبور جدید خود را وارد کنید .");

                } else if (!newPass.equals(confirmNewPass)) {
                    dialogClass.showMessageDialog("خطا", "رمز عبور جدید با تکرار آن مطابقت ندارد. لطفا رمز عبور جدید را مجددا وارد نمایید.");

                } else {
                    WebService.sendChangePasswordRequest(oldpass, newPass);
                    layLoading.setVisibility(View.VISIBLE);
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(final EventOnChangePasswordResponse passwordResponse) {


        if (passwordResponse.getStatus()== EnumResponse.OK){
            layLoading.setVisibility(View.GONE);
            Intent intent = new Intent(this,ActivityLogin.class);
            startActivity(intent);
            finish();
            edtOldPass.setText("");
            edtNewPassword.setText("");
            edtConfirmNewPassword.setText("");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogClass.showMessageDialog("", passwordResponse.getMessage()+"\n لطفا دوباره وارد شوید");
                }
            },700);
        }else {
            dialogClass.showMessageDialog("", passwordResponse.getMessage());

        }

    }
}
