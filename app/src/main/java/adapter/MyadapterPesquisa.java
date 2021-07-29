package adapter;

import android.content.Intent;
import android.net.Uri;
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


import java.util.HashMap;
import java.util.Map;

import model.Profissional;

public class MyadapterPesquisa extends FirebaseRecyclerAdapter<Profissional,MyadapterPesquisa.myviewholder> {
   // private MyadapterVisuProfi pesquisa;


    public MyadapterPesquisa(@NonNull FirebaseRecyclerOptions<Profissional> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyadapterPesquisa.myviewholder holder, int position, @NonNull Profissional model) {
        holder.BtxtNomeProfi.setText(model.getNomeProfi());
        holder.BtxtFuncao.setText(model.getFuncao());
        holder.BtxfoneProfi.setText(model.getFoneProfi());
        holder.BtxDescricao.setText(model.getDescricao());



       holder.BtxfoneProfi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=+55"+holder.BtxfoneProfi +"&text=" + "Olá!");
               Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
               //startActivity(sendIntent);


           }
       });



    }

    @NonNull
    @Override
    public MyadapterPesquisa.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_profi,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView BtxtNomeProfi,BtxtFuncao,BtxfoneProfi, BtxDescricao;
        ImageView visualizar;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            BtxtNomeProfi = (TextView) itemView.findViewById(R.id.BtxtNomeProfi);
            BtxtFuncao = (TextView) itemView.findViewById(R.id.BtxtFuncao);
            BtxfoneProfi = (TextView) itemView.findViewById(R.id.BtxFoneProfi);
            BtxDescricao = (TextView) itemView.findViewById(R.id.BtxDescricao);

            visualizar = (ImageView) itemView.findViewById(R.id.visualizar);





        }
    }


}