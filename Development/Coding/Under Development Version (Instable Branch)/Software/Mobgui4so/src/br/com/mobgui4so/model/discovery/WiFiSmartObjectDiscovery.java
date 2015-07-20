/**
 * 
 */
package br.com.mobgui4so.model.discovery;

import java.io.IOException;

import br.com.mobgui4so.model.interaction.SmartObjectListRequester;
import br.com.mobgui4so.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */
public class WiFiSmartObjectDiscovery extends BaseSmartObjectDiscovery {

	@Override
	public SmartObjectList execute(String[] form) throws IOException {
		return SmartObjectListRequester.getSmartObjectList(form);
	}

}
