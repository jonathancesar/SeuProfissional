package activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seuprofissional.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import adapter.MyadapterVisuProfi;
import model.Profissional;

public class VisualizarProfi extends Fragment {
    RecyclerView recyclervisuProfi;
    MyadapterVisuProfi visuAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_visualizar_profi, container, false);

        recyclervisuProfi = (RecyclerView) view.findViewById(R.id.recyclervisuProfi);
        recyclervisuProfi.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Profissional")
                .limitToLast(50);

        FirebaseRecyclerOptions<Profissional> options =
                new FirebaseRecyclerOptions.Builder<Profissional>()
                        .setQuery(query,Profissional.class)
                        .build();

        visuAdapter = new MyadapterVisuProfi(options);
        visuAdapter.startListening();
        recyclervisuProfi.setAdapter(visuAdapter);
        visuAdapter.notifyItemChanged(1,visuAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclervisuProfi.setHasFixedSize(true);
        recyclervisuProfi = (RecyclerView) view.findViewById(R.id.recyclervisuProfi);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclervisuProfi.setLayoutManager(llm);
        visuAdapter.startListening();
        visuAdapter.notifyItemChanged(0,visuAdapter.getItemCount());

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        visuAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        visuAdapter.stopListening();
    }


}