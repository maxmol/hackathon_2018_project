package i.maxmol.hackapp;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        Button shootBut = findViewById(R.id.shoot_button);

        CameraView cameraView = findViewById(R.id.camera_view);
        final Fotoapparat fotoapparat = Fotoapparat
                .with(context)
                .into(cameraView)
                .lensPosition(front())
                .build();
        fotoapparat.start();
        shootBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final PhotoResult photoResult = fotoapparat.takePicture();   // Asynchronously saves photo to file
                    final File file = new File(context.getFilesDir() + "/img.jpg");
                    photoResult.saveToFile(file).whenDone(new WhenDoneListener<Unit>() {
                        @Override
                        public void whenDone(@Nullable Unit unit) {
                            SendImage.upload(file);
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
}

