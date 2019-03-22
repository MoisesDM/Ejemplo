package prueba.app.soccerfutbol.moy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import prueba.app.soccerfutbol.moy.modelo.persona;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {
private List<persona> listaPerson=new ArrayList<persona>();
ArrayAdapter<persona> arrayAdapterPersona;
    EditText nomp,appP,coreoP;
    ListView listV_perosnas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    persona PersonaSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nomp=findViewById(R.id.txt_NombrePersona);
        appP=findViewById(R.id.txt_Apellidos);
        coreoP=findViewById(R.id.txt_correo);
        listV_perosnas=findViewById(R.id.lv_datosPersonas);

        inicializarFirebase();
        listarDatos();
        listV_perosnas.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonaSelect=(persona) parent.getItemAtPosition(position);
                nomp.setText(PersonaSelect.getNombre());
                appP.setText(PersonaSelect.getApellidos());
                coreoP.setText(PersonaSelect.getCorreo());
            }
            });
    }

    /**
     * En este metodo se mandan a llamar los datos para monstrarlo en el listview**
     *
     */
    private void listarDatos() {
        databaseReference.child("persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    persona p = objSnaptshot.getValue(persona.class);
                    listaPerson.add(p);
                arrayAdapterPersona= new ArrayAdapter<persona>(MainActivity.this, android.R.layout.simple_list_item_1,listaPerson);
                listV_perosnas.setAdapter(arrayAdapterPersona);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * En este Metodo se  se la intancia de la firebase
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
      //  firebaseDatabase.setPersistenceEnabled(true);
        databaseReference=firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nombre=nomp.getText().toString();
        String apellidos=appP.getText().toString();
        String correo=coreoP.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{
                if (nombre.equals("")|| correo.equals("")|| apellidos.equals("")){
                    validacion();
                }else {
                    persona p= new persona();
                    p.setuId(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellidos(apellidos);
                    p.setCorreo(correo);
                    databaseReference.child("persona").child(p.getuId()).setValue(p);
                    Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
                    limpiar();

                }
                break;
            }
            case R.id.icon_save:{
                if (nombre.equals("")|| apellidos.equals("")||correo.equals("")){
                    validacion();
                }
                else{
                persona p= new persona();
                p.setuId(PersonaSelect.getuId());
                p.setNombre(nomp.getText().toString().trim());
                p.setApellidos(appP.getText().toString().trim());
                p.setCorreo(coreoP.getText().toString().trim());
                databaseReference.child("persona").child(p.getuId()).setValue(p);
                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                limpiar();
                }
                break;
            }
            case R.id.icon_delete:{
                persona p= new persona();
                p.setuId(PersonaSelect.getuId());
                databaseReference.child("persona").child(p.getuId()).removeValue();
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                limpiar();
                break;
            }
            default: break;


        }
        return true;
    }

    private void limpiar() {
        nomp.setText("");
        appP.setText("");
        coreoP.setText("");
    }

    private void validacion() {
        String nombre=nomp.getText().toString();
        String apellidos=appP.getText().toString();
        String correo=coreoP.getText().toString();

            if (nombre.equals("")){
            nomp.setError("Requerido");
        }
        else if
        (apellidos.equals("")) {
            appP.setError("Requerido");
        }
        else if (correo.equals("")) {
                coreoP.setError("Requerido");
            }
    }
}
