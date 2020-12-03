package com.example.donaciones.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donaciones.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterChats extends RecyclerView.Adapter<AdapterChats.viewHolderAdapter> {

    List<Chats> chatsList;
    Context context;
    public static final int MENSAJE_RIGHT =1;
    public static final int MENSAJE_LEFT =0;
    Boolean soloright = false;
    FirebaseUser fuser;

    public AdapterChats(List<Chats> chatsList, Context context) {
        this.chatsList = chatsList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType== MENSAJE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new AdapterChats.viewHolderAdapter(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new AdapterChats.viewHolderAdapter(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapter holder, int position) {
        Chats chats = chatsList.get(position);

        holder.tv_mensaje.setText(chats.getMensaje());
        if(soloright){
             final Calendar c = Calendar.getInstance();
             final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
             final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
             if(chats.getFecha().equals(dateFormat.format(c.getTime()))){
                 holder.tv_fecha.setText("Hoy "+chats.getHora());
             }else {
                 holder.tv_fecha.setText(chats.getFecha()+" "+chats.getHora());

             }
        }
    }


    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class viewHolderAdapter extends RecyclerView.ViewHolder {
        TextView tv_mensaje,tv_fecha;

        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            tv_mensaje = itemView.findViewById(R.id.tv_mensaje);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatsList.get(position).getEnvia().equals(fuser.getUid())){
            soloright = true;
            return MENSAJE_RIGHT;
        }else{
            soloright = false;
            return MENSAJE_LEFT;
        }
    }
}
