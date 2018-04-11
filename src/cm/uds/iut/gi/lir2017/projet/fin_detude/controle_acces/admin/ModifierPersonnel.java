package cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.admin;

import android.app.Activity;
import android.os.Bundle;
import cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.R;

public class ModifierPersonnel extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_personnel);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
