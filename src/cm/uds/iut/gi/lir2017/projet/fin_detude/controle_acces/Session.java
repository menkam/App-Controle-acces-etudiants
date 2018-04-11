package cm.uds.iut.gi.lir2017.projet.fin_detude.controle_acces;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Session extends Activity{
	private static String session_info_user;
	private static String id_connect;
	private static String nbr_perso;
	private static String infoScan;
	
	/*
	 * Getter
	 */
	public static String getInfoScan() { return infoScan; }
	public static void setInfoScan(String infoScan) { Session.infoScan = infoScan; }
	public static String getSession_info_user() { return session_info_user; }
	public static void setSession_info_user(String session_info_user) { Session.session_info_user = session_info_user; }
	public static String getId_connect() { return id_connect; }
	
	/*
	 * Setter
	 */
	public static void setId_connect(String id_connect) { Session.id_connect = id_connect; }
	public static String getNbr_perso() { return nbr_perso; }
	public static void setNbr_perso(String nbr_perso) { Session.nbr_perso = nbr_perso; }
	
	/*
	 * fonction 
	 */
	
	//redimentionner une image
	public Bitmap redimentionnerBitmap(Bitmap bm, int w, int h){
		int width = bm.getWidth();
		int height = bm.getHeight();
		int newWidth = w;
		int newHeight = h;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
 
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
 
		return resizedBitmap;
	}
	
}
