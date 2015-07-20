/**
 * 
 */
package br.com.mobgui4so.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Ercilio Nascimento
 */
public class HttpHelper {

	public static String doGet(String url, String charset) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		InputStream in = conn.getInputStream();
		String s = IOUtils.toString(in, charset);
		in.close();
		conn.disconnect();
		return s;
	}

	public static String doPost(String url, String... params) {
		StringBuilder ack = new StringBuilder();
		StringBuilder data = new StringBuilder();
		try {
			for (int i = 0; i < params.length; i++) {
				data.append(URLEncoder.encode(params[i], "UTF-8") + "=" + URLEncoder.encode(params[++i], "UTF-8") + "&");
			}

			URLConnection conn;
			URL u = new URL(url);
			conn = u.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			((HttpURLConnection) conn).setRequestMethod("POST");
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data.toString());
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				ack.append(line);
			}
			wr.close();
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ack.toString();
	}

}
