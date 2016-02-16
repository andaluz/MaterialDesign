package app.appplanet.com.mytoolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by digivox on 15/02/16.
 */
public class TransparentFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transparent_toolbar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        modifyToolbar();
    }

    private void modifyToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //
        } else {
            //
        }
        //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new Drawable() {
        //});
        Toolbar toolbar = (Toolbar)((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        //toolbar.setBackgroundResource(android.R.color.transparent);
        toolbar.setVisibility(View.GONE);

        //Toolbar tb = new Toolbar(getActivity());
        //tb.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    public void onStop() {
        super.onStop();

        Toolbar toolbar = (Toolbar)((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
}
