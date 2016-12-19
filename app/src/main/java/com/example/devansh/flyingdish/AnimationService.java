package com.example.devansh.flyingdish;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;

public class AnimationService extends Service {


    private WindowManager windowManager;



    ImageView firstanim , secondanim;

    private ViewGroup mPopupLayout;

    private  ViewGroup mParentView;

    boolean canProceed = false;

    Context context;
    public AnimationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    /** Called when the service is being created. */
    @Override
    public void onCreate() {

        context = this;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(Settings.canDrawOverlays(context)) {
                canProceed = true;
            }
            else
                canProceed = false;
        }
        else
            canProceed = true;


        if(canProceed){
            windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

            //        chatHead = new ImageView(context);
            //         chatHead.setImageResource(R.mipmap.ic_launcher);

//            View view  = LayoutInflater.from(context).inflate(R.layout.show_anim,null,false);
//            view.setAlpha(0.3F);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                    PixelFormat.TRANSLUCENT);


//            params.width = ActionBar.LayoutParams.WRAP_CONTENT;
//            params.height = ActionBar.LayoutParams.WRAP_CONTENT;

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mPopupLayout = (RelativeLayout) inflater.inflate(R.layout.show_anim, null);
            //firstanim = new ImageView(context);
            firstanim = (ImageView) mPopupLayout.findViewById(R.id.FirstAnimImage);
            secondanim = (ImageView) mPopupLayout.findViewById(R.id.FirstAnimImage2);
            //mPopupLayout.setVisibility(View.GONE);

            mParentView = new FrameLayout(this);


            mParentView.addView(mPopupLayout);
//            mPopupLayout.setVisibility(View.GONE);


//            params.gravity = Gravity.TOP | Gravity.LEFT;
//            params.x = 0;
//            params.y = 100;


            windowManager.addView(mParentView, params);


            Animation a = AnimationUtils.loadAnimation(this, R.anim.scale_image);
            firstanim.startAnimation(a);

            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    firstanim.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher_resized));
                    Animation b = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);

                    firstanim.startAnimation(b);

                    b.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            Animation c = AnimationUtils.loadAnimation(context, R.anim.scale_down);
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
                                           windowManager.removeView(mParentView);
                                            stopSelf();
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

//            Intent i = new Intent(context , ShowAnimation.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

        }
        else {
            Toast.makeText(context , "Give app the permission" , Toast.LENGTH_SHORT).show();
        }




    }





    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }


    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

        super.onDestroy();



        //stopForeground(false);

    }


}
