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
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Connexion extends Activity implements OnClickListener {
	
	private Button btn_send,btn_reset;
	private EditText login, pass;
	private ProgressBar progressBarConnexion;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        StrictMode.ThreadPolicy threadPolicy = new
        		StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        login = (EditText) findViewById(R.id.login);
        pass = (EditText) findViewById(R.id.password);
        progressBarConnexion = (ProgressBar) findViewById(R.id.progressBarConnexion);
        
        progressBarConnexion.setVisibility(View.GONE);
        btn_send.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }   

	@Override
	public void onClick(View arg0) {
		
		/*
		 * traitement apères l'action de click sur le bouton btn_send
		 */
		if(arg0.getId()==R.id.btn_send){
			
			progressBarConnexion.setVisibility(View.VISIBLE);
						
			StringBuffer reponseHTTP = new StringBuffer();
			HttpClient client = new DefaultHttpClient();
			String url = "http://192.168.8.100/PFE_Controle_Acces/Android/verifier_login.php?login="+login.getText().toString()+"&pass="+pass.getText().toString();
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
						String nom = jsonObject.getString("nom");
						String prenom = jsonObject.getString("prenom");
						Session.setId_connect(jsonObject.getString("id"));
						Session.setSession_info_user("Connecter en tant que : "+nom+" "+prenom);
						Toast.makeText(getApplicationContext(), "Bienvenu M(Mme). "+nom+" "+prenom, Toast.LENGTH_LONG).show();
						Intent i = new Intent("cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.SCANNER");
				        startActivity(i);
					}
					else if(jsonObject.getInt("statut") == 0){
						Toast.makeText(getApplicationContext(), "Login / Pass incorrect !!!", Toast.LENGTH_LONG).show();
					}
					else if(jsonObject.getInt("statut") == 2){
						Session.setId_connect(jsonObject.getString("id"));
						Toast.makeText(getApplicationContext(), "connecter en tant que admin", Toast.LENGTH_LONG).show();
						Intent i = new Intent("cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.admin.ACCUEILLE");
						startActivity(i);
					}
					
				}else{
					Toast.makeText(getApplicationContext(), "Code = "+statusCode, Toast.LENGTH_LONG).show();
					progressBarConnexion.setVisibility(View.GONE);
				}			
			}catch(Exception e){
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				progressBarConnexion.setVisibility(View.GONE);
			}
			 
		}
		
		/*
		 * traitement de l'action du click sur le bouton btn_reset
		 */
		if(arg0.getId()==R.id.btn_reset){
			login.setText("");
			pass.setText("");
			Toast toast = Toast.makeText(getApplicationContext(), "Formulaire Réinitialiser", Toast.LENGTH_SHORT);
			toast.show();
		}
	}    
}
