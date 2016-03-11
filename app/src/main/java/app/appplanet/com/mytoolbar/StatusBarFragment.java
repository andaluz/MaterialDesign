package app.appplanet.com.mytoolbar;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by digivox on 11/03/16.
 */
public class StatusBarFragment extends Fragment {
    private final String TAG = "StatusBarFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statusbar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        initializeUI();
    }

    private void initializeUI() {
        Button btn = (Button) getView().findViewById(R.id.btn_press);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

                Toolbar toolbar = (Toolbar)((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
                toolbar.setVisibility(View.GONE);
            }
        });
    }


}
