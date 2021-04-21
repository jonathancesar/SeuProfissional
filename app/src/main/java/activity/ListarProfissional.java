package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.seuprofissional.R;
import com.example.seuprofissional.ui.BuscarProfisisonal.BuscarProfiFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import model.Profissional;



public class ListarProfissional extends AppCompatActivity {

    private SearchView searchProfi;
    private RecyclerView recyclerView;
    private Profissional profissional;


    private FirestoreRecyclerAdapter adapter;
    // private AdapterPesquisa adapterPesquisa;

    private ArrayList<Profissional> listaProfissional;
    private DatabaseReference profissionalRef;
    private FirebaseFirestore fStore;
    private String idUsuarioLogado;
    private TextView funcaotela;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_profissional);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        funcaotela = findViewById(R.id.textView3);

        autenticacao = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//TALVEZ FUNCIONE
        fStore.collection("Profissional").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {

                            Profissional obj = d.toObject(Profissional.class);
                            listaProfissional.add(obj);
                             int total = listaProfissional.size();
                            Log.i("TotalProfi2","total" + total);





                        }
                         adapter.notifyDataSetChanged();

                    }
                });

//Query
        Query query = fStore.collection("Profissional");
//RecyclerOption
        FirestoreRecyclerOptions<Profissional> options = new FirestoreRecyclerOptions.Builder<Profissional>()
                .setQuery(query,Profissional.class).build();

        adapter = new FirestoreRecyclerAdapter<Profissional, ProfissionalViewHolder>(options) {
            @NonNull
            @Override
            public ProfissionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_busca,parent,false);
                return new ProfissionalViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProfissionalViewHolder holder, int position, @NonNull Profissional model) {
               holder.funcaotela.setText(model.getFuncao());
                //int total =  listaProfissional.size();
                //Log.i("Total","Total:" + total);

            }
        };
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        //return view;


    }

    private class ProfissionalViewHolder extends RecyclerView.ViewHolder{
        private TextView funcaotela;

        public ProfissionalViewHolder(@NonNull View itemView) {
            super(itemView);

            funcaotela = findViewById(R.id.funcao);
        }

    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}



