/**
 * 
 */
package br.com.mobgui4so.view;

/**
 * @author Ercilio Nascimento
 */
public interface ITransaction {

	public void preExecute();

	public void execute() throws Exception;

	public void postExecute();

	public void swapLayouts();
}
