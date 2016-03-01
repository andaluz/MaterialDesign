package app.appplanet.com.mytoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by digivox on 15/02/16.
 */
public class DetailFragment extends Fragment implements TextWatcher{
    private final String TAG = "DetailFragment";

    private static final String[] COUNTRIES = new String[] {
            "Morocco", "Belgium", "France", "Italy", "Germany", "Spain",
            "Morocco", "Belgium", "France", "Italy", "Germany", "Spain",
            "Morocco", "Belgium", "France", "Italy", "Germany", "Spain",
            "Morocco", "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        initializeAutocompletion();
        showSpinner();
    }

    private void initializeAutocompletion() {
        AutoCompleteTextView auto = (AutoCompleteTextView) getView().findViewById(R.id.at_countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        auto.setAdapter(adapter);
    }

    private void showSpinner() {
        Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        //        android.R.layout.simple_spinner_item, COUNTRIES);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.custom_spinner_item, COUNTRIES);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter adapter = (ArrayAdapter) parent.getAdapter();
                Log.d(TAG, "Selected item: " + adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SeekBar seekBar = (SeekBar) getView().findViewById(R.id.seekbar);
        final TextView tvProgress = (TextView) getView().findViewById(R.id.tv_progress);

        //seekBar.setMax(100);
        seekBar.setMax(3000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //tvProgress.setText(String.valueOf((float)(progress/100.0f)));
                tvProgress.setText(String.format("%3.2f", (float) (progress / 100.0f)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        EditText input = (EditText) getView().findViewById(R.id.et_input);
        input.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() != 0) {
            TextView tvProgress = (TextView) getView().findViewById(R.id.tv_progress);
            tvProgress.setText("Yes we got changes!");
        }
    }
}
