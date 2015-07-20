/**
 * 
 */
package br.com.mobgui4so.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ercilio Nascimento
 */
public class SmartObjectList implements Serializable {

	private static final long serialVersionUID = 6494791003706930546L;
	private List<SmartObject> list;
	private String user;
	private String password;
	private boolean ckSaveUser;
	private String serverURL;
	private String errorMsg;

	public SmartObjectList merge(SmartObjectList listFromDisk) {
		if (listFromDisk != null) {
			listFromDisk.setAllUnavaiable();
			for (SmartObject so : list) {
				if (!listFromDisk.containsSO(so.getIdSOBD())) {
					listFromDisk.add(so);
				} else {
					listFromDisk.getSO(so.getIdSOBD()).setAvaiable(true);
				}
			}

			return listFromDisk;
		} else {
			return this;
		}
	}

	public SmartObject getSO(int idSOBD) {
		SmartObject smart = null;
		for (SmartObject so : list) {
			if (so.getIdSOBD() == idSOBD) {
				smart = so;
				break;
			}
		}

		return smart;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setAllUnavaiable() {
		for (SmartObject so : list) {
			so.setAvaiable(false);
		}
	}

	public boolean containsSO(int idSOBD) {
		boolean contains = false;
		for (SmartObject so : list) {
			if (so.getIdSOBD() == idSOBD) {
				contains = true;
				break;
			}
		}

		return contains;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public SmartObjectList() {
		this.list = new ArrayList<SmartObject>(0);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCkSaveUser() {
		return ckSaveUser;
	}

	public void setCkSaveUser(boolean ckSaveUser) {
		this.ckSaveUser = ckSaveUser;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	/**
	 * @return the list
	 */
	public List<SmartObject> getList() {
		return this.list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<SmartObject> list) {
		this.list = list;
	}

	public String[] getSONames() {
		String[] names = new String[list.size()];
		for (int i = 0; i < names.length; i++) {
			if (list.get(i).isAvaiable()) {
				names[i] = list.get(i).getName();
			}
		}

		return names;
	}

	public int getMaxSONameLength() {
		int length = 0;
		String[] names = new String[list.size()];
		for (int i = 0; i < names.length; i++) {
			int currentLength = list.get(i).getName().length();
			if (currentLength > length) {
				length = currentLength;
			}
		}

		return length;
	}

	public void add(SmartObject so) {
		this.list.add(so);
	}
}
