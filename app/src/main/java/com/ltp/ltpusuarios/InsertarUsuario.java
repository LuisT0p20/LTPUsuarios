package com.ltp.ltpusuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InsertarUsuario extends AppCompatActivity {
    EditText edtIUId, edtIUNombre, edtIUApellido;
    Spinner spnGenero, spnRegion, spnEspecialidad;
    Button btnIUGuardar;
    ArrayList especialidadArrayStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_usuario);
        edtIUId = findViewById(R.id.IUId);
        edtIUNombre = findViewById(R.id.IUNombres);
        edtIUApellido = findViewById(R.id.IUApellidos);
        spnGenero = findViewById(R.id.spnIUGenero);
        spnEspecialidad = findViewById(R.id.spnIUEspecialidad);
        spnRegion = findViewById(R.id.spnIURegion);
        btnIUGuardar = findViewById(R.id.btnIUGuardar);
        especialidadArrayStr = new ArrayList();
        fillSpinner();
    }

    private void fillSpinner() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.92/api/consultartabla.php?tabla=especialidad";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("especialidad");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String especialidad = jsonObject1.getString("especialidad");
                        especialidadArrayStr.add(especialidad);
                    }
                    spnEspecialidad.setAdapter(new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item,especialidadArrayStr));
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}