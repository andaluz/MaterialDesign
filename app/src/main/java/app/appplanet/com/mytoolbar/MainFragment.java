package app.appplanet.com.mytoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by digivox on 15/02/16.
 */
public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button detail = (Button) getView().findViewById(R.id.button_detail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new DetailFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button transparent = (Button) getView().findViewById(R.id.button_transparent);
        transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new TransparentFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button compass = (Button) getView().findViewById(R.id.button_compass);
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new CompassFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button statusbar = (Button) getView().findViewById(R.id.button_statusbar);
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new CompassFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
