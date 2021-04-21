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

import com.example.seuprofissional.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import helper.ConfiguracaoFirebase;
import model.Usuario;

public class CadastroUsuario extends AppCompatActivity {

    private EditText edtNomeCompleto, edtEmail, edtSenha, edtFone;
    private Button botaoCadastrar;
    private ProgressBar progressbar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private FirebaseFirestore fStore;
    boolean valid = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        inicializarComponentes();
        autenticacao = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //CADASTRAR USUÁRIO

        progressbar.setVisibility(View.GONE);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                String txtNome = edtNomeCompleto.getText().toString();
                String txtEmail = edtEmail.getText().toString();
                String txtSenha = edtSenha.getText().toString();
                String txtFone = edtFone.getText().toString();


                if (!txtNome.isEmpty()) {
                    if (!txtFone.isEmpty()) {
                    if (!txtEmail.isEmpty()) {
                        if (!txtSenha.isEmpty()) {


                                usuario = new Usuario();
                                usuario.setNome(txtNome);

                                usuario.setEmail(txtEmail);
                                usuario.setSenha(txtSenha);
                                usuario.setFone(txtFone);
                                cadastrar(usuario);

                            } else {
                                Toast.makeText(CadastroUsuario.this,
                                        "Preencha a senha!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CadastroUsuario.this,
                                    "Preencha o E-mail!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroUsuario.this,
                                "Preencha o nome telefone!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(CadastroUsuario.this,
                            "Preencha o Nome Completo",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void inicializarComponentes() {
        edtNomeCompleto = findViewById(R.id.edtNomeCompleto);

        edtEmail = findViewById(R.id.edtEmailUser);
        edtSenha = findViewById(R.id.edtSenhaUser);
        edtFone = findViewById(R.id.edtFone);
        botaoCadastrar = findViewById(R.id.btnCadastrar);
        progressbar = findViewById(R.id.progressBarCadastro);
        edtEmail.requestFocus();

    }


    //MÉTODO PARA CADASTRAR USUÁRIO
    public void cadastrar(Usuario usuario) {

        progressbar.setVisibility(View.GONE);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    try {

                                        progressbar.setVisibility(View.GONE);

                                        //Salvar dados no firebase e FS
                                        FirebaseUser user = autenticacao.getCurrentUser();
                                        DocumentReference df = fStore.collection("Usuarios").document(user.getUid());
                                        Map<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("Nome Completo:", edtNomeCompleto.getText().toString());

                                        userInfo.put("E-mail", edtEmail.getText().toString());
                                        userInfo.put("Senha", edtSenha.getText().toString());
                                       userInfo.put("Fone",edtFone.getText().toString());
                                        //especificando o usuário
                                        userInfo.put("Usuario","1");
                                        df.set(userInfo);


                                         String idUsario = task.getResult().getUser().getUid();
                                         usuario.setId(idUsario);
                                         usuario.salvar();

                                        Toast.makeText(CadastroUsuario.this,
                                                "Cadastro realizado com sucesso!",
                                                Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(), LoginUser.class));
                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    progressbar.setVisibility(View.GONE);
                                    String erroExcecao = "";
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        erroExcecao = "Digite uma senha mais forte!";
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        erroExcecao = "Por favor, digite um e-mail válido";
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        erroExcecao = "Esta conta já foi cadastrada";
                                    } catch (Exception e) {
                                        erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(CadastroUsuario.this,
                                            "Erro: " + erroExcecao,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                );

    }
}