package ir.jahanmir.araxx.classes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Update;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.adapter.AdapterPollOptions;
import ir.jahanmir.araxx.adapter.AdapterSpinnerPoll;
import ir.jahanmir.araxx.component.CustomEditText;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.events.EventOnCanceledDialogUpdatingApplication;
import ir.jahanmir.araxx.events.EventOnCheckGetPollRequest;
import ir.jahanmir.araxx.events.EventOnClickedEndFeshfeshe;
import ir.jahanmir.araxx.events.EventOnClickedLogoutButton;
import ir.jahanmir.araxx.events.EventOnClickedYesOnYesNoDialog;
import ir.jahanmir.araxx.events.EventOnNotifyDeleted;
import ir.jahanmir.araxx.events.EventOnRegisterPoll;
import ir.jahanmir.araxx.events.EventOnShowDialogUpdatingApplicationRequest;
import ir.jahanmir.araxx.gson.FactorDetailResponse;
import ir.jahanmir.araxx.gson.GetIspInfoResponse;
import ir.jahanmir.araxx.gson.GetPollResponse;
import ir.jahanmir.araxx.model.ModelPollOption;
import ir.jahanmir.araxx.model.ModelYesNoDialog;
import ir.jahanmir.araxx.model.Notify;
import ir.jahanmir.araxx.model.getUpdate;

import static ir.jahanmir.araxx.G.context;

/**
 * Created by Microsoft on 3/9/2016.
 */
public class DialogClass {
    Dialog dialog;
    Dialog dialogPoll;
    Dialog dlgWaiting;
    Dialog dialogPayMessage;
    AdapterSpinnerPoll adapterSpinnerPoll;
    Dialog dlgWaitingWithBackground;
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;

    public DialogClass() {
    }

    public DialogClass(AppCompatActivity activity) {
        G.currentActivity = activity;
    }

    public void DialogWaiting() {
        dlgWaiting = new Dialog(G.currentActivity, android.R.style.Theme_Black_NoTitleBar);
        dlgWaiting.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dlgWaiting.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dlgWaiting.setContentView(R.layout.dialog_waiting);
        ImageView imgOne = (ImageView)dlgWaiting.findViewById(R.id.imgOne);
        ImageView imgTwo = (ImageView)dlgWaiting.findViewById(R.id.imgTwo);
        Animation animation = AnimationUtils.loadAnimation(G.currentActivity, R.anim.anim_rotate1 );
        imgOne.startAnimation(animation);
        dlgWaiting.setCancelable(false);
        dlgWaiting.show();
    }

    public void DialogWaitingClose() {
        if (dlgWaiting != null)
            dlgWaiting.dismiss();
    }

    public void showExitDialog() {

        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);

