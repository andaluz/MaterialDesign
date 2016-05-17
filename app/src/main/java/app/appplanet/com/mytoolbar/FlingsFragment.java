package app.appplanet.com.mytoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by digivox on 17/05/16.
 */
public class FlingsFragment extends Fragment {
    private final static String TAG = "FlingsFragment";

    private GestureDetector gestureDetector;
    private TextView tvFling;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fling, container, false);

        initGesture(root);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        initUI();
    }

    private void initUI() {
        tvFling = (TextView) getView().findViewById(R.id.tv_fling);
    }


    private void initGesture(View view) {
        if(gestureDetector == null) {
            Log.d(TAG, "initGesture() - Creating GestureDetector object.");

            gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    Log.d(TAG, "onFling() - Getting Motion events!");
                    String swipe = "";
                    float sensitvity = 50;

                    if ((e1.getY() - e2.getY()) > sensitvity) {
                        swipe += "Swipe Up\n";
                        tvFling.setText("Swipe up!");
                        Log.d(TAG, swipe);
                        return false;
                    } else if ((e2.getY() - e1.getY()) > sensitvity) {
                        swipe += "Swipe Down\n";
                        tvFling.setText("Swipe down!");
                        Log.d(TAG, swipe);
                        return false;
                    }

                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            });

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d(TAG, "onTouch() - Getting Motion events!");
                    if (gestureDetector != null) {
                        Log.d(TAG, "onTouch() - Passing events to Gesture.");
                    }

                    return gestureDetector.onTouchEvent(event);
                }
            });
        }
    }
}
