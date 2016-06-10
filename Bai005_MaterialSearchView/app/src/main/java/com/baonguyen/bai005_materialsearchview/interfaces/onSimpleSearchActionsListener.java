package com.baonguyen.bai005_materialsearchview.interfaces;

/**
 * Created by shahroz on 1/12/2016.
 */
public interface onSimpleSearchActionsListener {
    void onItemClicked(String item);
    void onScroll();
    void error(String localizedMessage);
}
