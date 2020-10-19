package mz.co.encomenda247;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificarNumeroActivity extends AppCompatActivity {
    EditText verificacao;
    Button confirmar;
    ProgressBar progressBar;
    String codeDeVerificacaoAutomatica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_numero);

        ActivityCompat.requestPermissions(VerificarNumeroActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        Bundle bundle
                 = getIntent().getExtras();

    String contacto = bundle.getString("contacto");

    verificacao = findViewById(R.id.verificacao);
    confirmar = findViewById(R.id.btn_confirmar);
    progressBar = findViewById(R.id.loader);

    verficacar(contacto);

    }

    private void verficacar(String contacto) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+258" + contacto,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =  new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeDeVerificacaoAutomatica = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                progressBar.setVisibility(View.VISIBLE);
                verificarCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerificarNumeroActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verificarCode(String codeManual) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeDeVerificacaoAutomatica, codeManual);
        logar(credential);
    }

    private void logar(PhoneAuthCredential credential) {

        FirebaseAuth auth  = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(VerificarNumeroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                 //CRIAR METOD PATA CADATRO DE DADOS PESSOAIS

                }else{
                    Toast.makeText(VerificarNumeroActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}