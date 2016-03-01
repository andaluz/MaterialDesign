package app.appplanet.com.mytoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * First you have to keep in mind that I have a project with multiple fragments
 * that have the same Toolbar with a fixed background. But for ONE fragment, I
 * want the background transparent so it shows the background of that one fragment.
 *
 * My solution so far:
 * To get a transparent Toolbar, I first hide the toolbar in the MainActivity.
 * Than create a custom toolbar with a transparent background and add a listener
 * on navigation click of the toolbar, which seems to be a built in thing.
 *
 * This is the only solution I found so far that works for me without a lot
 * of coding, xml layout/style editing.
 *
 * What I don't like about this, is I have to hide the other toolbar declared
 * in MainActivity and add a custom one just for this fragment.
 * I rather use the Toolbar in the MainActivity, but making that Toolbar transparent,
 * doesn't show the underlying background of this fragment.
 * If you have a solution to make the Toolbar of the MainActivity transparent, you're
 * welcome to share it with me.
 */
public class TransparentFragment extends Fragment {
    private final static String TAG = "TransparentFragment";

    private int counter = 0;
    private Runnable counterRunnable;
    TextView tvCounter;

    @Nullable
    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transparent_toolbar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button btn = (Button) getView().findViewById(R.id.btn_animate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAnimation();
            }
        });
        modifyToolbar();

        Button yoda = (Button) getView().findViewById(R.id.btn_yoda);
        yoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playDate();
            }
        });
    }

    private void modifyToolbar() {
        Toolbar toolbar = (Toolbar)((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        final DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        Toolbar transparent = (Toolbar) getView().findViewById(R.id.tb_transparent);
        transparent.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        transparent.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer != null) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void playAnimation() {
        tvCounter = (TextView) getView().findViewById(R.id.tv_animation);
        YoYo.with(Techniques.BounceIn)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        TextView tv2 = (TextView) getView().findViewById(R.id.tv_anim2);
                        YoYo.with(Techniques.BounceIn)
                                .duration(1000)
                                .playOn(tv2);

                        counterRunnable = getTextViewRunnable(1000);
                        tvCounter.postDelayed(counterRunnable, 1000);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(tvCounter);
    }

    private Runnable getTextViewRunnable(final int time) {
        return new Runnable() {
            @Override
            public void run() {
                if(tvCounter!=null) {
                    tvCounter.setText(String.format("Count %d", counter++));
                    counterRunnable = getTextViewRunnable(time);
                    tvCounter.postDelayed(counterRunnable, time);
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();

        Toolbar toolbar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        //cleanUpResources();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cleanUpResources();
    }

    private void cleanUpResources() {
        if (counterRunnable != null)
            tvCounter.removeCallbacks(counterRunnable);
    }

    private void playDate() {
        DateTime now = new DateTime();

        Log.d(TAG, "getYear: "+now.getYear());
        Log.d(TAG, "getYearOfCentury: "+now.getYearOfCentury());
        Log.d(TAG, "getYearOfEra: "+now.getYearOfEra());
    }
}
