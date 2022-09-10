package com.example.androidconsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegistrar;
    private Button btnInformacion;

    private SignInButton btnSignIn;
    private Button btnSignOut;
    private Button btnRevoke;
    private Spinner spinner;

    //CallbackManager callbackManager;

    private GoogleApiClient apiClient;
    private static final int RC_SIGN_IN = 1001;

    private ProgressDialog progressDialog;
    private static final String TAG = "MainActivity";

    //Llamamos al autenticador
    private FirebaseAuth auth;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();

        spinner = (Spinner) findViewById(R.id.spusu);
        String []opciones={"Administrador", "Usuario"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);

        spinner.setAdapter(adapter);
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnInformacion = (Button)findViewById(R.id.btnInformacion);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userE=etUser.getText().toString();
                String passE=etPassword.getText().toString();
                String txt = spinner.getSelectedItem().toString();
                //Ahora validamos por si uno de los campos esta vacío
                if(TextUtils.isEmpty(userE)){
                    //por si falta correo
                    Toast.makeText(MainActivity.this,"Inserte correo",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passE)){
                    //por si falta password
                    Toast.makeText(MainActivity.this,"Inserte contraseña",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Ahora usamos el Auth para que se logee una vez registrado
                auth.signInWithEmailAndPassword(userE,passE).
                        //Le pasamos la clase registro
                                addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"A ocurrido un error",Toast.LENGTH_SHORT).show();
                                    return;
                                }else if (txt.equals("Administrador")){
                                    Toast.makeText(MainActivity.this, "Bienvenido al Intermediario", Toast.LENGTH_SHORT).show();
                                    Intent i =new Intent(MainActivity.this, inermediate.class);
                                    startActivity(i);
                                }else if (txt.equals("Usuario")){
                                    Toast.makeText(MainActivity.this, "Bienvenido a Usuario", Toast.LENGTH_SHORT).show();
                                    Intent i =new Intent(MainActivity.this, SubirActas.class);
                                    startActivity(i);
                                }

                               // Toast.makeText(MainActivity.this,"Bienvenido", Toast.LENGTH_SHORT).show();

                                //Intent i = new Intent(MainActivity.this,inermediate.class);
                                //startActivity(i);
                            }
                        });
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Registro.class);
                startActivity(i);
            }
        });

        btnSignIn = (SignInButton)findViewById(R.id.sign_in_button);
        btnSignOut = (Button)findViewById(R.id.sign_out_button);
        btnRevoke = (Button)findViewById(R.id.revoke_button);

        //Google API Client

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Personalización del botón de login

        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setColorScheme(SignInButton.COLOR_LIGHT);
        btnSignIn.setScopes(gso.getScopeArray());

        //Eventos de los botones

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                updateUI(false);
                            }
                        });
            }



        });

        btnInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Informacion.class);
                startActivity(i);
            }
        });



        btnRevoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.revokeAccess(apiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                updateUI(false);
                            }
                        });
            }
        });

        updateUI(false);

    }
    private void updateUI(boolean signedIn) {
        if (signedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevoke.setVisibility(View.VISIBLE);
        } else {

            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnRevoke.setVisibility(View.GONE);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String txtt = spinner.getSelectedItem().toString();

        if (requestCode == RC_SIGN_IN&&txtt.equals("Administrador")) {
            GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
            Toast.makeText(this, "Bienvenido a Administrador", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, inermediate.class );
            startActivity(i);
        }else if(txtt.equals("Usuario")){
            Toast.makeText(this, "Bienvenido a Usuario ;)", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, VerActas.class );
            startActivity(i);
        }

        //Toast.makeText(this, "No existe el producto con dicho código", Toast.LENGTH_SHORT).show();
        //Intent i = new Intent(this, inermediate.class );
        //startActivity(i);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //Usuario logueado --> Mostramos sus datos
            GoogleSignInAccount acct = result.getSignInAccount();
            updateUI(true);
        } else {
            //Usuario no logueado --> Lo mostramos como "Desconectado"
            updateUI(false);
        }
    }

}