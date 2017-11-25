package ir.jahanmir.araxx.component;


import android.content.Context;
import android.graphics.Typeface;

public class FontHelper {
    private FontHelper instance;
    private Typeface persianTypeface;

    public FontHelper() {

    }
    private FontHelper(Context context, String font) {
        persianTypeface = Typeface.createFromAsset(context.getAssets(), font);
    }
    public  synchronized FontHelper getInstance(Context context, String font) {
        if (instance == null)
            instance = new FontHelper(context,font);
        return instance;
    }
    public Typeface getPersianTextTypeface() {
        return persianTypeface;
    }
}