        txtBtnOk.setText("بله");
        txtBtnCancel.setText("خیر");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedLogoutButton());
                dialog.dismiss();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showMessageDialog(String title, String message) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_show_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtMessageTitle = (TextView) dialog.findViewById(R.id.txtMessageTitle);
        TextView txtMessageDescription = (TextView) dialog.findViewById(R.id.txtMessageDescription);
        txtMessageTitle.setText("" + title);
        txtMessageDescription.setText("" + message);
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showPointsMessageDialog(String title, String message) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_show_points_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtMessageTitle = (TextView) dialog.findViewById(R.id.txtMessageTitle);
        WebView webView = (WebView) dialog.findViewById(R.id.webView);
        String s = "<html>" +
                "<head>" +
                "<style>body{direction:rtl;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                message +
                "</body></html>";
        webView.loadData(s, "Text/html; charset=utf-8", "UTF-8");
        webView.setBackgroundColor(0x00000000);

        txtMessageTitle.setText("" + title);
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void showFactorDetail(FactorDetailResponse factor) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.l_factor_detail_item);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(U.getDeviceWidth() - 30, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView txtFactorDetailDes = (TextView) dialog.findViewById(R.id.txtFactorDetailDes);
        LinearLayout layDescription = (LinearLayout) dialog.findViewById(R.id.layDescription);
        TextView txtFactorDetailTitle = (TextView) dialog.findViewById(R.id.txtFactorDetailTitle);
        TextView txtFactorDetailPrice = (TextView) dialog.findViewById(R.id.txtFactorDetailPrice);

        String name = factor.Name.length() == 0 ? "-" : "" + factor.Name;
        txtFactorDetailTitle.setText(name);
        String price = factor.Price.length() == 0 ? "-" : "" + factor.Price + " " + G.currentUserInfo.unit;
        txtFactorDetailPrice.setText(price);
        if (factor.Des.length() != 0) {
            layDescription.setVisibility(View.VISIBLE);
            ////
            txtFactorDetailDes.setText("" + factor.Des);
        } else {
            layDescription.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void showCompanyDetailDialog(GetIspInfoResponse response) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_company_detail);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtCompanyUrl = (TextView) dialog.findViewById(R.id.txtCompanyUrl);
        TextView txtCompanyDetail = (TextView) dialog.findViewById(R.id.txtCompanyDetail);
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        txtCompanyUrl.setText("" + response.Hyperlink);
        txtCompanyDetail.setText("" + response.Contact);
        dialog.show();
    }

    public void showQuestionForEndFeshfesheDialog() {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_question);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);
        TextView txtDialogTitle = (TextView) dialog.findViewById(R.id.txtDialogTitle);
        TextView txtDialogDescription = (TextView) dialog.findViewById(R.id.txtDialogDescription);

        txtDialogTitle.setText("هشدار");
        txtDialogDescription.setText("آیا مطمئن هستید میخواهید فشفشه جاری را خاتمه دهید؟");

        txtBtnOk.setText("بله");
        txtBtnCancel.setText("خیر");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedEndFeshfeshe());
                dialog.dismiss();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void enableGps(String test) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_question);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);
        TextView txtDialogTitle = (TextView) dialog.findViewById(R.id.txtDialogTitle);
        TextView txtDialogDescription = (TextView) dialog.findViewById(R.id.txtDialogDescription);

        txtDialogTitle.setText("هشدار");
        txtDialogDescription.setText(test);

        txtBtnOk.setText("روشن کردن");
        txtBtnCancel.setText("انصراف");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gpsOptionsIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(gpsOptionsIntent);
                dialog.dismiss();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showUpdateApplicationDialog(final String newVersion, final boolean isForce, final String url) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_update_application);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnCancel = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        LinearLayout layBtnUpdate = (LinearLayout) dialog.findViewById(R.id.layBtnUpdate);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtCurrentVersion = (TextView) dialog.findViewById(R.id.txtCurrentVersion);
        TextView txtNewVersion = (TextView) dialog.findViewById(R.id.txtNewVersion);
        TextView txtLayBtnCancel = (TextView) layBtnCancel.findViewById(R.id.txtValue);
        TextView txtLayBtnUpdate = (TextView) layBtnUpdate.findViewById(R.id.txtValue);

        if (isForce) {
            txtLayBtnCancel.setText("خروج از برنامه");
        } else {
            txtLayBtnCancel.setText("انصراف");
        }
        txtLayBtnUpdate.setText("دانلود");
        txtCurrentVersion.setText("نسخه فعلی : " + U.getAppVersionName());
        txtNewVersion.setText("نسخه جدید : " + newVersion);
        if (isForce) {
//            layBtnCancel.setVisibility(View.GONE);
            imgCloseDialog.setVisibility(View.GONE);
        }
        layBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnShowDialogUpdatingApplicationRequest(newVersion, isForce, url));
                dialog.dismiss();
            }
        });
        layBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isForce) {
                    G.currentActivity.finish();
                    System.exit(0);
                } else {
                    dialog.dismiss();
                    EventBus.getDefault().post(new EventOnCheckGetPollRequest());
                }
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                EventBus.getDefault().post(new EventOnCheckGetPollRequest());
            }
        });
        dialog.show();
    }

    public void showUpdatingApplicationDialog(final String newVersion, final boolean isForce, final String url) {
        dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_updating_application);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnCancelCurrentUpdate = (LinearLayout) dialog.findViewById(R.id.layBtnCancelCurrentUpdate);
        LinearLayout layBtnInstallUpdate = (LinearLayout) dialog.findViewById(R.id.layBtnInstallUpdate);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtLayBtnInstallUpdate = (TextView) layBtnInstallUpdate.findViewById(R.id.txtValue);
        TextView txtLayBtnCancelCurrentUpdate = (TextView) layBtnCancelCurrentUpdate.findViewById(R.id.txtValue);

        txtLayBtnInstallUpdate.setText("نصب");
        txtLayBtnCancelCurrentUpdate.setText("انصراف");
        layBtnInstallUpdate.setVisibility(View.GONE);

        layBtnCancelCurrentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnInstallUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                U.installUpdateApp();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                EventBus.getDefault().post(new EventOnCanceledDialogUpdatingApplication(newVersion, isForce, url));
            }
        });
        dialog.show();
    }

    public void changeProgressPercent(float percent) {
        if (dialog != null) {
            ((ProgressWheel) (dialog.findViewById(R.id.prgDownloadUpdatePercent))).setProgress(percent);
            ((TextView) (dialog.findViewById(R.id.txtLoading))).setText((int) (percent * 100) + "%");
        }
    }

    public void showInstallButton() {
        if (dialog != null) {
            ((LinearLayout) (dialog.findViewById(R.id.layBtnInstallUpdate))).setVisibility(View.VISIBLE);
            ((TextView) (dialog.findViewById(R.id.txtDialogDescription))).setText("دانلود با موفقیت انجام شد، حال میتوانید اقدام به نصب نسخه جدید کنید.");
            ((TextView) (dialog.findViewById(R.id.txtDialogTitle))).setText("نصب نسخه جدید");
            U.collapse(((LinearLayout) (dialog.findViewById(R.id.layLoading))));
        }
    }

    public void showPollDialog(final GetPollResponse poll) {
        String OptionTexts = poll.getOptionText();
        String id = poll.getOptionID();
        String[] tt = OptionTexts.split("`");
        String[] ii = id.split("`");
        List<ModelPollOption> modelPolls = new ArrayList<>();

        for (int j = 0; j < tt.length; j++) {
            ModelPollOption modelPoll = new ModelPollOption();
            modelPoll.setOptionID(ii[j]);
            modelPoll.setOptionText(tt[j]);
            modelPoll.setPollCode(String.valueOf(poll.getCode()));
            modelPolls.add(modelPoll);
        }

        boolean optionalKind = false;

        dialogPoll = new Dialog(G.currentActivity, R.style.DialogSlideAnimAppCompact);
        dialogPoll.setContentView(R.layout.dialog_show_poll);
        dialogPoll.setCancelable(false);
        dialogPoll.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogPoll.getWindow().setGravity(Gravity.CENTER);
        dialogPoll.getWindow().setLayout(U.getDeviceWidth() - 30, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogPoll.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        PersianTextViewNormal txtQuestion = (PersianTextViewNormal) dialogPoll.findViewById(R.id.txtQuestion);
        final CustomEditText edtComment = (CustomEditText) dialogPoll.findViewById(R.id.edtComment);
        RecyclerView rcyOptions = (RecyclerView) dialogPoll.findViewById(R.id.rcyOptions);
        PersianTextViewNormal txtDone = (PersianTextViewNormal) dialogPoll.findViewById(R.id.txtDone);
        final PersianTextViewNormal txtError = (PersianTextViewNormal) dialogPoll.findViewById(R.id.txtError);


        txtQuestion.setText(poll.getQuestion());
        //agar nazar sanji text bashad
        if (poll.getOptionID() == null || poll.getOptionID().equals("")) {
            edtComment.setVisibility(View.VISIBLE);
            optionalKind = false;
            rcyOptions.setVisibility(View.GONE);
        } else {
            optionalKind = true;
            rcyOptions.setVisibility(View.VISIBLE);
            edtComment.setVisibility(View.GONE);
        }


        final AdapterPollOptions adapterPollOptions = new AdapterPollOptions(modelPolls);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        rcyOptions.setHasFixedSize(true);
        rcyOptions.setLayoutManager(layoutManager);
        rcyOptions.setAdapter(adapterPollOptions);


        final boolean finalOptionalKind = optionalKind;
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String optionId = adapterPollOptions.getSelectedOptionId();
                String coment = edtComment.getText().toString();
                txtError.setText("");
                if (finalOptionalKind && optionId.equals("0")) {
                    txtError.setText("لطفا یک گزینه را انتخاب کنید");
                } else if (!finalOptionalKind && coment.equals("")) {
                    txtError.setText("لطفا نظر سنجی را تکمیل کنید");
                } else {
                    if (coment == null) coment = "";
                    txtError.setText("");
                    EventBus.getDefault().post(new EventOnRegisterPoll(String.valueOf(poll.getCode()), optionId, coment));
                }
            }
        });


        dialogPoll.show();
    }

    public void cancelPollDialog() {
        if (dialogPoll != null) {
            dialogPoll.dismiss();
        }
    }


    public void DialogWaitingWithBackground(Context context) {
        dlgWaitingWithBackground = new Dialog(context, android.R.style.Theme_Black_NoTitleBar);
        dlgWaitingWithBackground.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dlgWaitingWithBackground.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dlgWaitingWithBackground.setContentView(R.layout.dialog_waiting_with_background);
        dlgWaitingWithBackground.setCancelable(false);
        dlgWaitingWithBackground.show();
    }

    public void cancelDialogWaitingWithBackground() {
        if (dlgWaitingWithBackground != null)
            dlgWaitingWithBackground.dismiss();
    }

    public void deleteNotify(final long notifyCode) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);

        txtBtnOk.setText("حذف");
        txtBtnCancel.setText("انصراف");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new Update(Notify.class)
                            .set("IsSeen = 1")
                            .execute();
                    EventBus.getDefault().post(new EventOnNotifyDeleted());
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showUpdateDialog(final getUpdate getUpdate) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_update);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        PersianTextViewNormal txtUpdate = (PersianTextViewNormal) dialog.findViewById(R.id.txtUpdate);
        PersianTextViewNormal txtClose = (PersianTextViewNormal) dialog.findViewById(R.id.txtClose);
        PersianTextViewNormal txtVer = (PersianTextViewNormal) dialog.findViewById(R.id.txtVer);
        WebView web = (WebView) dialog.findViewById(R.id.web);

        String s = "<html>" +
                "<head>" +
                "<style>body{direction:rtl;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                getUpdate.getDes() +
                "</body></html>";
        web.loadData(s, "Text/html; charset=utf-8", "UTF-8");
        web.setBackgroundColor(0x00000000);

        String Ver = " نسخه فعلی : " + G.versionName + "\n نسخه جدید : " + getUpdate.getVer() + "\n";
        txtVer.setText(Ver);

        if (getUpdate.isForce()) {
            txtClose.setVisibility(View.GONE);
            dialog.setCanceledOnTouchOutside(false);
        }
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getUpdate.getUrl()));
                    context.startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showYesNoDialog(final ModelYesNoDialog modelYesNoDialog) {

        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        PersianTextViewNormal txtTitle = (PersianTextViewNormal) dialog.findViewById(R.id.txtTitle);
        PersianTextViewNormal txtMessage = (PersianTextViewNormal) dialog.findViewById(R.id.txtMessage);

        txtMessage.setText(modelYesNoDialog.getBody());
        txtTitle.setText(modelYesNoDialog.getTitle());

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);

        txtBtnOk.setText("بله");
        txtBtnCancel.setText("خیر");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedYesOnYesNoDialog(modelYesNoDialog.getYesNoKind(), modelYesNoDialog.getData()));
                dialog.dismiss();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

