package cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces;

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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Accueille extends Activity implements OnClickListener{
	

	private TextView message,matricule,nom,prenom,matiere,regime,numero,nbrPerso,info_user;
	private String id_connect,infoScan;
	private Button btn_retourScan;
	private ImageView image;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueille);
        
        
        message = (TextView)findViewById(R.id.tv_message);
        matricule = (TextView)findViewById(R.id.tv_matricule);
        nom = (TextView)findViewById(R.id.tv_nom);
        prenom = (TextView)findViewById(R.id.tv_prenom);
        matiere = (TextView)findViewById(R.id.tv_matiere);
        regime = (TextView)findViewById(R.id.tv_regime);
        numero = (TextView)findViewById(R.id.tv_numTable); 
        nbrPerso = (TextView)findViewById(R.id.tv_nbreEtudiant);  
        info_user = (TextView)findViewById(R.id.info_user);  
        btn_retourScan = (Button)findViewById(R.id.btn_retourScan);
        image = (ImageView)findViewById(R.id.image);        
        
        
        info_user.setText(Session.getSession_info_user());
        info_user.setBackgroundColor(Color.BLUE);
        
        //recupération de l'identifiant du connecté          
        id_connect = Session.getId_connect();
        
        //récupération des information du scanner de cobe bar
        infoScan = Session.getInfoScan();
        
        btn_retourScan.setOnClickListener(this);
        
        StringBuffer reponseHTTP = new StringBuffer();
		HttpClient client = new DefaultHttpClient();
        String url = "http://192.168.8.100/PFE_Controle_Acces/Android/verifier_etudiant.php?info_carte="+infoScan+"&=id_perso="+id_connect;
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
					message.setBackgroundColor(Color.GREEN);
					message.setText(R.string.message3);
					matricule.setText("Matricule : "+jsonObject.getString("matricule"));
					nom.setText("Nom : "+jsonObject.getString("nom"));
					prenom.setText("Prenom : "+jsonObject.getString("prenom"));
					matiere.setText("matiere : "+jsonObject.getString("matiere"));
					regime.setText("Regime : "+jsonObject.getString("regime"));
					numero.setText("Numero de Table : "+jsonObject.getString("numero"));
					Session.setNbr_perso(jsonObject.getString("nbre_perso"));
					//numero.setText("Numero de Table : "+jsonObject.("photo"));
					
					byte[] decodedString = Base64.decode(jsonObject.getString("photo"), Base64.DEFAULT);
					Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
					image.setImageBitmap(bm);
					
					nbrPerso.setText(Session.getNbr_perso());
			        nbrPerso.setBackgroundColor(Color.YELLOW);
					
				}else if(jsonObject.getInt("statut") == 0){
					message.setBackgroundColor(Color.RED);
					message.setText(R.string.message1);
					
					matricule.setText("Matricule : ");
					nom.setText("Nom : ");
					prenom.setText("Prenom : ");
					matiere.setText("matiere : ");
					regime.setText("Regime : ");
					numero.setText("Numero de Table : ");
					
				}else if(jsonObject.getInt("statut") == 2){
					message.setBackgroundColor(Color.CYAN);
					message.setText(R.string.message2);
					matricule.setText("Matricule : "+jsonObject.getString("matricule"));
					nom.setText("Nom : "+jsonObject.getString("nom"));
					prenom.setText("Prenom : "+jsonObject.getString("prenom"));
					matiere.setText("matiere : "+jsonObject.getString("matiere"));
					regime.setText("Regime : "+jsonObject.getString("regime"));
					numero.setText("Numero de Table : "+jsonObject.getString("numero"));
					Session.setNbr_perso(jsonObject.getString("nbre_perso"));
					byte[] decodedString = Base64.decode(jsonObject.getString("photo"), Base64.DEFAULT);
					Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
					image.setImageBitmap(bm);
					
					nbrPerso.setText(Session.getNbr_perso());
			        nbrPerso.setBackgroundColor(Color.YELLOW);
				}
				
				
			}else{
				Toast.makeText(getApplicationContext(), "Code = "+statusCode, Toast.LENGTH_LONG).show();
			}			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "erreur de connexion "+e1.getMessage(), Toast.LENGTH_LONG).show();
		}
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accueil, menu);
        return true;
    }

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.btn_retourScan){
			Intent i = new Intent("cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.SCANNER");
	        startActivity(i);
	        finish();
		}		
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
