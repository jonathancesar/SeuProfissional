package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seuprofissional.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import model.Profissional;

public class AdapterProfisisonal extends FirebaseRecyclerAdapter<Profissional,AdapterProfisisonal.myviewholder> {



    public AdapterProfisisonal(@NonNull FirebaseRecyclerOptions<Profissional> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Profissional model) {
        holder.funcao.setText(model.getFuncao());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_busca,parent,false);
      return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView funcao,email;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

           // funcao = (TextView) itemView.findViewById(R.id.funcao);
        }
    }

}
