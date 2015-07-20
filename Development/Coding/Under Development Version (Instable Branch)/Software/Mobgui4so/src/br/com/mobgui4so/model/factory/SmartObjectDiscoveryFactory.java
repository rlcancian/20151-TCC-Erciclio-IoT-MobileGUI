/**
 * 
 */
package br.com.mobgui4so.model.factory;

import java.io.InputStream;
import java.util.Properties;

import br.com.mobgui4so.model.discovery.BaseSmartObjectDiscovery;

/**
 * @author Ercilio Nascimento
 */
public class SmartObjectDiscoveryFactory {

	private Properties discoveryTypes = new Properties();
	private static SmartObjectDiscoveryFactory instance = new SmartObjectDiscoveryFactory();

	public static SmartObjectDiscoveryFactory getInstance() {
		return instance;
	}

	public BaseSmartObjectDiscovery create(int discoveryType, InputStream properties) {
		String typeName = "";
		switch (discoveryType) {
		case 1:
			typeName = "WiFi";
			break;
		default:
			typeName = "WiFi";
			break;
		}
		try {
			discoveryTypes.load(properties);
			String stringClass = discoveryTypes.getProperty(typeName);
			Class<?> discoveryClass = Class.forName(stringClass);
			return (BaseSmartObjectDiscovery) discoveryClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
