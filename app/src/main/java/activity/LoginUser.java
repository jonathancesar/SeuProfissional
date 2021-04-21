package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.seuprofissional.MainActivity;
import com.example.seuprofissional.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import helper.ConfiguracaoFirebase;
import model.Usuario;

public class LoginUser extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnEntrar;
    private ProgressBar progressBar;

    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificarUsuarioLogado();
        inicializarComponentes();


        //FAZER LOGIN DO USUÁRIO

        //progressBar.setVisibility( View.GONE );
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = edtEmail.getText().toString();
                String txtSenha = edtSenha.getText().toString();

                if(!txtEmail.isEmpty()){
                    if(!txtSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(txtEmail);
                        usuario.setSenha(txtSenha);
                        validarLogin(usuario);

                    }else{
                        Toast.makeText(LoginUser.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginUser.this,
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
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }
    }


    //VALIDAR CADASTRO USUÁRIO e LOGAR USUÁRIO
    public void validarLogin(Usuario usuario){
      // progressBar.setVisibility( View.VISIBLE );
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //progressBar.setVisibility(View.GONE);


                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginUser.this,
                            "Erro ao fazer login!",
                            Toast.LENGTH_SHORT).show();
                   // progressBar.setVisibility(View.GONE);

                }
            }
        });

    }

    public void abrirCadastro(View view){
        Intent i = new Intent(LoginUser.this, CadastroUsuario.class);
        startActivity( i );
    }

    private void inicializarComponentes() {

        edtEmail = findViewById(R.id.edtEmailUser);
        edtSenha = findViewById(R.id.edtSenhaUser);
        btnEntrar = findViewById(R.id.btnEntrar);

        edtEmail.requestFocus();
    }



}