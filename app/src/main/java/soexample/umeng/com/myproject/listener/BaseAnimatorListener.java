package soexample.umeng.com.myproject.listener;

import android.animation.Animator;

public  abstract class BaseAnimatorListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public abstract void onAnimationEnd(Animator animator);

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

}
