/**
 * 
 */
package br.com.mobgui4so.model.discovery;

import java.util.ArrayList;
import java.util.List;

import br.com.mobgui4so.model.pojo.SmartObject;
import br.com.mobgui4so.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */
public class TestSmartObjectDiscovery extends BaseSmartObjectDiscovery {

	public SmartObjectList execute(String[] form) {
		SmartObjectList list = new SmartObjectList();
		List<SmartObject> soList = new ArrayList<SmartObject>(0);
		for (int i = 0; i < 20; i++) {
			soList.add(new SmartObject(i, "A" + i, "smart object " + i, null));
		}
		list.setList(soList);
		return list;
	}

}
