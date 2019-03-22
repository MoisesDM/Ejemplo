package prueba.app.soccerfutbol.moy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashsActivity extends Activity {

    private  final int Duracion_Splash=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashs);
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Intent intent = new Intent(
                SplashsActivity.this, MainActivity.class
        );
        startActivity(intent);
        finish();
                      };
        },Duracion_Splash);
}
}
