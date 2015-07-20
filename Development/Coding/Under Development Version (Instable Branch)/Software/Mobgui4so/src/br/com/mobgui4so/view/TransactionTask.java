/**
 * 
 */
package br.com.mobgui4so.view;

import android.content.Context;
import android.os.AsyncTask;
import br.com.mobgui4so.utils.AndroidUtils;

/**
 * @author Ercilio Nascimento
 */
public class TransactionTask extends AsyncTask<Void, Void, Boolean> {

	private final Context context;
	private final ITransaction transaction;
	private Throwable exceptionErro;

	public TransactionTask(Context context, ITransaction transaction) {
		this.context = context;
		this.transaction = transaction;
	}

	@Override
	protected void onPreExecute() {
		transaction.preExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			transaction.execute();
		} catch (Throwable e) {
			this.exceptionErro = e;
			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			transaction.postExecute();
		} else {
			AndroidUtils.alertDialog(context, "Erro: " + exceptionErro.getMessage());
		}
	}
}
