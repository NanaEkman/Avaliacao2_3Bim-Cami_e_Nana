package br.edu.ifsp.scl.ads.pdm.avaliacao2_3bim_cami_e_nana;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterItem extends CursorAdapter {

    private Context context;

    public AdapterItem(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(context);
        View itemView = li.inflate(R.layout.item_lista, parent, false);

        return itemView;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView lblProduto = view.findViewById(R.id.lblProduto);
        TextView lblMarca = view.findViewById(R.id.lblMarca);
        TextView lblQuant = view.findViewById(R.id.lblQuant);
        TextView lblComprado = view.findViewById(R.id.lblComprado);

        String produto = cursor.getString( cursor.getColumnIndex("produto") );
        String marca = cursor.getString( cursor.getColumnIndex("marca") );
        String quantidade = cursor.getString( cursor.getColumnIndex("quantidade") );
        String comprado = cursor.getString( cursor.getColumnIndex("comprado") );

        lblProduto.setText(produto);
        lblMarca.setText(marca);
        lblQuant.setText(quantidade);

        if(comprado.equals("sim")){
            lblComprado.setText("* COMPRADO *");
        }else if(comprado.equals("não")){
            lblComprado.setText("");
        }


    }

}
