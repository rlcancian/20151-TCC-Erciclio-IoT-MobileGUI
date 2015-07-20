/**
 * 
 */
package br.com.mobgui4so.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ScrollView;

/**
 * @author Ercilio Nascimento
 */
public class ScrollViewMap {

	private static Map<Integer, List<ScrollView>> map = new HashMap<Integer, List<ScrollView>>();

	public static List<ScrollView> getScrollViewList(int idSO) {
		List<ScrollView> list = map.get(idSO);
		if (list == null) {
			list = new ArrayList<ScrollView>();
			map.put(idSO, list);
		}

		return list;
	}

	public static void updateMap(int idSO, int idFragment, ScrollView sv) {
		List<ScrollView> list = map.get(idSO);
		list.add(idFragment, sv);
		map.put(idSO, list);
	}

}
