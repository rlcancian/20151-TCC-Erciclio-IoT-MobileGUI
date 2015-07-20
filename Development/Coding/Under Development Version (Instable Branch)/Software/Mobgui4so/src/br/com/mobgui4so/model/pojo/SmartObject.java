/**
 * 
 */
package br.com.mobgui4so.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import br.com.mobgui4so.model.guigenerating.Genotype;

/**
 * @author Ercilio Nascimento
 */
public class SmartObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -451798137279786633L;
	private int idSOBD;
	private String idSOModbus;
	private String url;
	private String name;
	private List<SmartObjectService> services;
	private boolean avaiable = true;
	private NavigableMap<Double, Genotype> landscapeGenotypes;
	private NavigableMap<Double, Genotype> portraitGenotypes;
	private Double actualLandscapeKey;
	private Double actualPortraitKey;

	public Double getActualLandscapeKey() {
		return actualLandscapeKey;
	}

	public String getIdServiceModbus(String serviceName) {
		String idServiceModbus = null;
		for (SmartObjectService service : this.services) {
			if (service.getServiceName().equalsIgnoreCase(serviceName)) {
				idServiceModbus = service.getIdServiceModbus();
				break;
			}
		}

		return idServiceModbus;
	}

	public String getIdRegisterModbus(String serviceName) {
		String idRegisterModbus = null;
		for (SmartObjectService service : this.services) {
			if (service.getServiceName().equalsIgnoreCase(serviceName)) {
				idRegisterModbus = service.getIdRegisterModbus();
				break;
			}
		}

		return idRegisterModbus;
	}

	public String getIdSOModbus() {
		return idSOModbus;
	}

	public void setIdSOModbus(String idSOModbus) {
		this.idSOModbus = idSOModbus;
	}

	public void setActualLandscapeKey(Double actualLandscapeKey) {
		this.actualLandscapeKey = actualLandscapeKey;
	}

	public Double getActualPortraitKey() {
		return actualPortraitKey;
	}

	public void setActualPortraitKey(Double actualPortraitKey) {
		this.actualPortraitKey = actualPortraitKey;
	}

	public SmartObject() {
		this.services = new ArrayList<SmartObjectService>(0);
		this.landscapeGenotypes = new TreeMap<Double, Genotype>();
		this.portraitGenotypes = new TreeMap<Double, Genotype>();
	}

	public NavigableMap<Double, Genotype> getLandscapeGenotypes() {
		return landscapeGenotypes;
	}

	public void setLandscapeGenotypes(NavigableMap<Double, Genotype> landscapeGenotypes) {
		this.landscapeGenotypes = landscapeGenotypes;
	}

	public NavigableMap<Double, Genotype> getPortraitGenotypes() {
		return portraitGenotypes;
	}

	public void setPortraitGenotypes(NavigableMap<Double, Genotype> portraitGenotypes) {
		this.portraitGenotypes = portraitGenotypes;
	}

	public boolean isAvaiable() {
		return avaiable;
	}

	public void setAvaiable(boolean avaiable) {
		this.avaiable = avaiable;
	}

	public void add(SmartObjectService service) {
		this.services.add(service);
	}

	public SmartObject(int idSOBD, String idSOModbus, String name, List<SmartObjectService> services) {
		this.idSOBD = idSOBD;
		this.idSOModbus = idSOModbus;
		this.name = name;
		this.services = services;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdSOBD() {
		return idSOBD;
	}

	public void setIdSOBD(int idSOBD) {
		this.idSOBD = idSOBD;
	}

	public List<SmartObjectService> getServices() {
		return this.services;
	}

	public void setServices(List<SmartObjectService> services) {
		this.services = services;
	}
}
