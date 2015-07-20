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
public class SmartObjectService implements Serializable {

	private static final long serialVersionUID = 1334387233404595046L;
	private String serviceName;
	private String returnType;
	private List<SOServiceParam> params;
	private String idServiceModbus, idRegisterModbus;

	public SmartObjectService() {
		this.params = new ArrayList<SOServiceParam>();
	}

	public List<SOServiceParam> getParams() {
		return params;
	}

	public void setParams(List<SOServiceParam> params) {
		this.params = params;
	}

	public void addParam(SOServiceParam param) {
		this.params.add(param);
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getIdServiceModbus() {
		return idServiceModbus;
	}

	public void setIdServiceModbus(String idServiceModbus) {
		this.idServiceModbus = idServiceModbus;
	}

	public String getIdRegisterModbus() {
		return idRegisterModbus;
	}

	public void setIdRegisterModbus(String idRegisterModbus) {
		this.idRegisterModbus = idRegisterModbus;
	}

}
