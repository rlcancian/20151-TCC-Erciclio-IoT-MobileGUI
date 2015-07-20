/**
 * 
 */
package br.com.soserver.comm;

/**
 * @author Ercilio Nascimento
 */
public class SimulatePortManager extends BasePortManager {

	@Override
	public String write(byte[] command) {
		String ack = null;
		String commandString = new String(command);
		int id = Integer.valueOf(commandString.substring(1, 3), 16);
		ack = "Comando executado com sucesso!";
		switch (id) {
		case 0xA1:

			break;
		case 0xA2:

			break;
		case 0xA3:

			break;

		default:
			break;
		}

		return ack;
	}

}
