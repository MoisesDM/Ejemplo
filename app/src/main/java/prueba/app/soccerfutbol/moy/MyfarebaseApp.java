package prueba.app.soccerfutbol.moy;

import com.google.firebase.database.FirebaseDatabase;

public class MyfarebaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
