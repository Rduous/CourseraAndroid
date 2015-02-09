package com.example.rduous.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private SeekBar seekBar;
    private FrameLayout bottomLeft;
    private FrameLayout bottomCenter;
    private FrameLayout bottomRight;
    private FrameLayout centerRight;
    private FrameLayout topRight;

    static final String URL = "http://MoMA.org";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int blColor = getResources().getColor(R.color.app_blue);
        final int bcColor = getResources().getColor(R.color.app_red);
        final int brColor = getResources().getColor(R.color.app_yellow);
        final int crColor = getResources().getColor(R.color.app_black);
        final int trColor = getResources().getColor(R.color.app_red);

        final Map<Integer, Integer> colorChanges = new HashMap<>();
        colorChanges.put(getResources().getColor(R.color.app_blue), getResources().getColor(R.color.app_red));
        colorChanges.put(getResources().getColor(R.color.app_red), getResources().getColor(R.color.app_black));
        colorChanges.put(getResources().getColor(R.color.app_black), getResources().getColor(R.color.app_yellow));
        colorChanges.put(getResources().getColor(R.color.app_yellow), getResources().getColor(R.color.app_blue));


        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
        }

        bottomLeft = (FrameLayout) findViewById(R.id.bottomLeft);
        bottomCenter = (FrameLayout) findViewById(R.id.bottomCenter);
        bottomRight = (FrameLayout) findViewById(R.id.bottomRight);
        centerRight = (FrameLayout) findViewById(R.id.centerRight);
        topRight = (FrameLayout) findViewById(R.id.topRight);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //change color by progress
                bottomLeft.setBackgroundColor(interpolateColor(blColor, colorChanges.get(blColor), progress/100f));
                bottomCenter.setBackgroundColor(interpolateColor(bcColor, colorChanges.get(bcColor), progress/100f));
                bottomRight.setBackgroundColor(interpolateColor(brColor, colorChanges.get(brColor), progress/100f));
                centerRight.setBackgroundColor(interpolateColor(crColor, colorChanges.get(crColor), progress/100f));
                topRight.setBackgroundColor(interpolateColor(trColor, colorChanges.get(trColor), progress/100f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            /** Returns an interpoloated color, between <code>a</code> and <code>b</code> */
            private int interpolateColor(int a, int b, float proportion) {
                float[] hsva = new float[3];
                float[] hsvb = new float[3];
                Color.colorToHSV(a, hsva);
                Color.colorToHSV(b, hsvb);
                for (int i = 0; i < 3; i++) {
                    hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
                }
                return Color.HSVToColor(hsvb);
            }

            private float interpolate(float a, float b, float proportion) {
                return (a + ((b - a) * proportion));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DialogFragment dialog = new InformationDialog();
            dialog.show(getFragmentManager(), "Info");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class InformationDialog extends DialogFragment {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new AlertDialog.Builder(getActivity()).
                        setMessage(R.string.information_dialog_message)
                        .setCancelable(true)
                        .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                InformationDialog.this.dismiss();
                            }
                        }).setPositiveButton("Visit MOMA",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            final DialogInterface dialog, int id) {
                                        Uri webPage = Uri.parse(URL);

                                        Intent baseIntent = new Intent(Intent.ACTION_VIEW, webPage);
                                        startActivity(baseIntent);
                                    }
                                })
                .create();
            }
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.activity_main, container, false);
//            return rootView;
//        }
//    }
}
