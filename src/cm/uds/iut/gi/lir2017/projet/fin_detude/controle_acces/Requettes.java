package cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces;



public class Requettes {
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
/*
	@SuppressWarnings("null")
	public JSONObject start(String url) throws JSONException{
		JSONObject resultat = null;
		int code = 0;
		StringBuffer reponseHTTP = new StringBuffer();
		HttpClient client = new DefaultHttpClient();
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
				code = 1;				
				resultat.put("code", code);
				resultat.putOpt("reponse", jsonObject);
				
			}else{
				resultat.put("code", code);
				resultat.put("reponse", "Code = "+statusCode);
			}			
		}catch(Exception e){
			resultat.put("code", code);
			resultat.put("reponse", e.getMessage());
			return resultat;
		}
		return resultat;
	}*/
}
