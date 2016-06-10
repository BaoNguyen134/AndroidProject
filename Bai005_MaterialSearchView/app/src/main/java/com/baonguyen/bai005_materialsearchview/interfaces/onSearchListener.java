package com.baonguyen.bai005_materialsearchview.interfaces;

/**
 * Created by shahroz on 1/12/2016.
 */
public interface onSearchListener {
    void onSearch(String query);
    void searchViewOpened();
    void searchViewClosed();
    void onCancelSearch();
}
