package cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.admin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.R;
import cm.uds.iut.gi.lir2017.projet.fin_detude.zxing.integration.android.IntentIntegrator;
import cm.uds.iut.gi.lir2017.projet.fin_detude.zxing.integration.android.IntentResult;

public class ModifierEtudiant extends Activity implements OnClickListener{
	private Button btn_rechercher_etudiant,btn_getCodebar_etudiant,
	btn_reset_student,btn_save_student,btn_ennuler_regist_student;
	private EditText et_recherche,et_nom_etudiant,et_prenom_etudiant,et_dateNais_etudiant,
	et_telephon_etudiant, et_email_etudiant, et_condeBare_etudiant,sexe;
	private ImageView iv_photo_etudiant;
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_etudiant);
        
        btn_rechercher_etudiant = (Button) findViewById(R.id.btn_rechercher_etudiant);
        btn_getCodebar_etudiant = (Button) findViewById(R.id.btn_getCodebar_etudiant);
        btn_reset_student = (Button) findViewById(R.id.btn_reset_student);
        btn_save_student = (Button) findViewById(R.id.btn_save_student);
        btn_ennuler_regist_student = (Button) findViewById(R.id.btn_ennuler_regist_student);
        
        et_recherche = (EditText) findViewById(R.id.et_recherche);
        et_nom_etudiant = (EditText) findViewById(R.id.et_nom_etudiant);
        et_prenom_etudiant = (EditText) findViewById(R.id.et_prenom_etudiant);
        et_dateNais_etudiant = (EditText) findViewById(R.id.et_dateNais_etudiant);
        et_telephon_etudiant = (EditText) findViewById(R.id.et_telephon_etudiant);
        et_email_etudiant = (EditText) findViewById(R.id.et_email_etudiant);
        et_condeBare_etudiant = (EditText) findViewById(R.id.et_condeBare_etudiant);
        
        iv_photo_etudiant = (ImageView) findViewById(R.id.iv_photo_etudiant);
        
        btn_rechercher_etudiant.setOnClickListener(this);
        btn_getCodebar_etudiant.setOnClickListener(this);
        btn_reset_student.setOnClickListener(this);
        btn_save_student.setOnClickListener(this);
        btn_ennuler_regist_student.setOnClickListener(this);
        
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.btn_rechercher_etudiant){

			StringBuffer reponseHTTP = new StringBuffer();
			HttpClient client = new DefaultHttpClient();
			String url = "http://192.168.8.100/PFE_Controle_Acces/Android/admin/update_etudiant.php?update=1&matricule="+et_recherche.getText().toString();
	        HttpGet httpGet = new HttpGet(url);
			try{
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if(statusCode == 200){
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null){
						reponseHTTP.append(line);					
					}
					JSONObject jsonObject = new JSONObject(reponseHTTP.toString());
					
					if(jsonObject.getInt("statut") == 1){
						et_nom_etudiant.setText(jsonObject.getString("nom"));
						et_prenom_etudiant.setText(jsonObject.getString("prenom"));
						et_dateNais_etudiant.setText(jsonObject.getString("date_naiss"));
						et_telephon_etudiant.setText(jsonObject.getString("telephone"));
						et_email_etudiant.setText(jsonObject.getString("email"));
						et_condeBare_etudiant.setText(jsonObject.getString("info_codebar"));
						if(jsonObject.getString("photo") != null){
							byte[] decodedString = Base64.decode(jsonObject.getString("photo"), Base64.DEFAULT);
							Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
							iv_photo_etudiant.setImageBitmap(bm);
						}						
						
					}if(jsonObject.getInt("statut") == 0){
						Toast.makeText(getApplicationContext(), jsonObject.getInt("message"), Toast.LENGTH_LONG).show();
					}
					
					
				}else{
					Toast.makeText(getApplicationContext(), "Code = "+statusCode, Toast.LENGTH_LONG).show();
				}			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "erreur de connexion "+e1.getMessage(), Toast.LENGTH_LONG).show();
			}					
			
		}
		
		/*
		 * getCodebar_etudiant
		 */
		if(v.getId() == R.id.btn_getCodebar_etudiant){
			
			//scan
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			
		}
		
		/*
		 * reset_student
		 */
		if(v.getId() == R.id.btn_reset_student){
			initialiser_formulaire();
			Toast toast = Toast.makeText(getApplicationContext(), "Formulaire Réinitialiser", Toast.LENGTH_SHORT);
			toast.show();
		}
		
		/*
		 * save_student
		 */
		if(v.getId() == R.id.btn_save_student){
			
			StringBuffer reponseHTTP = new StringBuffer();
			HttpClient client = new DefaultHttpClient();
			String url = "http://192.168.8.100/PFE_Controle_Acces/Android/admin/update_etudiant.php?save=1";//&" +
					/*"matricule="+et_recherche.getText().toString()+"&" +
					"nom="+et_nom_etudiant.getText().toString()+"&" +
					"prenom="+et_prenom_etudiant.getText().toString()+"&" +
					"date_naiss="+et_dateNais_etudiant.getText().toString()+"&" +
					"telephone="+et_telephon_etudiant.getText().toString()+"&" +
					"email="+et_email_etudiant.getText().toString()+"&" +
					"info_codebar="+et_condeBare_etudiant.getText().toString();*/
	        HttpGet httpGet = new HttpGet(url);
			try{
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if(statusCode == 200){
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null){
						reponseHTTP.append(line);					
					}
					JSONObject jsonObject = new JSONObject(reponseHTTP.toString());
					
					if(jsonObject.getInt("statut") == 1){
						Toast.makeText(getApplicationContext(), "modification terminer avec succès", Toast.LENGTH_LONG).show();
						initialiser_formulaire();
					}if(jsonObject.getInt("statut") == 0){
						Toast.makeText(getApplicationContext(), jsonObject.getInt("message"), Toast.LENGTH_LONG).show();
					}					
					
				}else{
					Toast.makeText(getApplicationContext(), "Code = "+statusCode, Toast.LENGTH_LONG).show();
				}			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "erreur de connexion "+e1.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		
		/*
		 * ennuler_regist_student
		 */
		if(v.getId() == R.id.btn_ennuler_regist_student){
			initialiser_formulaire();
			Toast toast = Toast.makeText(getApplicationContext(), "Enregistrement annulé !!!", Toast.LENGTH_SHORT);
			toast.show();
			Intent i = new Intent("cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.admin.ACCUEILLE");
			startActivity(i);
			finish();
		}
		
	}

	public void initialiser_formulaire() {
		et_nom_etudiant.setText("");
		et_prenom_etudiant.setText("");
		et_dateNais_etudiant.setText("");
		et_telephon_etudiant.setText("");
		et_email_etudiant.setText("");
		et_condeBare_etudiant.setText("");
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			//we have a result
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();			
			
			if(scanFormat != null){
				et_condeBare_etudiant.setText(scanContent);
				
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
			    toast.show();
			}			
			
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(),  "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}