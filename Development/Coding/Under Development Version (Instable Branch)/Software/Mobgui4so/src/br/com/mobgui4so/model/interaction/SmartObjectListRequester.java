/**
 * 
 */
package br.com.mobgui4so.model.interaction;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.mobgui4so.model.guigenerating.Gene.ParamType;
import br.com.mobgui4so.model.pojo.SOServiceParam;
import br.com.mobgui4so.model.pojo.SmartObject;
import br.com.mobgui4so.model.pojo.SmartObjectList;
import br.com.mobgui4so.model.pojo.SmartObjectService;
import br.com.mobgui4so.utils.HttpHelper;

/**
 * @author Ercilio Nascimento
 */
public class SmartObjectListRequester {

	public static SmartObjectList getSmartObjectList(String[] form) throws IOException {
		String list = HttpHelper.doPost(form[2] + "/getList.do", "user", form[0], "password", form[1]);
		/*String list = "1,A1,http://192.168.43.138:8080/SOServer/soCommand.do,Ar Condicionado,05,00,Ligar,Ligado,boolean,null,null,null;" +
			"1,A1,http://192.168.43.138:8080/SOServer/soCommand.do,Ar Condicionado,16,00,Configurar,Temperatura,double,18,28,null;" +
			"1,A1,http://192.168.43.138:8080/SOServer/soCommand.do,Ar Condicionado,16,00,Configurar,Humidade,double,0,100,null;" +
			"1,A1,http://192.168.43.138:8080/SOServer/soCommand.do,Ar Condicionado,16,00,Configurar,Velocidade,combo,null,null,Fraco|M\u00E9dio|Forte;" +
			"1,A1,http://192.168.43.138:8080/SOServer/soCommand.do,Ar Condicionado,16,00,Configurar,Timer,double,0,7,null;" +
			"1,A1,http://192.168.43.138:8080/SOServer/soCommand.do,Ar Condicionado,3,,Tipo de Fun\u00E7\u00E3o,Fun\u00E7\u00E3o,combo,null,null,Circular|Refrigerar|Aquecer;" +
			"1,A1,http://192.168.43.138:8080/SOServer/soCommand.do,Ar Condicionado,03,00,Pegar Temperatura,Temperatura,get,null,null,null;" +
			"3,A2,http://192.168.43.138:8080/SOServer/soCommand.do,L\u00E2mpada,05,00,Acender,Aceso,boolean,null,null,null;" +
			"5,A3,http://192.168.43.138:8080/SOServer/soCommand.do,CO2,03,00,Quantidade de CO2,CO2,get,null,null,null;";*/
		if (list.equals(""))
			return null;
		else
			return parseList(StringEscapeUtils.unescapeJava(list));
	}

	private static SmartObjectList parseList(String list) {
		SmartObjectList soList = new SmartObjectList();
		if (list.startsWith("0")) {
			soList.setErrorMsg(list.substring(1));

			return soList;
		}
		SmartObject so = null;
		String soName = null;
		String serviceName = null;
		SmartObjectService soService = null;
		List<String> sos = Arrays.asList(list.split(";"));
		for (int i = 0; i < sos.size(); i++) {
			List<String> services = Arrays.asList(sos.get(i).split(","));
			if (!services.get(3).equalsIgnoreCase(soName)) {
				if (soName != null) {
					so.add(soService);
					soList.add(so);
				}
				so = new SmartObject();
				so.setIdSOBD(Integer.parseInt(services.get(0)));
				so.setIdSOModbus(services.get(1));
				so.setUrl(services.get(2));
				so.setName(services.get(3));
				soName = services.get(3);
				soService = null;
			}
			if (serviceName == null || !services.get(6).equalsIgnoreCase(serviceName)) {
				if (soService != null)
					so.add(soService);
				soService = new SmartObjectService();
				soService.setIdServiceModbus(services.get(4));
				soService.setIdRegisterModbus(services.get(5));
				soService.setServiceName(services.get(6));
				serviceName = services.get(6);
			}
			SOServiceParam param = new SOServiceParam();
			param.setName(services.get(7));
			param.setType(ParamType.valueOf(services.get(8).toUpperCase()));
			param.setMinValue(services.get(9));
			param.setMaxValue(services.get(10));
			param.setOptions(services.get(11));
			soService.addParam(param);
		}
		so.add(soService);
		soList.add(so);

		return soList;
	}
}
