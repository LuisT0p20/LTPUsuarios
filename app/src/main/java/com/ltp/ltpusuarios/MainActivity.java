package com.ltp.ltpusuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {
    EditText edtCodigo;
    TextView txtNombres,txtapellidos,txtgenero,txtregion,txtespecialidad;
    Button btnBuscar;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtCodigo = findViewById(R.id.camDoc);
        txtNombres=findViewById(R.id.txtNombre);
        txtapellidos = findViewById(R.id.txtApellido);
        txtgenero = findViewById(R.id.txtGenero);
        txtregion = findViewById(R.id.txtRegion);
        txtespecialidad = findViewById(R.id.txtEspecialidad);
        btnBuscar = findViewById(R.id.btnConsutar);
        requestQueue = Volley.newRequestQueue(this);
        btnBuscar.setOnClickListener(this);
    }

    private void cargarWebService(String documento) {
        String url = "http://192.168.0.92/api/consultarusuario.php?id=" + documento;
        url = url.replace(" ","%20");//si hay espacios se reemplazan por %20
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        //Respuesta exitosa
        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            txtNombres.setText(jsonObject.optString("nombres"));
            txtapellidos.setText(jsonObject.optString("apellidos"));
            txtgenero.setText(jsonObject.optString("genero"));
            txtregion.setText(jsonObject.optString("region"));
            txtespecialidad.setText(jsonObject.optString("especialidad"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Error en la respuesta
        Log.i("Error",error.toString());
    }

    @Override
    public void onClick(View v) {
        cargarWebService(edtCodigo.getText().toString());
    }
}