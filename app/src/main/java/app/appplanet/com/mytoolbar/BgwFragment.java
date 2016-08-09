package app.appplanet.com.mytoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * Created by digivox on 08/08/16.
 */
public class BgwFragment extends Fragment {
    private final static String TAG = "BgwFragment";

    private RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        initializeUI();
    }

    private void initializeUI() {
        Button btn = (Button) getView().findViewById(R.id.btn_fire_bgw);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAsynchornousWork();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(queue!=null)
            queue.cancelAll(TAG);
    }

    private void performAsynchornousWork() {
        Log.i(TAG, "performAsynchornousWork()");
        final WeakReference<Fragment> fragmentWeakRef;
        fragmentWeakRef = new WeakReference<Fragment>(this);

        //String url = "https://al-yaqeen.com/wp-json/api/v1/gebedstijden/steden/Rotterdam";
        String url = "https://al-yaqeen.com/api_prayer.php?action=listCities";

        queue = Volley.newRequestQueue(getActivity(), new HurlStack());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (fragmentWeakRef.get() != null) {
                            Log.d(TAG, response.toString());
                        } else {
                            Log.d(TAG, "BgwFragment doesn't exist anymore :(");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (fragmentWeakRef.get() != null) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                } else {
                    Log.d(TAG, "BgwFragment doesn't exist anymore :(");
                }
            }
        });
        stringRequest.setTag(TAG);

        queue.add(stringRequest);
    }
}
