/**
 * 
 */
package br.com.sorepository.model.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ercilio Nascimento
 */
public class SmartObjectList {

	private List<String> list;

	public SmartObjectList() {
		this.list = new ArrayList<String>(0);
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String printList() {
		String s = "";
		for (int i = 0; i < this.list.size(); i++) {
			s += this.list.get(i) + ";";
		}

		return s;
	}

}
