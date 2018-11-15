package com.example.gabriel.dcc196trabalho01;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.gabriel.dcc196trabalho01.R.layout.participantes_layout;

public class ParticipanteAdapter extends RecyclerView.Adapter<ParticipanteAdapter.ViewHolder>{

    private Cursor cursor;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public ParticipanteAdapter (Cursor cursor)
    {
        this.cursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View participanteView = inflater.inflate(R.layout.participantes_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(participanteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int idxNum = cursor.getColumnIndexOrThrow(AppContract.Participante.COLUMN_NAME_REGISTRO);
        int idxNome = cursor.getColumnIndexOrThrow(AppContract.Participante.COLUMN_NAME_NOME);

        cursor.moveToPosition(i);

        viewHolder.numParticipante.setText(String.valueOf(cursor.getInt(idxNum)));
        viewHolder.nomeParticipante.setText(cursor.getString(idxNome));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView numParticipante;
        public TextView nomeParticipante;

        public ViewHolder (final View participanteView)
        {
            super(participanteView);
            numParticipante = (TextView)participanteView.findViewById(R.id.txtParticipanteNumero);
            nomeParticipante = (TextView)participanteView.findViewById(R.id.txtParticipanteNome);
            participanteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(itemView, Integer.parseInt(numParticipante.getText().toString()));
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                listener.onItemClick(v, position);
            }
        }
    }
}
