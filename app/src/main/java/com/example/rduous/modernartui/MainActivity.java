package com.example.rduous.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private SeekBar seekBar;

    static final String URL = "http://MoMA.org";

    final static int[] coloredRectangleIds = new int[] {R.id.bottomLeft, R.id.bottomCenter, R.id.bottomRight,
                R.id.centerRight, R.id.topRight};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<FrameLayout> frames = getColoredRectangles();
        final int[] baseColors = getDefaultColors(frames);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //change color by progress
                float p = progress/100f;
                for (int i=0; i<frames.size(); i++) {
                    FrameLayout frame = frames.get(i);
                    frame.setBackgroundColor(mutateColor(baseColors[i],p));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            private int mutateColor(int baseColor, float proportion) {
                float[] hsv = new float[3];
                Color.colorToHSV(baseColor, hsv);
                for (int i=0; i<3; i++) {
                    hsv[i] =  hsv[i] *  (1 - proportion/(5)) ;
                }
                return Color.HSVToColor(hsv);
            }
        });
    }

    private int[] getDefaultColors(List<FrameLayout> frames) {
        int[] defaultColors = new int[frames.size()];
        int i = 0;
        for(FrameLayout frame : frames) {
            ColorDrawable background = (ColorDrawable) frame.getBackground();
            defaultColors[i] =background.getColor();
            i++;
        }
        return defaultColors;
    }

    private List<FrameLayout> getColoredRectangles() {
        List<FrameLayout> frames = new ArrayList<>();
        int app_white = getResources().getColor(R.color.app_white);
        for (int id : coloredRectangleIds) {
            FrameLayout rectangle = (FrameLayout) findViewById(id);
            ColorDrawable background = (ColorDrawable) rectangle.getBackground();
            if (background.getColor() != app_white) {
                frames.add(rectangle);
            }
        }
        return frames;
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
}
