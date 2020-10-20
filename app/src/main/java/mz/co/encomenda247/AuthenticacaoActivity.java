package mz.co.encomenda247;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AuthenticacaoActivity extends AppCompatActivity {

    EditText tv_contacto, nome, apelido;
    Button btn_confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticacao);

        tv_contacto = findViewById(R.id.tv_contacto);

        btn_confirmar = findViewById(R.id.btn_confirmar);

       btn_confirmar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (tv_contacto.getText().toString().isEmpty()){
                   Toast.makeText(AuthenticacaoActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
               }else if (tv_contacto.getText().toString().replace("", "").length() != 9){
                   Toast.makeText(AuthenticacaoActivity.this, "Digite um numero valido", Toast.LENGTH_SHORT).show();
               }else{
                 Intent intent = new Intent(getApplicationContext(), VerificarNumeroActivity.class);
                 intent.putExtra("contacto",tv_contacto.getText().toString());
                 startActivity(intent);
                 finish();
               }
           }
       });
    }
}
