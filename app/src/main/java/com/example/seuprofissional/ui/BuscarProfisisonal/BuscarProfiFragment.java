package com.example.seuprofissional.ui.BuscarProfisisonal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seuprofissional.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterProfisisonal;
import model.Profissional;

public class BuscarProfiFragment extends Fragment {

    // private SearchView searchProfi;
    private RecyclerView recyclerView;
    private Profissional profissional;
    private AdapterProfisisonal adapter;


   // private FirestoreRecyclerAdapter adapter;
    // private AdapterPesquisa adapterPesquisa;

    private ArrayList<Profissional> listaProfissional;
    private DatabaseReference profissionalRef;
    private FirebaseFirestore fStore;

    private BuscarProfiViewModel buscarProfiViewModel;


    public BuscarProfiFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        // buscarProfiViewModel = new ViewModelProvider(this).get(BuscarProfiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fStore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);





//RecyclerOption
        FirebaseRecyclerOptions<Profissional> options = new FirebaseRecyclerOptions.Builder<Profissional>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Profissional"), Profissional.class).build();

        adapter = new AdapterProfisisonal(options);
        recyclerView.setAdapter(adapter);






        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}



