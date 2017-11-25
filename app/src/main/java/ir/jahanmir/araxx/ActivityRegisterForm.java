package ir.jahanmir.araxx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.adapter.AdapterSpinnerCity;
import ir.jahanmir.araxx.adapter.AdapterSpinnerRegions;
import ir.jahanmir.araxx.adapter.AdapterSpinnerServiceKind;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.CustomEditText;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumResponse;
import ir.jahanmir.araxx.enums.EnumServiceKind;
import ir.jahanmir.araxx.events.EventOnSelectServiceKindSpinner;
import ir.jahanmir.araxx.events.EventOnSuccessGetCities;
import ir.jahanmir.araxx.events.EventOnSuccessGetRegions;
import ir.jahanmir.araxx.events.EventOnSuccessRegisterCustomer;
import ir.jahanmir.araxx.model.ModelCities;
import ir.jahanmir.araxx.model.ModelRegions;
import ir.jahanmir.araxx.model.ModelRegisterCustomer;
import ir.jahanmir.araxx.model.ModelServiceKind;

import static ir.jahanmir.araxx.enums.EnumServiceKind.asdl;


public class ActivityRegisterForm extends AppCompatActivity {

    @Bind(R.id.spnCities)
    Spinner spnCities;
    @Bind(R.id.spnRegions)
    Spinner spnRegions;
    @Bind(R.id.snpService)
    Spinner snpService;

    @Bind(R.id.layCheckPhoneNumber)
    LinearLayout layCheckPhoneNumber;

    @Bind(R.id.edtFamilyName)
    CustomEditText edtFamilyName;
    @Bind(R.id.edtName)
    CustomEditText edtName;
    @Bind(R.id.edtMobileNumber)
    CustomEditText edtMobileNumber;
    @Bind(R.id.edtAddress)
    CustomEditText edtAddress;

    @Bind(R.id.layInfoForm)
    LinearLayout layInfoForm;
    @Bind(R.id.layBtnDone)
    LinearLayout layBtnDone;
    @Bind(R.id.edtPrePhoneNumber)
    PersianTextViewNormal edtPrePhoneNumber;
    @Bind(R.id.edtTelNumber)
    CustomEditText edtTelNumber;
    @Bind(R.id.edtRanzheNumber)
    CustomEditText edtRanzheNumber;

    @Bind(R.id.edtPreTelNumber)
    PersianTextViewNormal edtPreTelNumber;

    @Bind(R.id.layBtnClose)
    FrameLayout layBtnClose;

    @Bind(R.id.layToolbarMain)
    LinearLayout layToolbarMain;


    @Bind(R.id.imgIcon)
    ImageView imgIcon;

    @Bind(R.id.txtName)
    PersianTextViewThin txtName;

    AdapterSpinnerCity adapterSpinnerCity;
    AdapterSpinnerRegions adapterspinnerRegions;

    List<ModelRegions> regionsList = new ArrayList<>();
    List<ModelCities> citiesList = new ArrayList<>();

    int cityCode = 0;
    int PrecityCode = 0;
    String ranzheNumber = "0";
    String name = "";
    String family = "";
    String mobileNumber = "";
    String telNumber = "";
    String address = "";
    int serviceKind = 0;

    DialogClass dlgWaiting;

    ModelRegisterCustomer modelRegisterCustomer;

