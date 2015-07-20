/**
 * 
 */
package br.com.mobgui4so.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import br.com.mobgui4so.model.discovery.BaseSmartObjectDiscovery;
import br.com.mobgui4so.model.factory.SmartObjectDiscoveryFactory;
import br.com.mobgui4so.model.guigenerating.GUIGeneratingMain;
import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;
import br.com.mobgui4so.model.interaction.SmartObjectCommandSender;
import br.com.mobgui4so.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */
public class ApplicationFacade {

	private SmartObjectDiscoveryFactory soFactory;

	public ApplicationFacade() {
		soFactory = SmartObjectDiscoveryFactory.getInstance();
	}

	public SmartObjectList discovery(String[] form, int discoveryType, InputStream properties) throws IOException, ClassNotFoundException {
		BaseSmartObjectDiscovery discover = soFactory.create(discoveryType, properties);
		SmartObjectList list = discover.execute(form);
		// this.saveSmartObjectListToDisk(form, list, saveStream);
		// SmartObjectList so = this.loadSmartObjectList(save);
		return list;
	}

	public void saveSmartObjectListToDisk(SmartObjectList list, FileOutputStream saveStream) {
		/*list.setServerURL(form[2]);
		if (Boolean.parseBoolean(form[3])) {
			list.setUser(form[0]);
			list.setPassword(form[1]);
			list.setCkSaveUser(true);
		}*/
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(saveStream);
			os.writeObject(list);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SmartObjectList loadSmartObjectListFromDisk(FileInputStream loadStream) throws IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(loadStream);
		SmartObjectList soList = (SmartObjectList) is.readObject();
		is.close();
		return soList;
	}

	/**
	 * Use it for Non-Android plataforms
	 */
	public BasePhenotype generateGUI(SmartObjectList soList, int soIndex, int screenWidth, int screenHeight, FileOutputStream saveStream) {
		return generateGUI(soList, soIndex, screenWidth, screenHeight, null, saveStream);
	}

	/**
	 * Use it just for Android plataforms
	 */
	public BasePhenotype generateGUI(SmartObjectList soList, int soIndex, int screenWidth, int screenHeight, Object context, FileOutputStream saveStream) {
		BasePhenotype phenotype = new GUIGeneratingMain(soList.getList().get(soIndex), screenWidth, screenHeight, context).generateGUI();
		saveSmartObjectListToDisk(soList, saveStream);

		return phenotype;
	}

	public String sendSmartObjectCommand(String url, List<String> params) {
		return SmartObjectCommandSender.sendSmartObjectCommand(url, params);
	}
}
