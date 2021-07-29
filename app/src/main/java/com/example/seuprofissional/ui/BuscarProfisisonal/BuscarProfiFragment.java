package com.example.seuprofissional.ui.BuscarProfisisonal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seuprofissional.R;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import activity.LoginUser;
import activity.MenuInicial;
import activity.VisualizarProfi;
import adapter.MyadapterPesquisa;
import model.Profissional;

public class BuscarProfiFragment extends Fragment {
    private static final String TAG = "BuscarProfiFragment";
    private Profissional profissional;
    private SearchView searchProfi;
    private RecyclerView recyclerBusca;
    private MyadapterPesquisa adapterpesquisa;
    private FirebaseFirestore firebaseFirestore;
    private ImageView visualizar;

    public BuscarProfiFragment(){
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // buscarProfiViewModel =
        // new ViewModelProvider(this).get(BuscarProfiViewModel.class);
        View view = inflater.inflate(R.layout.fragment_buscar_profi, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setHasOptionsMenu(true);



        recyclerBusca = (RecyclerView) view.findViewById(R.id.recyclerBusca);
        recyclerBusca.setLayoutManager(new LinearLayoutManager(getActivity()));


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Profissional")
                .limitToLast(50);

        FirebaseRecyclerOptions<Profissional> options =
                new FirebaseRecyclerOptions.Builder<Profissional>()
                .setQuery(query,Profissional.class)
                .build();

        //colocar os adapter
       adapterpesquisa = new MyadapterPesquisa(options);
       adapterpesquisa.startListening();
       recyclerBusca.setAdapter(adapterpesquisa);
       adapterpesquisa.notifyItemChanged(1,adapterpesquisa);





        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerBusca.setHasFixedSize(true);
        recyclerBusca = (RecyclerView) view.findViewById(R.id.recyclerBusca);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerBusca.setLayoutManager(lm);
        adapterpesquisa.startListening();
        adapterpesquisa.notifyItemChanged(0,adapterpesquisa.getItemCount());

        super.onViewCreated(view,savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        adapterpesquisa.startListening();
    }


        @Override
    public void onStop() {
        super.onStop();
        adapterpesquisa.stopListening();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sair:{
                return true;
            }
            case R.id.search:{
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //Search Menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        setHasOptionsMenu(true);
        inflater.inflate(R.menu.searchmenu,menu);
        super.onCreateOptionsMenu(menu, inflater);

       final MenuItem item = menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return false;
            }
        });



    }

    private void search(String s){
      FirebaseRecyclerOptions<Profissional> options =
                new FirebaseRecyclerOptions.Builder<Profissional>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Profissional").orderByChild("funcao")
                        .startAt(s).endAt(s+"\uf8ff"),Profissional.class).build();

        adapterpesquisa = new MyadapterPesquisa(options);
        adapterpesquisa.startListening();
        recyclerBusca.setAdapter(adapterpesquisa);




    }




}

