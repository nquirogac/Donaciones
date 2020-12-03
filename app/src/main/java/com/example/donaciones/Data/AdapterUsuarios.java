package com.example.donaciones.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donaciones.MensajesActivity;
import com.example.donaciones.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.viewHolderAdapters> {
        List<Usuario> userlist;
        Context context;
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences mpref;
    public AdapterUsuarios(List<Usuario> userlist, Context context) {
        this.userlist = userlist;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterUsuarios.viewHolderAdapters onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios,parent,false);
        viewHolderAdapters holder = new viewHolderAdapters(v);
        return holder;
    }


    @Override
    public void onBindViewHolder( @NonNull viewHolderAdapters holder, int position) {
        int i=0;
        final Vibrator vibrator =(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        final Usuario userss2 = userlist.get(1);
        while((i < 3)){
            Usuario userss = userlist.get(i);
            if(i==0){
                holder.tv_usuario.setText(userss.getNombre());
                if (userss.getEmail().equals("trilce@gmail.com")) {
                    holder.imguser.setImageResource(R.mipmap.danar_libros);
                }else{
                holder.imguser.setImageResource(R.drawable.user);
                }}
            if(i==1){
                holder.tv_usuario2.setText(userss.getNombre());
                if (userss.getEmail().equals("bancodealimentos@gmail.com")) {

                    holder.imguser2.setImageResource(R.mipmap.banco_de_alimentos);
                }else{
                    holder.imguser2.setImageResource(R.drawable.user);
                }
            }
            if(i==2) {
                holder.tv_usuario3.setText(userss.getNombre());
                if (userss.getEmail().equals("minuto@gmail.com")) {
                    holder.imguser3.setImageResource(R.mipmap.banco_de_ropa);
                } else {
                    holder.imguser3.setImageResource(R.drawable.user);
                }

            }
        i++;
        }
        holder.btnchat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpref = v.getContext().getSharedPreferences("usuario_sp",context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = mpref.edit();
                final String idchat = "AbCdE200428";
                Solicitudes sol = new Solicitudes("enviado","AbCdE200428");
                if(user.getEmail().equals("bancodealimentos@gmail.com")||user.getEmail().equals("trilce@gmail.com")||user.getEmail().equals("minuto@gmail.com")){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Users").child("Fundaciones").child(user.getUid()).child("chat").child(userss2.getId()).setValue(sol).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                }else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Users").child("Donantes").child(user.getUid()).child("chat").child(userss2.getId()).setValue(sol).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });}
                if(user.getEmail().equals("bancodealimentos@gmail.com")||user.getEmail().equals("trilce@gmail.com")||user.getEmail().equals("minuto@gmail.com")){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Users").child("Donantes").child(userss2.getId()).child("chat").child(user.getUid()).setValue(sol).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Users").child("Fundaciones").child(userss2.getId()).child("chat").child(user.getUid()).setValue(sol).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });}

                Intent intent = new Intent(v.getContext(), MensajesActivity.class);
                intent.putExtra("nombre", userss2.getNombre());
                intent.putExtra("idchat", idchat);
                intent.putExtra("iduser", userss2.getId());
                editor.putString("usuario_sp",userss2.getId());
                editor.apply();

                v.getContext().startActivity(intent);
            }


        });
    }


    @Override
    public int getItemCount() {
        return userlist.size();

    }



    public class viewHolderAdapters extends RecyclerView.ViewHolder {
        TextView tv_usuario,tv_usuario2,tv_usuario3;
        ImageView imguser,imguser2,imguser3;
        CardView cardView,cardView2,cardView3;
        Button btnchat1,btnchat2,btnchat3;

        public viewHolderAdapters(@NonNull View itemView) {
            super(itemView);
            tv_usuario = itemView.findViewById(R.id.tv_user);
            tv_usuario2 = itemView.findViewById(R.id.tv_user2);
            tv_usuario3 = itemView.findViewById(R.id.tv_users);
            imguser = itemView.findViewById(R.id.imagenUser);
            imguser2 = itemView.findViewById(R.id.imagenUser2);
            imguser3 = itemView.findViewById(R.id.img_user);
            cardView = itemView.findViewById(R.id.cardview);
            cardView2 = itemView.findViewById(R.id.cardview2);
            cardView3 = itemView.findViewById(R.id.cardview3);
            btnchat1 = itemView.findViewById(R.id.btnchat1);
            btnchat2 = itemView.findViewById(R.id.btnchat2);
            btnchat3 = itemView.findViewById(R.id.btnchat3);
        }
    }
}
