package br.edu.ifsp.scl.ads.pdm.avaliacao2_3bim_cami_e_nana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Camila Devita Basaglia - SC3010058 e Nana de Souza Ekman Simões - SC3010414

    private Button btnAdiciona;
    private ListView listaCompras;
    private AdapterItem adaptador;

    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdiciona = findViewById(R.id.btnAdiciona);
        btnAdiciona.setOnClickListener(new EscutadorBotaoAdiciona());
        listaCompras = findViewById(R.id.listaCompras);

        listaCompras.setAdapter(adaptador);

        listaCompras.setOnItemClickListener(new EscutadorLista());

        listaCompras.setOnItemLongClickListener(new EscutadorLista());

        bd = openOrCreateDatabase( "listacompras", MODE_PRIVATE, null );

        String cmd;

        cmd = "CREATE TABLE IF NOT EXISTS compras ";
        cmd = cmd + "(id INTEGER PRIMARY KEY AUTOINCREMENT, produto VARCHAR, marca VARCHAR, quantidade VARCHAR, comprado VARCHAR)";
        bd.execSQL( cmd );

        Cursor cursor = bd.rawQuery( "SELECT _rowid_ _id, id, produto, marca, quantidade, comprado FROM compras", null );

        adaptador = new AdapterItem( this, cursor );

        listaCompras.setAdapter(adaptador);

    }

    class EscutadorBotaoAdiciona implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), DigitaActivity.class);
            startActivityForResult(i, 1);

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i) {

        super.onActivityResult(requestCode, resultCode, i);

        if(resultCode == RESULT_OK){

            Cursor cursor = bd.rawQuery( "SELECT _rowid_ _id, id, produto, marca, quantidade, comprado FROM compras", null );

            adaptador.changeCursor(cursor);

            listaCompras.setAdapter(adaptador);

        }

    }

    private class EscutadorLista implements AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            String comprado = "";
            Cursor c = (Cursor) adaptador.getItem(i);
            int rowid = c.getInt(c.getColumnIndex("_id"));
            String cmd = "SELECT comprado FROM compras WHERE _rowid_ = " + rowid + ";";

            Cursor resposta = bd.rawQuery( cmd, null );

            while(resposta.moveToNext()){
                comprado = resposta.getString(resposta.getColumnIndex("comprado"));


                if(comprado.equals("não")){

                    bd.execSQL("UPDATE compras SET comprado='sim' WHERE _rowid_ = " + rowid + ";");
                    Toast.makeText(MainActivity.this, "Item comprado!", Toast.LENGTH_SHORT).show();
                }else if(comprado.equals("sim")){
                    bd.execSQL("UPDATE compras SET comprado='não' WHERE _rowid_ = " + rowid + ";");
                    Toast.makeText(MainActivity.this, "Compra cancelada!", Toast.LENGTH_SHORT).show();
                }
            }

            Cursor cursor = bd.rawQuery( "SELECT _rowid_ _id, id, produto, marca, quantidade, comprado FROM compras", null );

            adaptador.changeCursor(cursor);

            listaCompras.setAdapter(adaptador);


        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            Cursor c = (Cursor) adaptador.getItem(i);
            int rowid = c.getInt(c.getColumnIndex("_id"));
            String cmd = "DELETE FROM compras WHERE _rowid_ = " + rowid + ";";
            bd.execSQL(cmd);

            Cursor cursor = bd.rawQuery( "SELECT _rowid_ _id, id, produto, marca, quantidade, comprado FROM compras", null );

            adaptador.changeCursor(cursor);

            listaCompras.setAdapter(adaptador);

            return true;
        }
    }
}