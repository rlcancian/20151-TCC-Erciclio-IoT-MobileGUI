/**
 * 
 */
package br.com.soserver.comm;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Ercilio Nascimento
 */
public class ParserCommand {

	private BasePortManager manager;

	public ParserCommand() {
		this.manager = PortManager.getInstance();
		// this.manager = new SimulatePortManager();
	}

	public String send(String command) {
		String modbusReturn = manager.write(parseCommandToASCIIModBus(command).getBytes());

		return parseModbusReturn(modbusReturn);
	}

	private String parseModbusReturn(String modbus) {
		String modbusReturn = null;
		if (modbus.equals("OK")) {
			modbusReturn = "Operação executada com sucesso!";
		} else {
			modbusReturn = String.valueOf(Integer.valueOf(modbus.substring(7, 9), 16));
		}

		return modbusReturn;
	}

	private String parseCommandToASCIIModBus(String command) {
		// command = "idSOModbus=A3&idServiceModbus=03&idRegisterModbus=00&ligar=false";
		System.out.println("Command: " + command);
		System.out.println("Parsing command to ASCII Modbus...");
		StringBuilder modBus = new StringBuilder(":");
		List<String> params = Arrays.asList(command.split("&"));
		String AA = StringUtils.substringAfter(params.get(0), "=");
		String BB = StringUtils.substringAfter(params.get(1), "=");
		String CC = StringUtils.substringAfter(params.get(2), "=");
		String DD = "";
		int AAhex = Integer.valueOf(AA, 16);
		int BBhex = Integer.valueOf(BB, 16);
		int CChex = Integer.valueOf(CC, 16);
		int DDhex = 0x00;
		if (BBhex != 0x03) {
			DD = StringUtils.substringAfter(params.get(3), "=");
			if (DD.equalsIgnoreCase("false")) {
				DD = "00";
			} else if (DD.equalsIgnoreCase("true") && AA.equals("A1")) {
				DD = "FF";
				DDhex = 0xFF;
			} else if (DD.equalsIgnoreCase("true") && AA.equals("A2")) {
				DD = "01";
				DDhex = 0x01;
			} else {
				DDhex = Integer.valueOf(DD);
				DD = Integer.toHexString(DDhex).toUpperCase();
			}
		}
		String irc = Integer.toHexString((((AAhex + BBhex + DDhex + CChex) ^ 0xFF) + 1) & 0xFF);
		modBus.append(AA);
		modBus.append(BB);
		modBus.append(CC);
		modBus.append(DD);
		modBus.append(irc.toUpperCase());
		System.out.println("Command parsed: " + modBus.toString() + "\\r\\n");

		return modBus.toString() + "\r\n";
		// return ":A303005A\r\n";
	}
}
