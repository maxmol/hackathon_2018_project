package i.maxmol.hackapp;


import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Scanner;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;
import kotlin.Unit;

import static io.fotoapparat.selector.LensPositionSelectorsKt.front;


/**
 * MainActivity
 */
public class MainActivity extends Activity {

    public static MainActivity context;
    public ProgressDialog progressBar;
    public Fotoapparat fotoapparat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        Typeface typeface = ResourcesCompat.getFont(this, R.font.raleway_semibold);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.reckoner_bold);

        final Button shootBut = findViewById(R.id.shoot_button);
        shootBut.setAlpha(0f);
        shootBut.setEnabled(false);

        final RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(55);
        rotatingTextWrapper.setTypeface(typeface2);

        final LinearLayout welcome = findViewById(R.id.welcomelayout);

        final Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Explore", "Fun", "Enjoy", "Voyage", "Travel", "Enrich", "Relax", "Rest", "Holiday");
        rotatable.setSize(55);
        rotatable.setAnimationDuration(500);
        rotatable.setTypeface(typeface);
        rotatable.setCenter(true);

        rotatingTextWrapper.setContent("Press to ?", rotatable);

        rotatingTextWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotatingTextWrapper.setOnClickListener(null);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                TranslateAnimation animate = new TranslateAnimation(0,0,0,displayMetrics.heightPixels * 1.1f);
                animate.setDuration(1000);
                animate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ((RelativeLayout)welcome.getParent()).removeView(welcome);
                        shootBut.setEnabled(true);
                        shootBut.animate()
                                .alpha(1f)
                                .setDuration(500)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                welcome.startAnimation(animate);

            }
        });

        CameraView cameraView = findViewById(R.id.camera_view);
        fotoapparat = Fotoapparat
                .with(context)
                .into(cameraView)
                .lensPosition(front())
                .build();
        fotoapparat.start();
        shootBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = new ProgressDialog(MainActivity.context);
                progressBar.setCancelable(false);
                progressBar.setMessage("Saving Image...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();
                try {
                    final PhotoResult photoResult = fotoapparat.takePicture();   // Asynchronously saves photo to file
                    final File file = new File(context.getFilesDir() + "/img.jpg");
                    photoResult.saveToFile(file).whenDone(new WhenDoneListener<Unit>() {
                        @Override
                        public void whenDone(@Nullable Unit unit) {
                            fotoapparat.stop();
                            SendImage.upload(file);

                            progressBar.setMessage("Uploading Image...");
                            Log.i("File send","ok");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        try {
            File file = new File(context.getFilesDir() + "/db.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                scanner.next(); // data line
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        fotoapparat.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        fotoapparat.stop();
        super.onPause();
    }
}

