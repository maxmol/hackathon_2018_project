package i.maxmol.hackapp;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.util.Scanner;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.log.LoggersKt;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.selector.FlashSelectorsKt;
import io.fotoapparat.selector.FocusModeSelectorsKt;
import io.fotoapparat.selector.LensPositionSelectorsKt;
import io.fotoapparat.selector.ResolutionSelectorsKt;
import io.fotoapparat.selector.SelectorsKt;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.selector.LensPositionSelectorsKt.front;


/**
 * MainActivity
 */
public class MainActivity extends Activity {

    public static MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;
        CameraView cameraView = findViewById(R.id.camera_view);
        Fotoapparat fotoapparat = Fotoapparat
                .with(context)
                .into(cameraView)
                .lensPosition(front())
                .build();
        fotoapparat.start();
        fotoapparat.takePicture();
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
}

