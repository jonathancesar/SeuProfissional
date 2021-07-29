package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seuprofissional.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import model.Profissional;

public class MyadapterVisuProfi extends FirebaseRecyclerAdapter<Profissional,MyadapterVisuProfi.myviewholder> {


    public MyadapterVisuProfi(@NonNull FirebaseRecyclerOptions<Profissional> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Profissional model) {
        holder.visuNomeProfi.setText(model.getNomeProfi());
        holder.visuFuncao.setText(model.getFuncao());
        holder.visuFoneProfi.setText(model.getFoneProfi());

        holder.visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.visuNomeProfi.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_cadastro_profisisonal))
                        .setExpanded(true,1500)
                        .create();

                View myview = dialogPlus.getHolderView();
                final TextView nome = myview.findViewById(R.id.visuNomeProfi);
                final TextView fun = myview.findViewById(R.id.visuFuncao);
                final TextView fon = myview.findViewById(R.id.visuFoneProfi);

                nome.setText(model.getNomeProfi());
                fun.setText(model.getFuncao());
                fon.setText(model.getFoneProfi());
                dialogPlus.show();
                dialogPlus.dismiss();


            }
        });



    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_visu_profi,parent,false);
        return new myviewholder(view);

    }


    public class myviewholder extends RecyclerView.ViewHolder {
        TextView visuFuncao,visuNomeProfi,visuFoneProfi;
        ImageView visualizar;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            visuFuncao = (TextView) itemView.findViewById(R.id.visuFuncao);
            visuNomeProfi = (TextView) itemView.findViewById(R.id.visuNomeProfi);
            visuFoneProfi = (TextView) itemView.findViewById(R.id.visuFoneProfi);
            visualizar = (ImageView) itemView.findViewById(R.id.visualizar);

        }

    }
}
