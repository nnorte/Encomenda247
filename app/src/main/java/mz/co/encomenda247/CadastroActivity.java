package mz.co.encomenda247;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroActivity extends AppCompatActivity {
    EditText  nome, apelido;
    Button btn_confirmar;
    CircleImageView foto_perfil;
    final int REQUISACAO_GALERIA = 999;
    private Uri fotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.nome);
        apelido = findViewById(R.id.apelido);
        foto_perfil = findViewById(R.id.foto_perfil);

        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFoto();
            }
        });

        btn_confirmar = findViewById(R.id.btn_confirmar);

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nome.getText().toString().isEmpty() || apelido.getText().toString().isEmpty()){
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    if (nome.getText().toString().length() < 3 || apelido.getText().toString().length() < 3){
                        Toast.makeText(CadastroActivity.this, "Introduza um nome & apelido validos 3 caracteres no minimo", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CadastroActivity.this, "Cadastrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void selecionarFoto() {
        ActivityCompat.requestPermissions(CadastroActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUISACAO_GALERIA
        );
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode ==RESULT_OK && data != null && data.getData() != null){
            fotoUri = data.getData();
            foto_perfil.setImageURI(fotoUri);
        }
    }
}