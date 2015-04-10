package co.edu.udea.cmovil.gr1.yamba;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class EstadoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.new_activity_status);
        if (savedInstanceState == null) {
            EstadoFragment fragment = new EstadoFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(android.R.id.content, fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }

    }

}
