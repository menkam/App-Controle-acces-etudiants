package cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.admin;

import cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccueilleAdmin extends Activity implements OnClickListener{
	
	private Button btn_update_etudiant,btn_update_personnel;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueille_admin);
        
        btn_update_etudiant = (Button) findViewById(R.id.btn_update_etudiant);
        btn_update_personnel = (Button) findViewById(R.id.btn_update_personnel);
        
        btn_update_etudiant.setOnClickListener(this);
        btn_update_personnel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_update_etudiant){
			demarrer("MODIFIERETUDIANT");
		}
		if(v.getId() == R.id.btn_update_personnel){
			demarrer("MODIFIERPERSONNEL");
		}		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private void demarrer(String string) {
		Intent i = new Intent("cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.admin."+string);
		startActivity(i);
		
	}
}
