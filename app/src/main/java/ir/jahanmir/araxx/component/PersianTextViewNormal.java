package ir.jahanmir.araxx.component;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public  class PersianTextViewNormal extends TextView {
    FontHelper fontHelper = new FontHelper();
    public PersianTextViewNormal(Context context) {
        super(context);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/fontnormal.ttf").getPersianTextTypeface());
    }
    public PersianTextViewNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/fontnormal.ttf").getPersianTextTypeface());
    }
    public PersianTextViewNormal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/fontnormal.ttf").getPersianTextTypeface());
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null)
            text = FormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}
