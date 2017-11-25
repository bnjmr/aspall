package ir.jahanmir.araxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivitySplash extends AppCompatActivity {

    @Bind(R.id.imgSlideL2R)
    ImageView imgSlideL2R;
    @Bind(R.id.imgSlidR2L)
    ImageView imgSlidR2L;
    @Bind(R.id.imgSlideL2RBig)
    ImageView imgSlideL2RBig;
    @Bind(R.id.imgSlidR2LBig)
    ImageView imgSlidR2LBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation R2L = AnimationUtils.loadAnimation(this, R.anim.anim_splash_r2l);
        imgSlidR2LBig.startAnimation(R2L);

        Animation R2L2 = AnimationUtils.loadAnimation(this, R.anim.anim_splash_r2l_2);
        imgSlideL2R.startAnimation(R2L2);

        Animation L2R = AnimationUtils.loadAnimation(this, R.anim.anim_solash_l2r);
        imgSlideL2RBig.startAnimation(L2R);

        Animation L2R2 = AnimationUtils.loadAnimation(this, R.anim.anim_splash_l2r_2);
        imgSlidR2L.startAnimation(L2R2);
        L2R2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(ActivitySplash.this, ActivityStarter.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

}