    int EserviceKind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        ButterKnife.bind(this);
        initView();
        WebService.sendGetRegions();
        initToolbar();
        dlgWaiting = new DialogClass(this);
        dlgWaiting.DialogWaiting();
    }

    private void initToolbar() {
        int width = U.getDeviceWidth() / 4;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layToolbarMain.setLayoutParams(layoutParams);
        layToolbarMain.setBackgroundColor(getResources().getColor(R.color.color_charg_online));
        imgIcon.setImageResource(R.mipmap.ic_register);
        txtName.setText("ثبت نام");

    }


    private void initView() {
        layInfoForm.setVisibility(View.GONE);
        layBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData();
                if (checkCondition()) {
                    dlgWaiting.DialogWaiting();
                    WebService.sendRegisterCustomerRequest(modelRegisterCustomer);
                }
            }
        });
        initSpinners();
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void prepareData() {
        try {
            name = edtName.getText().toString();
            family = edtFamilyName.getText().toString();
            address = edtAddress.getText().toString();
            mobileNumber = edtMobileNumber.getText().toString();
            telNumber = edtTelNumber.getText().toString();
            ranzheNumber = edtRanzheNumber.getText().toString();

            modelRegisterCustomer = new ModelRegisterCustomer();
            modelRegisterCustomer.setName(name);
            modelRegisterCustomer.setFamily(family);
            modelRegisterCustomer.setPhoneRanzhe(ranzheNumber);
            modelRegisterCustomer.setPhone(telNumber);
            modelRegisterCustomer.setMobile(mobileNumber);
            modelRegisterCustomer.setAddress(address);
            modelRegisterCustomer.setCustomerType(EserviceKind);
            modelRegisterCustomer.setCity(cityCode);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean checkCondition() {
        if (cityCode == 0) {
            DialogClass.showMessageDialog("خطا", "لطفا شهر را انتخاب کنید");
            return false;
        }

        if (EserviceKind == EnumServiceKind.unselected) {
            DialogClass.showMessageDialog("خطا", "لطفا نوع سرویس را مشخص کنید ");
            return false;
        }

        if (EserviceKind == asdl) {
            if (ranzheNumber.equals("") || ranzheNumber.length() != 8) {
                DialogClass.showMessageDialog("خطا", "شماره تلفنی که برای رانژه وارد شده معتبر نمیباشد");
                return false;
            }
        }
        if (!telNumber.equals("") && telNumber.length() != 8) {
            DialogClass.showMessageDialog("خطا", "شماره تلفن وارد شده معتبر نمیباشد");
        }
        if (name.equals("")) {
            DialogClass.showMessageDialog("خطا", "لطفا نام خود را وارد کنید");
            return false;
        }
        if (family.equals("")) {
            DialogClass.showMessageDialog("خطا", "لطفا نام خانوادگی خود را وارد کنید");
            return false;
        }

        if (mobileNumber.equals("")) {
            DialogClass.showMessageDialog("خطا", "لطفا شماره موبایل خود را وارد کنید");
            return false;
        } else if (mobileNumber.length() != 10) {
            DialogClass.showMessageDialog("خطا", " شماره موبایل وارد شده صحیح نمیباشد");
            return false;
        }

        if (address.equals("")) {
            DialogClass.showMessageDialog("خطا", "لطفا آدرس خور را وارد کنید");
            return false;
        }


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.context = this;
        G.currentActivity = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initSpinners() {
        ModelRegions unitlRegio = new ModelRegions();
        unitlRegio.setName("در حال دریافت استان ها");
        regionsList.add(0, unitlRegio);
        adapterspinnerRegions = new AdapterSpinnerRegions(regionsList);
        spnRegions.setAdapter(adapterspinnerRegions);

        spnRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    layInfoForm.setVisibility(View.GONE);
                    edtRanzheNumber.setText("");
//لیست شهر ها را خالی میکنیک
                    citiesList.clear();
                    ModelCities modelCities = new ModelCities();

                    if (position == 0) {
                        modelCities.setName("انتخاب شهر");
                    } else {
                        modelCities.setName("در حال دریافت شهرها");
                    }
                    citiesList.add(0, modelCities);
                    adapterSpinnerCity = new AdapterSpinnerCity(citiesList);
                    spnCities.setAdapter(adapterSpinnerCity);

                    if (position != 0) {
                        WebService.sendGetCities(adapterspinnerRegions.getItem(position).getCode());
                        PrecityCode = adapterspinnerRegions.getItem(position).getPreTel();
                        edtPrePhoneNumber.setText("0" + PrecityCode);
                        edtPreTelNumber.setText("0" + PrecityCode);
                        dlgWaiting.DialogWaiting();

                        Logger.d("ActivityRegisterForm : region id is " + (adapterspinnerRegions.getItem(spnRegions.getSelectedItemPosition())).getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                layInfoForm.setVisibility(View.GONE);
            }
        });


        ModelCities modelCities = new ModelCities();
        modelCities.setName("انتخاب شهر");
        citiesList.add(0, modelCities);
        adapterSpinnerCity = new AdapterSpinnerCity(citiesList);
        spnCities.setAdapter(adapterSpinnerCity);
        spnCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                layInfoForm.setVisibility(View.VISIBLE);
                edtRanzheNumber.setText("");

                if (position != 0) {
                    cityCode = adapterSpinnerCity.getItem(position).getCode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<ModelServiceKind> modelServiceKinds = new ArrayList<>();
        modelServiceKinds.add(new ModelServiceKind("نوع سرویس", EnumServiceKind.unselected));
        modelServiceKinds.add(new ModelServiceKind("WireLess", EnumServiceKind.wireless));
        modelServiceKinds.add(new ModelServiceKind("ADSL", asdl));
        modelServiceKinds.add(new ModelServiceKind("TD-LTE", EnumServiceKind.lte));
        final AdapterSpinnerServiceKind adapterSpinnerServiceKind = new AdapterSpinnerServiceKind(modelServiceKinds);
        snpService.setAdapter(adapterSpinnerServiceKind);
        snpService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new EventOnSelectServiceKindSpinner(adapterSpinnerServiceKind.getItem(position).getServiceKind()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onEventMainThread(EventOnSuccessGetRegions regions) {
        regionsList.clear();
        dlgWaiting.DialogWaitingClose();
        List<ModelRegions> modelRegionses = regions.getModelRegionsList();
        for (int i = 0; i < modelRegionses.size() + 1; i++) {
            if (i == 0) {
                ModelRegions modelRegions = new ModelRegions();
                modelRegions.setName("انتخاب استان");
                regionsList.add(0, modelRegions);
            } else {
                regionsList.add(modelRegionses.get(i - 1));
            }
        }
        adapterspinnerRegions.updateList(regionsList);
    }

    public void onEventMainThread(EventOnSuccessGetCities getCities) {
        citiesList.clear();
        dlgWaiting.DialogWaitingClose();
        for (int i = 0; i < getCities.getCitiesList().size() + 1; i++) {
            if (i == 0) {
                ModelCities modelCities = new ModelCities();
                modelCities.setName("انتخاب شهر");
                citiesList.add(0, modelCities);
            } else {
                citiesList.add(getCities.getCitiesList().get(i - 1));
            }
        }
        adapterSpinnerCity.updateList(citiesList);
    }

    public void onEventMainThread(EventOnSelectServiceKindSpinner spinner) {
        //گر adsl انتخاب شد، اول باید شماره تلفن را اعتبار سنجی کنید
        dlgWaiting.DialogWaitingClose();

        EserviceKind = spinner.getServiceKind();
        if (spinner.getServiceKind() == asdl) {
            layCheckPhoneNumber.setVisibility(View.VISIBLE);
            layInfoForm.setVisibility(View.VISIBLE);
        } else {
            layInfoForm.setVisibility(View.VISIBLE);
            layCheckPhoneNumber.setVisibility(View.INVISIBLE);
        }
    }

    public void onEventMainThread(EventOnSuccessRegisterCustomer customer) {
        dlgWaiting.DialogWaitingClose();

        if (customer.getCustomerResponse().getResult() == EnumResponse.OK) {
            String s = "لطفا نام کاربری و کلمه عبور خود را یادداشت کنید  " + "\n" +
                    "نام کاربری : " + customer.getCustomerResponse().getUserName() + "\n" +
                    "کلمه عبور : " + customer.getCustomerResponse().getPassword() + "\n" +
                    "جهت ادامه ثبت نام به پنل کاربری خود وارد شوید";
            DialogClass.showMessageDialog("ثبت نام شما موفق بود", s);

        } else {
            DialogClass.showMessageDialog("خطا", customer.getCustomerResponse().getMessage());

        }


    }
}
