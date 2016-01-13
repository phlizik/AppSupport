package com.app.support.widget.gridview;

import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.SparseArray;
import android.view.View;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by liyang on 16/1/12.
 */
public class ViewsStateBundle {

    public static final int LIMIT_DEFAULT = 100;
    public static final int UNLIMITED = Integer.MAX_VALUE;
    private LruCache mChildStates;
    private int mLimitNumber = 100;
    private int mSavePolicy = 0;

    static String getSaveStatesKey(int paramInt) {
        return Integer.toString(paramInt);
    }

    protected void applyPolicyChanges() {
        if (this.mSavePolicy == 2) {
            if (this.mLimitNumber <= 0) {
                throw new IllegalArgumentException();
            }
            if ((this.mChildStates == null) || (this.mChildStates.maxSize() != this.mLimitNumber)) {
                this.mChildStates = new LruCache(this.mLimitNumber);
            }
        }
        do {
            if ((this.mSavePolicy != 3) && (this.mSavePolicy != 1)) {
                this.mChildStates = null;
                break;
            }
            return;
        } while ((this.mChildStates != null) && (this.mChildStates.maxSize() == Integer.MAX_VALUE));
        this.mChildStates = new LruCache(Integer.MAX_VALUE);
    }

    public void clear() {
        if (this.mChildStates != null) {
            this.mChildStates.evictAll();
        }
    }

    public final int getLimitNumber() {
        return this.mLimitNumber;
    }

    public final int getSavePolicy() {
        return this.mSavePolicy;
    }

    public final void loadFromBundle(Bundle paramBundle) {
        if ((this.mChildStates != null) && (paramBundle != null)) {
            this.mChildStates.evictAll();
            Iterator localIterator = paramBundle.keySet().iterator();
            while (localIterator.hasNext()) {
                String str = (String) localIterator.next();
                this.mChildStates.put(str, paramBundle.getSparseParcelableArray(str));
            }
        }
    }

    public final void loadView(View paramView, int paramInt) {
        if (this.mChildStates != null) {
            Object localObject = getSaveStatesKey(paramInt);
            localObject = (SparseArray) this.mChildStates.get(localObject);
            if (localObject != null) {
                paramView.restoreHierarchyState((SparseArray) localObject);
            }
        }
    }

    public void remove(int paramInt) {
        if ((this.mChildStates != null) && (this.mChildStates.size() != 0)) {
            this.mChildStates.remove(getSaveStatesKey(paramInt));
        }
    }

    public final Bundle saveAsBundle() {
        if ((this.mChildStates == null) || (this.mChildStates.size() == 0)) {
            return null;
        }
        Object localObject = this.mChildStates.snapshot();
        Bundle localBundle = new Bundle();
        localObject = ((Map) localObject).entrySet().iterator();
        while (((Iterator) localObject).hasNext()) {
            Map.Entry localEntry = (Map.Entry) ((Iterator) localObject).next();
            localBundle.putSparseParcelableArray((String) localEntry.getKey(), (SparseArray) localEntry.getValue());
        }
        return localBundle;
    }

    public final void saveOffscreenView(View paramView, int paramInt) {
        remove(paramInt);
        switch (this.mSavePolicy) {
            case 2:
            case 3:
                saveViewUnchecked(paramView, paramInt);
                return;
            default:
                return;
        }
    }

    public final void saveOnScreenView(View paramView, int paramInt) {
        if (this.mSavePolicy != 0) {
            saveViewUnchecked(paramView, paramInt);
        }
    }

    protected final void saveViewUnchecked(View paramView, int paramInt) {
        if (this.mChildStates != null) {
            String str = getSaveStatesKey(paramInt);
            SparseArray localSparseArray = new SparseArray();
            paramView.saveHierarchyState(localSparseArray);
            this.mChildStates.put(str, localSparseArray);
        }
    }

    public final void setLimitNumber(int paramInt) {
        this.mLimitNumber = paramInt;
        applyPolicyChanges();
    }

    public final void setSavePolicy(int paramInt) {
        this.mSavePolicy = paramInt;
        applyPolicyChanges();
    }
}
