package app.appplanet.com.mytoolbar;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by digivox on 01/03/16.
 */
public class CompassFragment extends Fragment implements SensorEventListener {
    private final static String TAG = "QiblaFrament";

    private SensorManager mSensorManager;
    private ImageView imgCompass;
    private TextView tvHeading;

    private float currentDegree = 0f;

    float[] mGravity = new float[3];
    float[] mGeomagnetic = new float[3];

    private LocationManager mLocManager;
    private LocationListener mLocListener;
    public final static int PERMISSION_REQUEST_FINE_LOCATION = 100;
    public final static int PERMISSION_REQUEST_COURSE_LOCATION = 101;

    //////////////////////////////////////
    static final float ALPHA = 0.4f;
    //////////////////////////////////////
    static final long ANIM_DURATION = 20;

    // link: http://www.techrepublic.com/article/pro-tip-create-your-own-magnetic-compass-using-androids-internal-sensors/
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_compass, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        performLocationPermissionCheck();

        if (mSensorManager == null) {
            mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        }

        imgCompass = (ImageView) getView().findViewById(R.id.iv_compass);
        tvHeading = (TextView) getView().findViewById(R.id.tv_log);

        //initLocation();
    }

    @Override
    public void onResume() {
        super.onResume();

        Sensor accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // for the system's orientation sensor registered listeners
        //mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "Receiving sensor event!");
        //oldVersion(event);
        newVersionNotWorking(event);
        //newVersionWorking(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void oldVersion(SensorEvent event) {
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        //Log.d(TAG, "Heading: " + Float.toString(degree) + " degrees");


        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        // how long the animation will take place
        ra.setDuration(200);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        imgCompass.startAnimation(ra);
        currentDegree = -degree;
    }

    private void newVersionNotWorking(SensorEvent event) {
        float degree = 0f;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //mGravity = event.values;
            Log.d(TAG, "ACCELARATOR: ["+event.values[0]+"]["+event.values[1]+"]");

            float[] accelVals = lowPass(event.values.clone(), mGravity);
            mGravity[0] = accelVals[0];
            mGravity[1] = accelVals[1];
            mGravity[2] = accelVals[2];
        } if ((event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)||(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)) {
            //mGeomagnetic = event.values;

            float[] magVals = lowPass(event.values.clone(), mGeomagnetic);
            mGeomagnetic[0] = magVals[0];
            mGeomagnetic[1] = magVals[1];
            mGeomagnetic[2] = magVals[2];
            Log.d(TAG, "MAGNETIC: ["+event.values[0]+"]["+event.values[1]+"]");
        } if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                //degree = orientation[0]; // orientation contains: azimut, pitch and roll
                degree = (float) (Math.toDegrees(orientation[0]) + 360) % 360;

                float bearingOld = currentDegree;
                float bearingNew = degree;//-getDirectionToMecca(null);

                // create a rotation animation (reverse turn degree degrees)
                RotateAnimation ra = new RotateAnimation(
                        /*currentDegree*/bearingOld,
                        /*-degree*/-bearingNew,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

                // how long the animation will take place
                ra.setDuration(ANIM_DURATION);

                // set the animation after the end of the reservation status
                ra.setFillAfter(true);

                tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
                //Log.d(TAG, "Heading: " + Float.toString(degree) + " degrees");

                // Start the animation
                imgCompass.startAnimation(ra);
                currentDegree = /*-degree*/-bearingNew;
            }
        }
    }

    private void initLocation() {
        mLocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                /*getDirectionToMecca(location);*/
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocListener);
            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
        } else {
            mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocListener);
            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
        }
    }

    private void performLocationPermissionCheck() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_COURSE_LOCATION);

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_FINE_LOCATION);
        }
    }

    /**
     * @see //http://en.wikipedia.org/wiki/Low-pass_filter#Algorithmic_implementation
     * @see //http://developer.android.com/reference/android/hardware/SensorEvent.html#values
     */
    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;

        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }
}
