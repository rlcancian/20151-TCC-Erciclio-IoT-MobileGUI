/**
 * 
 */
package br.com.mobgui4so.utils;

import android.app.Activity;

/**
 * @author Ercilio Nascimento
 */
public class BlockingOnUIRunnable {

	// Activity
	private Activity activity;

	// Event Listener
	private IBlockingOnUIRunnableListener listener;

	// UI runnable
	private Runnable uiRunnable;

	/**
	 * Class initialization
	 * 
	 * @param activity
	 *            Activity
	 * @param listener
	 *            Event listener
	 */
	public BlockingOnUIRunnable(Activity activity, IBlockingOnUIRunnableListener listener) {
		this.activity = activity;
		this.listener = listener;

		uiRunnable = new Runnable() {
			public void run() {
				// Execute custom code
				if (BlockingOnUIRunnable.this.listener != null) {
					BlockingOnUIRunnable.this.listener.runOnUIThread();
				}

				synchronized (this) {
					this.notify();
				}
			}
		};
	}

	/**
	 * Start runnable on UI thread and wait until finished
	 */
	public void startOnUiAndWait() {
		synchronized (uiRunnable) {
			// Execute code on UI thread
			activity.runOnUiThread(uiRunnable);

			// Wait until runnable finished
			try {
				uiRunnable.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
