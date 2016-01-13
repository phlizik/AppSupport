package com.app.support.widget.gridview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liyang on 16/1/12.
 */
public interface OnChildSelectedListener {
    void onChildSelected(ViewGroup parent, View contentView, int position, long l);
}

