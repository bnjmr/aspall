package ir.jahanmir.aspa.gson;

import android.os.Parcel;
import android.os.Parcelable;

public class FactorDetailResponse implements Parcelable {
    public String Name = "";
    public String Des = "";
    public String Price = "";


    public FactorDetailResponse(String name, String des, String price) {
        Name = name;
        Des = des;
        Price = price;
    }

    protected FactorDetailResponse(Parcel in) {
        Name = in.readString();
        Des = in.readString();
        Price = in.readString();
    }

    public static final Creator<FactorDetailResponse> CREATOR = new Creator<FactorDetailResponse>() {
        @Override
        public FactorDetailResponse createFromParcel(Parcel in) {
            return new FactorDetailResponse(in);
        }

        @Override
        public FactorDetailResponse[] newArray(int size) {
            return new FactorDetailResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Des);
        parcel.writeString(Price);
    }
}
