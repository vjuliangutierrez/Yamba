package co.edu.udea.cmovil.gr1.yamba;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StatusFragment";
    private Button buttonPublicar;
    private EditText publicacion;
    private TextView caracteresRestantes;
    SharedPreferences prefs;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        buttonPublicar = (Button) view.findViewById(R.id.buttonPublicar);
        publicacion = (EditText) view.findViewById(R.id.publicacion);
        caracteresRestantes = (TextView) view.findViewById(R.id.caracteresRestantes);

        buttonPublicar.setEnabled(false);
        buttonPublicar.setOnClickListener(this);

        publicacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int contador = 140 - publicacion.length();
                caracteresRestantes.setText(Integer.toString(contador));
                if(contador < 10)
                    caracteresRestantes.setTextColor(Color.RED);
                else if(contador >= 10  && contador <40)
                    caracteresRestantes.setTextColor(Color.rgb(255,165,0));
                else if(contador < 140)
                    buttonPublicar.setEnabled(true);
                else
                    buttonPublicar.setEnabled(false);
                    caracteresRestantes.setTextColor(Color.GREEN);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        String estado = publicacion.getText().toString();
        Log.d(TAG, "Publicacion: " + estado);

        new Hilo().execute(estado);

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(publicacion.getWindowToken(), 0);
    }

    private final class Hilo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String usuario = prefs.getString("usuario", "");
            String contraseña = prefs.getString("contraseña", "");
            if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contraseña)) { //
                getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                return "Por favor actualiza tu usuario y contraseña";
            }

            YambaClient yambaCloud = new YambaClient(usuario, contraseña);
            try{
                yambaCloud.postStatus(params[0]);
                return "Publicado";
            }catch(YambaClientException e){
                e.printStackTrace();
                return "Fallo al publicar en el servicio de Yamba";
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            Toast.makeText(StatusFragment.this.getActivity(), resultado, Toast.LENGTH_LONG).show();
            if(resultado.equals("Publicado")){
                publicacion.setText("");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
