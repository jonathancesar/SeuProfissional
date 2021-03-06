package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seuprofissional.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import helper.ConfiguracaoFirebase;
import model.Profissional;

public class CadastroProfisisonal extends AppCompatActivity {

    private EditText edtNomeCompletoProfi, edtApelidoProfi,edtEmailProfi, edtSenhaProfi, edtFoneProfi,  edtFuncao, edtdescricao;
    private Button btnCadastrarProfi;
    private CheckBox checkSeg;
    private CheckBox checkTer;
    private CheckBox checkQuar;
    private CheckBox checkQuin;
    private CheckBox checkSex;
    private CheckBox checkSab;
    private CheckBox checkDom;
    private Profissional profissional;
    private FirebaseAuth autenticacao;
    private FirebaseFirestore fStore;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_profisisonal);
        inicializarComponentes();
        autenticacao = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        reference = database.getInstance().getReference().child("Profissional");



        btnCadastrarProfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtnomeProfi = edtNomeCompletoProfi.getText().toString();
                String txtApelidoProfi = edtApelidoProfi.getText().toString();
                String txtemailProfi = edtEmailProfi.getText().toString();
                String txtsenhaProfi = edtSenhaProfi.getText().toString();
                String txtfoneProfi = edtFoneProfi.getText().toString();
                String txtfuncaoProfi = edtFuncao.getText().toString();
                String txtDescricaoProfi = edtdescricao.getText().toString();

                String CheckSegu = checkSeg.getText().toString();
                String CheckTerc = checkTer.getText().toString();
                String CheckQuart = checkQuar.getText().toString();
                String CheckQuint = checkQuin.getText().toString();
                String CheckSext = checkSex.getText().toString();
                String CheckSaba = checkSab.getText().toString();
                String CheckDomi = checkDom.getText().toString();

                if (!txtnomeProfi.isEmpty()) {
                    if (!txtApelidoProfi.isEmpty()) {
                        if(!txtfuncaoProfi.isEmpty()){
                        if (!txtemailProfi.isEmpty()) {
                            if(!txtsenhaProfi.isEmpty()){
                            profissional = new Profissional();
                            profissional.setNomeProfi(txtnomeProfi);
                            profissional.setApelidoProfi(txtApelidoProfi);
                            profissional.setEmailProfi(txtemailProfi);
                            profissional.setSenhaProfi(txtsenhaProfi);
                            profissional.setFoneProfi(txtfoneProfi);
                            profissional.setFuncao(txtfuncaoProfi);
                            profissional.setDescricao(txtDescricaoProfi);
                            profissional.setSeg(CheckSegu);
                            profissional.setTer(CheckTerc);
                            profissional.setQuar(CheckQuart);
                            profissional.setQuin(CheckQuint);
                            profissional.setSex(CheckSext);
                            profissional.setSab(CheckSaba);
                            profissional.setDom(CheckDomi);
                            cadastrar(profissional);

                            //MENSAGEM PARA COLOCAR: "Preencha a descri????o do profisisonal!" e Preencha a fun????o do profissional

                        } else {
                                Toast.makeText(CadastroProfisisonal.this,
                                        "Preencha a senha!",
                                        Toast.LENGTH_SHORT).show();
                         }

                        } else {
                            Toast.makeText(CadastroProfisisonal.this,
                                    "Preencha o email!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        } else {
                            Toast.makeText(CadastroProfisisonal.this,
                                    "Preencha a fun????o!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroProfisisonal.this,
                                "Preencha o apelido!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroProfisisonal.this,
                            "Preencha o nome completo!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void inicializarComponentes(){
        edtNomeCompletoProfi = findViewById(R.id.edtNomeCompletoProfi);
        edtApelidoProfi= findViewById(R.id.edtApelidoProfi);
        edtEmailProfi =  findViewById(R.id.edtEmailUser);
        edtSenhaProfi = findViewById(R.id.edtSenhaUser);
        edtFoneProfi = findViewById(R.id.edtFoneProfi);
        edtdescricao = findViewById(R.id.edtDescricao);
        edtFuncao = findViewById(R.id.edtFuncao);
        btnCadastrarProfi = findViewById(R.id.btnCadastrarProfi);
        checkSeg = findViewById(R.id.checkSeg);
        checkTer = findViewById(R.id.checkTer);
        checkQuar = findViewById(R.id.checkQuar);
        checkQuin = findViewById(R.id.checkQuin);
        checkSex = findViewById(R.id.checkSex);
        checkSab = findViewById(R.id.checkSab);
        checkDom = findViewById(R.id.checkDom);

    }


    //M??TODO PARA CADASTRAR PROFISISONAL
    public void cadastrar(Profissional profissional) {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                profissional.getEmailProfi(),
                profissional.getSenhaProfi()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            try {



                                //Salvar dados no firebase e FS
                                FirebaseUser user = autenticacao.getCurrentUser();
                                DocumentReference df = fStore.collection("Profissional").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("NomeCompleto", edtNomeCompletoProfi.getText().toString());
                                userInfo.put("Apelido", edtApelidoProfi.getText().toString());
                                userInfo.put("Email", edtEmailProfi.getText().toString());
                                userInfo.put("Senha", edtSenhaProfi.getText().toString());
                                userInfo.put("Fone",edtFoneProfi.getText().toString());
                                userInfo.put("Funcao",edtFuncao.getText().toString());
                                userInfo.put("Descricao",edtdescricao.getText().toString());

                                if(checkSeg.isChecked()){
                                    userInfo.put("Segunda",checkSeg.getText().toString());

                                }else{

                                }if(checkTer.isChecked()){
                                    userInfo.put("Ter??a",checkTer.getText().toString());

                                }else{

                                }if(checkQuar.isChecked()){
                                    userInfo.put("Quarta",checkQuar.getText().toString());

                                }else{

                                }if(checkQuin.isChecked()){
                                    userInfo.put("Quinta",checkQuin.getText().toString());

                                }else{

                                }if(checkSex.isChecked()){
                                    userInfo.put("Sexta",checkSex.getText().toString());

                                }else{

                                }if(checkSeg.isChecked()){
                                    userInfo.put("Sabado",checkSeg.getText().toString());

                                }else{

                                }if(checkDom.isChecked()){
                                    userInfo.put("Domingo",checkDom.getText().toString());

                                }else{

                                }

                                //especificando o usu??rio
                                userInfo.put("Profissional","2");
                                df.set(userInfo); // adiciona dados SOMENTE no firestore


                                String idUsario = task.getResult().getUser().getUid();
                               profissional.setId(idUsario); //adiciona SOMENTE no REALTIME
                               profissional.salvar();

                                Toast.makeText(CadastroProfisisonal.this,
                                        "Cadastro realizado com sucesso",
                                        Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), LoginProfissional.class));
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {

                            String erroExcecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erroExcecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroExcecao = "Por favor, digite um e-mail v??lido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erroExcecao = "Este conta j?? foi cadastrada";
                            } catch (Exception e) {
                                erroExcecao = "ao cadastrar usu??rio: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroProfisisonal.this,
                                    "Erro: " + erroExcecao,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );

    }



}


