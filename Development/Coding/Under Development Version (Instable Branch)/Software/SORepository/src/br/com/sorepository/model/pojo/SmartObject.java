/**
 * 
 */
package br.com.sorepository.model.pojo;

/**
 * @author Ercilio Nascimento
 */
public class SmartObject {

	private String soName;
	private String serviceName;
	private String serviceReturnType;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;

	public SmartObject() {
	}

	public static SmartObject splitCommandToSmartObject(String command) {
		SmartObject so = new SmartObject();
		String[] array = command.split(",");
		so.setSoName(array[0]);
		so.setServiceName(array[1]);
		so.setServiceReturnType(array[2]);
		so.setParam1(array[3]);
		so.setParam2(array[4]);
		so.setParam3(array[5]);
		so.setParam4(array[6]);
		so.setParam5(array[7]);

		return so;
	}

	public String getSoName() {
		return soName;
	}

	public void setSoName(String soName) {
		this.soName = soName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceReturnType() {
		return serviceReturnType;
	}

	public void setServiceReturnType(String serviceReturnType) {
		this.serviceReturnType = serviceReturnType;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

}
