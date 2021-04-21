package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seuprofissional.MainActivity;
import com.example.seuprofissional.MainActivityProfissional;
import com.example.seuprofissional.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import helper.ConfiguracaoFirebase;
import model.Profissional;
import model.Usuario;

public class LoginProfissional extends AppCompatActivity {

    private EditText edtEmailProfi,edtSenhaProfi;
    private Button btnEntrarProfi;
    private Profissional profissional;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_profissional);
        verificarUsuarioLogado();
        inicializarComponentes();

        //FAZER LOGIN DO USUÁRIO

        //progressBar.setVisibility( View.GONE );
        btnEntrarProfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = edtEmailProfi.getText().toString();
                String txtSenha = edtSenhaProfi.getText().toString();

                if(!txtEmail.isEmpty()){
                    if(!txtSenha.isEmpty()){
                        profissional = new Profissional();
                        profissional.setEmailProfi(txtEmail);
                        profissional.setSenhaProfi(txtSenha);
                        validarLogin(profissional);

                    }else{
                        Toast.makeText(LoginProfissional.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginProfissional.this,
                            "Preencha o e-mail!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //VERIFICAR USUÁRIO LOGADO

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivityProfissional.class));
            finish();

        }
    }

    //VALIDAR CADASTRO USUÁRIO e LOGAR USUÁRIO
    public void validarLogin(Profissional profissional){
        // progressBar.setVisibility( View.VISIBLE );
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                profissional.getEmailProfi(),
                profissional.getSenhaProfi()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //progressBar.setVisibility(View.GONE);


                    startActivity(new Intent(getApplicationContext(), MainActivityProfissional.class));
                    finish();
                }else{
                    Toast.makeText(LoginProfissional.this,
                            "Erro ao fazer login!",
                            Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);

                }
            }
        });

    }



    public void abrirCadastro(View view){
        Intent i = new Intent(LoginProfissional.this, CadastroProfisisonal.class);
        startActivity( i );
    }

    private void inicializarComponentes(){
        edtEmailProfi = findViewById(R.id.edtEmailProfissional);
        edtSenhaProfi = findViewById(R.id.edtSenhaProfissional);
        btnEntrarProfi = findViewById(R.id.btnEntrarProfi);
        edtSenhaProfi.requestFocus();


    }


}