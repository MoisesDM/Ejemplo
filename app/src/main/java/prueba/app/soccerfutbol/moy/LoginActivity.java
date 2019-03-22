package prueba.app.soccerfutbol.moy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText TextEmail, TextContra;
    private Button btnRegistrar,btnEntrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = firebaseAuth.getInstance();
        this.TextEmail = (EditText) findViewById(R.id.email);
        this.TextContra = (EditText) findViewById(R.id.contra);
        this.btnRegistrar = (Button) findViewById(R.id.btnlogin);
        this.btnEntrar = (Button) findViewById(R.id.btnEntrar);
        progressDialog = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(this);

        btnEntrar.setOnClickListener(this);
    }

    public void Registrar() {
        String email = TextEmail.getText().toString().trim();
        String contra = TextContra.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Ingresa el E-mail", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(contra)) {

            Toast.makeText(LoginActivity.this, "Falta ingresar la Contraseña", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Espere un momento .....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, contra).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Se ha Registrado  el usuario con el e.mail ".concat(TextEmail.getText().toString()), Toast.LENGTH_SHORT).show();
                    TextEmail.setText("");
                    TextContra.setText("");
                } else {
                    if  (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(LoginActivity.this, "el usuario ya existe porfavor entra ", Toast.LENGTH_SHORT).show();

                    }else
                    Toast.makeText(LoginActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        });
    }
    private void logeo() {
        String email = TextEmail.getText().toString().trim();
        String contra = TextContra.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Ingresa el E-mail", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(contra)) {

            Toast.makeText(LoginActivity.this, "Falta ingresar la Contraseña", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Conectando .....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, contra).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Bienvenido  ".concat(TextEmail.getText().toString()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);
                    TextEmail.setText("");
                    TextContra.setText("");
                } else {
                    if  (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(LoginActivity.this, "el usuario ya existe porfavor entra ", Toast.LENGTH_SHORT).show();

                    }else
                        Toast.makeText(LoginActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        });


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnlogin:
                Registrar();
                break;
            case  R.id.btnEntrar:
                logeo();
                break;

        }
    }


}
