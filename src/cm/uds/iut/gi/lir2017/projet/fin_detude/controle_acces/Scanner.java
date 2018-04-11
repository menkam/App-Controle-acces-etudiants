 
package cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces;
 
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
import cm.uds.iut.gi.lir2017.projet.fin_detude.zxing.integration.android.IntentIntegrator;
import cm.uds.iut.gi.lir2017.projet.fin_detude.zxing.integration.android.IntentResult;

 
public class Scanner extends Activity implements OnClickListener{
 
	private Button scanBtn,btnAnalyseScan;
	private TextView formatTxt, contentTxt,info_user;
 
 
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
 
 
	    setContentView(R.layout.scanner);
 
	    // fixation de variables aux elements
	    scanBtn = (Button)findViewById(R.id.btn_scan);
	    btnAnalyseScan = (Button)findViewById(R.id.btnAnalyseScan);
	    formatTxt = (TextView)findViewById(R.id.tvScanFormat);
	    contentTxt = (TextView)findViewById(R.id.tvScanContent);
	    info_user = (TextView)findViewById(R.id.info_user);   
	    
	    info_user.setText(Session.getSession_info_user());
        info_user.setBackgroundColor(Color.BLUE);
        
        btnAnalyseScan.setVisibility(View.GONE);
 
	    scanBtn.setOnClickListener(this);
	    btnAnalyseScan.setOnClickListener(this);
	  }
 
 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_scan){
			//scan
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		}
		if(v.getId() == R.id.btnAnalyseScan){
			//Session.setInfoScan("12838_CM-UDS-14IUT0004");
			Intent i = new Intent("cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces.ACCUEILLE");
	        startActivity(i);
		}
	}
 
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			//we have a result
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();			
			
			if(scanFormat != null){
				formatTxt.setText("FORMAT: " + scanFormat);
				contentTxt.setText("CONTENT: " + scanContent);
				Session.setInfoScan(scanContent);
				btnAnalyseScan.setVisibility(View.VISIBLE);
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
			    toast.show();
			}			
			
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		            "No scan data received!", Toast.LENGTH_SHORT);
		        toast.show();
		}
	}
 
}