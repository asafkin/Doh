package kinamonistudio.doh.views;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import kinamonistudio.doh.R;
import kinamonistudio.doh.Utils;

/**
 * Created by Asaf on 22/04/2016.
 */
public class OverlayView extends RelativeLayout {

    public OverlayView(Context context) {
        super(context);
        init();
    }

    public OverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OverlayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (getLayoutTransition() != null) {
            return;
        }

        LayoutTransition transition = new LayoutTransition();
        Animator rippleAnimation = AnimatorInflater.loadAnimator(getContext(), R.animator.ripple_effect);
        transition.setAnimator(LayoutTransition.APPEARING, rippleAnimation);
        transition.setStartDelay(LayoutTransition.APPEARING, 0);
        transition.setAnimator(LayoutTransition.DISAPPEARING, null);
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, null);
        rippleAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                final View view = (View) ((ObjectAnimator) ((AnimatorSet) animator).getChildAnimations().get(0)).getTarget();
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                final View view = (View) ((ObjectAnimator) ((AnimatorSet) animator).getChildAnimations().get(0)).getTarget();
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        removeView(view);
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                final View view = (View) ((ObjectAnimator) ((AnimatorSet) animator).getChildAnimations().get(0)).getTarget();
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        removeView(view);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        setLayoutTransition(transition);

        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                showRippleEffect(e.getX(), e.getY(), Utils.dpToPx(getContext(), 100));
            }

            public boolean onDown(MotionEvent e) {
                showRippleEffect(e.getX(), e.getY(), Utils.dpToPx(getContext(), 100));
                return false;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // delete below line if you want transparent back color, but to understand the sizes use back color
        //canvas.drawColor(Color.BLACK);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
        // Toast.makeText(getContext(),"onTouchEvent", Toast.LENGTH_LONG).show();

        //return true;
    }

    private void showRippleEffect(float x, float y, int size) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);

        final View rippleView = new View(getContext());
        rippleView.setX(x - size/2);
        rippleView.setY(y - size/2);
        rippleView.setBackgroundResource(R.drawable.ripple_background);
        rippleView.setVisibility(View.GONE);
        addView(rippleView, params);
    }
}
