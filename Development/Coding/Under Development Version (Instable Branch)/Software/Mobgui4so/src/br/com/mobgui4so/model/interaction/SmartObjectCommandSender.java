/**
 * 
 */
package br.com.mobgui4so.model.interaction;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.mobgui4so.utils.HttpHelper;

/**
 * @author Ercilio Nascimento
 */
public class SmartObjectCommandSender {

	public static String sendSmartObjectCommand(String url, List<String> params) {
		String[] list = new String[params.size()];
		for (int i = 0; i < params.size(); i++) {
			list[i] = params.get(i);
		}

		return StringEscapeUtils.unescapeJava(HttpHelper.doPost(url, list));
	}

}
