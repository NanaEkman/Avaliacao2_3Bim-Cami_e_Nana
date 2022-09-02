package br.edu.ifsp.scl.ads.pdm.avaliacao2_3bim_cami_e_nana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DigitaActivity extends AppCompatActivity {

    private EditText txtProduto;
    private EditText txtMarca;
    private EditText txtQuant;
    private Button btnInsere;
    private Button btnCancela;

    SQLiteDatabase bd;
    private AdapterItem adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digita);

        txtProduto = findViewById(R.id.txtProduto);
        txtMarca = findViewById(R.id.txtMarca);
        txtQuant = findViewById(R.id.txtQuant);
        btnCancela = findViewById(R.id.btnCancela);
        btnInsere = findViewById(R.id.btnInsere);

        btnInsere.setOnClickListener(new EscutadorBotaoInsere());
        btnCancela.setOnClickListener(new EscutadorBotaoCancela());

        bd = openOrCreateDatabase( "listacompras", MODE_PRIVATE, null );


    }

    class EscutadorBotaoInsere implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            String produto, marca, quant;


            produto = txtProduto.getText().toString();
            marca = txtMarca.getText().toString();
            quant = txtQuant.getText().toString();

            String cmd = "INSERT INTO compras (produto, marca, quantidade, comprado) VALUES ('";
            cmd = cmd + produto;
            cmd = cmd + "', '";
            cmd = cmd + marca;
            cmd = cmd + "', '";
            cmd = cmd + quant;
            cmd = cmd + "', '";
            cmd = cmd + "não";
            cmd = cmd + "')";
o:
            bd.execSQL( cmd );
            // Limpando a interface para a próxima digitação:
            txtProduto.setText("");
            txtMarca.setText("");
            txtQuant.setText("");

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();

        }
    }

    class EscutadorBotaoCancela implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            Intent i = new Intent();

            setResult(RESULT_CANCELED, i);

            finish();

        }
    }

}