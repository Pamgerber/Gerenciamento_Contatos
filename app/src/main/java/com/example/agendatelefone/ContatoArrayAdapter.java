package com.example.agendatelefone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.agendatelefone.dominio.entidades.Contato;

public class ContatoArrayAdapter extends ArrayAdapter<Contato> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public ContatoArrayAdapter(Context context, int resource)
    {
        super(context, resource);
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource= resource;
        this.context= context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view= null;
        ViewHolder viewHolder= null;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();

            view= inflater.inflate(resource, parent, false);

            viewHolder.txtCor= (TextView)view.findViewById(R.id.txtCor);
            viewHolder.txtNome= (TextView)view.findViewById(R.id.txtNome);

            view.setTag(viewHolder);

            convertView= view;

        }else
        {
            viewHolder= (ViewHolder)convertView.getTag();
            view= convertView;
        }

        Contato contato= getItem(position);

        if(contato.getNome().toUpperCase().startsWith("A") )
        viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.verde));
        else
        if(contato.getNome().toUpperCase().startsWith("P") )
            viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.azul));
            else
            viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.violeta));

        viewHolder.txtNome.setText(contato.getNome());

        return view;

    }

    static class ViewHolder
    {
        TextView txtCor;
        TextView txtNome;

    }
}
