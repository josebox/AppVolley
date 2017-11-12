package com.example.jose.appvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.android.internal.util.Predicate;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    WebView wb_prediccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue rq= Volley.newRequestQueue(this);
        String url ="http://www.aemet.es/xml/municipios/localidad_28079.xml";
        wb_prediccion = (WebView)findViewById(R.id.wb_Prediccion);
        Response.Listener oyente=new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                String xml_respuesta=(String)o;
                String titulo = "Tabla Prediciones";
                ArrayList<Prediccion> predicciones = ParsearXml.parsear(xml_respuesta);
                String encabezado[] = {"Fecha","Maxima","Minima"};
                ArrayList <String[]> filas = new ArrayList<>();
                for (Prediccion p : predicciones){
                    String [] fila = new String[3];
                    fila[0] = p.getFecha();
                    fila[1] = p.gettMaxima();
                    fila[2] = p.gettMinima();
                    filas.add(fila);
                }
                String tablaHtml = TablaHtml.generarHtml(titulo,encabezado,filas);
                wb_prediccion.loadData(tablaHtml,"text/html",null);
              //  Log.v("XML",xml_respuesta);
            }
        };
        Response.ErrorListener oyente_fallo=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        };
        StringRequest respuesta=new StringRequest(url, oyente, oyente_fallo);
        rq.add(respuesta);
    }
}
