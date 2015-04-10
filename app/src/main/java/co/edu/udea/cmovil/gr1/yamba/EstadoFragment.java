package co.edu.udea.cmovil.gr1.yamba;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EstadoFragment";
    private Button buttonPublicar;
    private EditText publicacion;
    private TextView caracteresRestantes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstadoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstadoFragment newInstance(String param1, String param2) {
        EstadoFragment fragment = new EstadoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EstadoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_estado, container, false);

        buttonPublicar = (Button) view.findViewById(R.id.buttonPublicar);
        publicacion = (EditText) view.findViewById(R.id.publicacion);
        caracteresRestantes = (TextView) view.findViewById(R.id.caracteresRestantes);

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
                else
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

    }

    private final class Hilo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            YambaClient yambaCloud = new YambaClient("student","password");
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
            Toast.makeText(EstadoFragment.this.getActivity(), resultado, Toast.LENGTH_LONG).show();
            if(resultado == "Publicado"){
                publicacion.setText("");
            }
        }
    }
}
