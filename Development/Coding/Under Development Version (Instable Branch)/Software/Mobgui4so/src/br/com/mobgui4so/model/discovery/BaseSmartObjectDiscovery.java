/**
 * 
 */
package br.com.mobgui4so.model.discovery;

import java.io.IOException;

import br.com.mobgui4so.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */
public abstract class BaseSmartObjectDiscovery {

	public abstract SmartObjectList execute(String[] form) throws IOException;

}
