package com.app.support.widget.gridview;

import android.support.v4.util.CircularArray;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyang on 16/1/12.
 */
public abstract class StaggeredGrid {

    public static final int START_DEFAULT = -1;
    protected int mFirstIndex = -1;
    protected CircularArray mLocations = new CircularArray(64);
    protected int mNumRows = 1;
    protected StaggeredGrid.Provider mProvider;
    protected StaggeredGrid.Row[] mRows;
    protected int mStartIndex = -1;
    protected int mStartRow = -1;
    private ArrayList[] mTmpItemPositionsInRows;

    protected final StaggeredGrid.Location appendItemToRow(int paramInt1, int paramInt2) {
        StaggeredGrid.Location localLocation = new StaggeredGrid.Location(paramInt2);
        if (this.mLocations.size() == 0) {
            this.mFirstIndex = paramInt1;
        }
        this.mLocations.addLast(localLocation);
        this.mProvider.createItem(paramInt1, paramInt2, true);
        return localLocation;
    }

    public abstract void appendItems(int paramInt);

    public final void debugPrint(PrintWriter paramPrintWriter) {
        int j = this.mLocations.size();
        int i = 0;
        while (i < j) {
            StaggeredGrid.Location localLocation = (StaggeredGrid.Location) this.mLocations.get(i);
            paramPrintWriter.print("<" + (this.mFirstIndex + i) + "," + localLocation.row + ">");
            paramPrintWriter.print(" ");
            paramPrintWriter.println();
            i += 1;
        }
    }

    public final int getFirstIndex() {
        return this.mFirstIndex;
    }

    public final List[] getItemPositionsInRows(int paramInt1, int paramInt2) {
        int i = 0;
        while (i < this.mNumRows) {
            this.mTmpItemPositionsInRows[i].clear();
            i += 1;
        }
        if (paramInt1 >= 0) {
            while (paramInt1 <= paramInt2) {
                this.mTmpItemPositionsInRows[getLocation(paramInt1).row].add(Integer.valueOf(paramInt1));
                paramInt1 += 1;
            }
        }
        return this.mTmpItemPositionsInRows;
    }

    public final int getLastIndex() {
        return this.mFirstIndex + this.mLocations.size() - 1;
    }

    public final StaggeredGrid.Location getLocation(int paramInt) {
        if (this.mLocations.size() == 0) {
            return null;
        }
        return (StaggeredGrid.Location) this.mLocations.get(paramInt - this.mFirstIndex);
    }

    protected final int getMaxHighRowIndex() {
        int j = 0;
        int i = 1;
        while (i < this.mNumRows) {
            int k = j;
            if (this.mRows[i].high > this.mRows[j].high) {
                k = i;
            }
            i += 1;
            j = k;
        }
        return j;
    }

    protected final int getMaxLowRowIndex() {
        int j = 0;
        int i = 1;
        while (i < this.mNumRows) {
            int k = j;
            if (this.mRows[i].low > this.mRows[j].low) {
                k = i;
            }
            i += 1;
            j = k;
        }
        return j;
    }

    protected final int getMinHighRowIndex() {
        int j = 0;
        int i = 1;
        while (i < this.mNumRows) {
            int k = j;
            if (this.mRows[i].high < this.mRows[j].high) {
                k = i;
            }
            i += 1;
            j = k;
        }
        return j;
    }

    protected final int getMinLowRowIndex() {
        int j = 0;
        int i = 1;
        while (i < this.mNumRows) {
            int k = j;
            if (this.mRows[i].low < this.mRows[j].low) {
                k = i;
            }
            i += 1;
            j = k;
        }
        return j;
    }

    public final int getNumRows() {
        return this.mNumRows;
    }

    public final int getSize() {
        return this.mLocations.size();
    }

    protected final StaggeredGrid.Location prependItemToRow(int firstIndex, int row) {
        StaggeredGrid.Location location = new StaggeredGrid.Location(row);
        this.mFirstIndex = firstIndex;
        this.mLocations.addFirst(location);
        this.mProvider.createItem(firstIndex, row, false);
        return location;
    }

    public abstract void prependItems(int item);

    public final void removeFirst() {
        this.mFirstIndex += 1;
        this.mLocations.popFirst();
    }

    public final void removeLast() {
        this.mLocations.popLast();
    }

    public void setProvider(StaggeredGrid.Provider provider) {
        this.mProvider = provider;
    }

    public final void setRows(StaggeredGrid.Row[] rows) {
        if ((rows == null) || (rows.length == 0)) {
            throw new IllegalArgumentException();
        }
        this.mNumRows = rows.length;
        this.mRows = rows;
        this.mTmpItemPositionsInRows = new ArrayList[this.mNumRows];
        int i = 0;
        while (i < this.mNumRows) {
            this.mTmpItemPositionsInRows[i] = new ArrayList(32);
            i += 1;
        }
    }

    public final void setStart(int startIndex, int startRow) {
        this.mStartIndex = startIndex;
        this.mStartRow = startRow;
    }

    public abstract void stripDownTo(int downTo);

    public static final class Location {
        public final int row;

        public Location(int row) {
            this.row = row;
        }
    }

    public interface Provider {
        void createItem(int index, int row, boolean paramBoolean);

        int getCount();
    }

    public final static class Row {
        public int high;
        public int low;
    }
}
