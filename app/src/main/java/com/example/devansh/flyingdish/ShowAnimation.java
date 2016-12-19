package com.example.devansh.flyingdish;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ShowAnimation extends Activity {

    ImageView firstanim , secondanim;
    private ViewGroup mPopupLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_anim);

        mPopupLayout = (ViewGroup) findViewById(R.id.mainCont);

        firstanim = (ImageView) findViewById(R.id.FirstAnimImage);
        secondanim = (ImageView) findViewById(R.id.FirstAnimImage2);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale_image);
        firstanim.startAnimation(a);

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                firstanim.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_resized));
                Animation b = AnimationUtils.loadAnimation(ShowAnimation.this, R.anim.rotate_anim);

                firstanim.startAnimation(b);

                b.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        Animation c = AnimationUtils.loadAnimation(ShowAnimation.this, R.anim.scale_down);
                        firstanim.startAnimation(c);
                        c.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                firstanim.setVisibility(View.GONE);
                                secondanim.setVisibility(View.VISIBLE);
                                ValueAnimator animator = ValueAnimator.ofFloat(0, 1); // values from 0 to 1
                                animator.setDuration(2000); // 5 seconds duration from 0 to 1
                                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float value = ((Float) (animation.getAnimatedValue()))
                                                .floatValue();
                                        // Set translation of your view here. Position can be calculated
                                        // out of value. This code should move the view in a half circle.
                                        secondanim.setTranslationX((float) (150.0 * Math.sin(value * Math.PI)));
                                        secondanim.setTranslationY((float) (150.0 * Math.cos(value * Math.PI)));
                                    }
                                });
                                animator.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                                animator.start();
                                secondanim.setScaleType(ImageView.ScaleType.CENTER);
                                secondanim.animate().scaleX(2.5f).scaleY(2.5f).setDuration(2000).start();
                                secondanim.setAlpha(1.0f);
                                mPopupLayout.setBackgroundColor(getResources().getColor(R.color.light_black));
//                                mPopupLayout.setAlpha(0.5f);


                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }
}
