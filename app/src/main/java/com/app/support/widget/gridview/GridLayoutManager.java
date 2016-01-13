package com.app.support.widget.gridview;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;

/**
 * Created by liyang on 16/1/12.
 */
public class GridLayoutManager extends RecyclerView.LayoutManager {

    private static final boolean DEBUG = false;
    private static final int NEXT_ITEM = 1;
    private static final int NEXT_ROW = 3;
    private static final int PREV_ITEM = 0;
    private static final int PREV_ROW = 2;
    private static final String TAG = "GridLayoutManager";
    private final BaseGridView mBaseGridView;
    private OnChildSelectedListener mChildSelectedListener = null;
    private int mChildVisibility = -1;
    final ViewsStateBundle mChildrenStates = new ViewsStateBundle();
    private int mFirstVisiblePos;
    private int mFixedRowSizeSecondary;
    private boolean mFocusOutEnd;
    private boolean mFocusOutFront;
    private int mFocusPosition = -1;
    private int mFocusPositionOffset = 0;
    private int mFocusScrollStrategy = 0;
    private boolean mFocusSearchDisabled;
    private boolean mForceFullLayout;
    private int mGravity = 51;
    private StaggeredGrid mGrid;


    private StaggeredGrid.Provider mGridProvider = new StaggeredGrid.Provider() {
        @Override
        public void createItem(int index, int row, boolean paramBoolean) {
//            int j = 0;
//            int m = 0;
//            View localView = getViewForPosition(index);
//            if (mFirstVisiblePos >= 0) {
//                if ((paramBoolean) && (index != mLastVisiblePos + 1)) {
//                    throw new RuntimeException();
//                }
//                if ((!paramBoolean) && (index != mFirstVisiblePos - 1)) {
//                    throw new RuntimeException();
//                }
//            }
//            int k;
//            label180:
//            int i;
//            if (!((RecyclerView.LayoutParams) localView.getLayoutParams()).isItemRemoved()) {
//                if (paramBoolean) {
//                    addView(localView);
//                    if (mChildVisibility != -1) {
//                        localView.setVisibility(mChildVisibility);
//                    }
//                    if ((mInLayout) && (index == mFocusPosition)) {
//                        dispatchChildSelected();
//                    }
//                    measureChild(localView);
//                }
//            } else {
//                if (mOrientation != 0) {
//                    k = localView.getMeasuredHeight();
//                }
//                k = localView.getMeasuredWidth();
//                if (mRows[row].high != mRows[row].low) {
//                    break label423;
//                }
//                i = 1;
//                label210:
//                if (!paramBoolean) {
//                    break label537;
//                }
//                if (i != 0) {
//                    break label429;
//                }
//                i = mRows[row].high + mMarginPrimary;
//                k += i;
//                mRows[row].high = k;
//                j = i;
//                i = k;
//                label270:
//                if (mFirstVisiblePos >= 0) {
//                    break label701;
//                }
//                mLastVisiblePos = index;
//                mFirstVisiblePos = index;
//            }
//            for (; ; ) {
//                k = getRowStartSecondary(row);
//                m = mScrollOffsetSecondary;
//                mChildrenStates.loadView(localView, index);
//                layoutChild(row, localView, j - mScrollOffsetPrimary, i - mScrollOffsetPrimary, k - m);
//                if (index == mFirstVisiblePos) {
//                    updateScrollMin();
//                }
//                if (index == mLastVisiblePos) {
//                    updateScrollMax();
//                }
//                return;
//                addView(localView, 0);
//                break;
//                break label180;
//                label423:
//                i = 0;
//                break label210;
//                label429:
//                i = m;
//                if (mLastVisiblePos >= 0) {
//                    i = mGrid.getLocation(mLastVisiblePos).row;
//                    if (i >= mNumRows - 1) {
//                        break label511;
//                    }
//                }
//                label511:
//                for (i = mRows[i].low; ; i = mRows[i].high + mMarginPrimary) {
//                    mRows[row].low = i;
//                    break;
//                }
//                label537:
//                if (i == 0) {
//                    i = mRows[row].low - mMarginPrimary;
//                    j = i - k;
//                    mRows[row].low = j;
//                    break label270;
//                }
//                i = k;
//                if (mFirstVisiblePos >= 0) {
//                    i = mGrid.getLocation(mFirstVisiblePos).row;
//                    if (i <= 0) {
//                        break label668;
//                    }
//                    j = mRows[i].low;
//                    i = k + j;
//                }
//                for (; ; ) {
//                    mRows[row].high = i;
//                    break;
//                    label668:
//                    i = mRows[i].low - mMarginPrimary;
//                    j = i - k;
//                }
//                label701:
//                if (paramBoolean) {
//                    mLastVisiblePos++;
//                } else {
//                    mFirstVisiblePos--;
//                }
//            }
        }

        @Override
        public int getCount() {
            return mState.getItemCount();
        }
    };


    private int mHorizontalMargin;
    private boolean mInLayout = false;
    private boolean mInSelection = false;
    private final ItemAlignment mItemAlignment = new ItemAlignment();
    private int mLastVisiblePos;
    private boolean mLayoutEnabled = true;
    private int mMarginPrimary;
    private int mMarginSecondary;
    private int mMaxSizeSecondary;
    private int[] mMeasuredDimension = new int[2];
    private int mNumRows;
    private int mNumRowsRequested = 1;
    private int mOrientation = 0;
    private float mPrimaryOverReach = 1.0F;
    private boolean mPruneChild = true;
    private RecyclerView.Recycler mRecycler;
    private final Runnable mRequestLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            requestLayout();
        }
    };
    private boolean mRowSecondarySizeRefresh;
    private int[] mRowSizeSecondary;
    private int mRowSizeSecondaryRequested;
    private StaggeredGrid.Row[] mRows;
    private boolean mScrollEnabled = true;
    private int mScrollOffsetPrimary;
    private int mScrollOffsetSecondary;
    private int mSizePrimary;
    private RecyclerView.State mState;
    private int[] mTempDeltas = new int[2];
    private int mVerticalMargin;
    private final WindowAlignment mWindowAlignment = new WindowAlignment();

    public GridLayoutManager(BaseGridView paramBaseGridView) {
        mBaseGridView = paramBaseGridView;
    }

    private boolean appendOneVisibleItem() {
        while ((this.mLastVisiblePos != -1) && (this.mLastVisiblePos < this.mState.getItemCount() - 1) && (this.mLastVisiblePos < this.mGrid.getLastIndex())) {
            int i = this.mLastVisiblePos + 1;
            int j = this.mGrid.getLocation(i).row;
            this.mGridProvider.createItem(i, j, true);
            if (j == this.mNumRows - 1) {
                return false;
            }
        }
        if (((this.mLastVisiblePos == -1) && (this.mState.getItemCount() > 0)) || ((this.mLastVisiblePos != -1) && (this.mLastVisiblePos < this.mState.getItemCount() - 1))) {
            this.mGrid.appendItems(this.mScrollOffsetPrimary + this.mSizePrimary);
            return false;
        }
        return true;
    }

    private void appendVisibleItems() {
        while ((needsAppendVisibleItem()) && (!appendOneVisibleItem())) {
        }
    }

    private void discardLayoutInfo() {
        this.mGrid = null;
        this.mRows = null;
        this.mRowSizeSecondary = null;
        this.mFirstVisiblePos = -1;
        this.mLastVisiblePos = -1;
        this.mRowSecondarySizeRefresh = false;
    }

    private void dispatchChildSelected() {
        long l = -1L;
        if (this.mChildSelectedListener == null) {
            return;
        }
        if (this.mFocusPosition != -1) {
            View localView = findViewByPosition(this.mFocusPosition);
            if (localView != null) {
                RecyclerView.ViewHolder localViewHolder = this.mBaseGridView.getChildViewHolder(localView);
                OnChildSelectedListener localOnChildSelectedListener = this.mChildSelectedListener;
                BaseGridView localBaseGridView = this.mBaseGridView;
                int i = this.mFocusPosition;
                if (localViewHolder != null) {
                    l = localViewHolder.getItemId();
                    localOnChildSelectedListener.onChildSelected(localBaseGridView, localView, i, l);
                }
            }
        }
        this.mChildSelectedListener.onChildSelected(this.mBaseGridView, null, -1, -1L);
    }

    private int findImmediateChildIndex(View paramView) {
        while ((paramView != null) && (paramView != this.mBaseGridView)) {
            int i = this.mBaseGridView.indexOfChild(paramView);
            if (i >= 0) {
                return i;
            }
            paramView = (View) paramView.getParent();
        }
        return -1;
    }

    private void forceRequestLayout() {
        ViewCompat.postOnAnimation(this.mBaseGridView, this.mRequestLayoutRunnable);
    }

    private boolean getAlignedPosition(View paramView, int[] paramArrayOfInt) {
        boolean bool = false;
        int j = getPrimarySystemScrollPosition(paramView);
        int i = getSecondarySystemScrollPosition(paramView);
        j -= this.mScrollOffsetPrimary;
        i -= this.mScrollOffsetSecondary;
        if ((j != 0) || (i != 0)) {
            paramArrayOfInt[0] = j;
            paramArrayOfInt[1] = i;
            bool = true;
        }
        return bool;
    }

    private int getMovement(int paramInt) {
//        int i = 0;
//        if (this.mOrientation == 0) {
//            switch (paramInt) {
//            }
//        }
//        for (; ; ) {
//            i = 17;
//            return i;
//            return 1;
//            return 2;
//            return 3;
//            if (this.mOrientation == 1) {
//                switch (paramInt) {
//                }
//            }
//        }
//        return 2;
//        return 3;
//        return 1;
        return 0;
    }

    private boolean getNoneAlignedPosition(View paramView, int[] paramArrayOfInt) {
//        Object localObject3 = null;
//        int i = getPositionByView(paramView);
//        int j = getViewMin(paramView);
//        int k = getViewMax(paramView);
//        int m = this.mWindowAlignment.mainAxis().getPaddingLow();
//        int n = this.mWindowAlignment.mainAxis().getClientSize();
//        int i1 = this.mGrid.getLocation(i).row;
//        Object localObject1;
//        Object localObject2;
//        if (j < m) {
//            if (this.mFocusScrollStrategy != 2) {
//                break label407;
//            }
//            localObject1 = paramView;
//            List localList;
//            do {
//                localObject2 = localObject3;
//                if (prependOneVisibleItem()) {
//                    break;
//                }
//                localList = this.mGrid.getItemPositionsInRows(this.mFirstVisiblePos, i)[i1];
//                localObject2 = findViewByPosition(((Integer) localList.get(0)).intValue());
//                localObject1 = localObject2;
//            } while (k - getViewMin((View) localObject2) <= n);
//            localObject1 = localObject2;
//            localObject2 = localObject3;
//            if (localList.size() > 1) {
//                localObject1 = findViewByPosition(((Integer) localList.get(1)).intValue());
//                localObject2 = localObject3;
//            }
//        }
//        for (; ; ) {
//            if (localObject1 != null) {
//                i = getViewMin((View) localObject1) - m;
//            }
//            for (; ; ) {
//                label200:
//                if (localObject1 != null) {
//                    paramView = (View) localObject1;
//                }
//                for (; ; ) {
//                    j = getSecondarySystemScrollPosition(paramView) - this.mScrollOffsetSecondary;
//                    if ((i == 0) && (j == 0)) {
//                        break label382;
//                    }
//                    paramArrayOfInt[0] = i;
//                    paramArrayOfInt[1] = j;
//                    return true;
//                    if (k <= n + m) {
//                        break label398;
//                    }
//                    if (this.mFocusScrollStrategy == 2) {
//                        label258:
//                        localObject1 = this.mGrid.getItemPositionsInRows(i, this.mLastVisiblePos)[i1];
//                        localObject1 = findViewByPosition(((Integer) ((List) localObject1).get(((List) localObject1).size() - 1)).intValue());
//                        if (getViewMax((View) localObject1) - j > n) {
//                            localObject1 = null;
//                        }
//                        for (; ; ) {
//                            if (localObject1 == null) {
//                                break label390;
//                            }
//                            localObject3 = null;
//                            localObject2 = localObject1;
//                            localObject1 = localObject3;
//                            break;
//                            if (!appendOneVisibleItem()) {
//                                break label258;
//                            }
//                        }
//                    }
//                    localObject1 = null;
//                    localObject2 = paramView;
//                    break;
//                    if (localObject2 == null) {
//                        break label384;
//                    }
//                    i = getViewMax((View) localObject2) - (m + n);
//                    break label200;
//                    if (localObject2 != null) {
//                        paramView = (View) localObject2;
//                    }
//                }
//                label382:
//                return false;
//                label384:
//                i = 0;
//            }
//            label390:
//            localObject2 = localObject1;
//            localObject1 = paramView;
//            continue;
//            label398:
//            localObject1 = null;
//            localObject2 = localObject3;
//            continue;
//            label407:
//            localObject1 = paramView;
//            localObject2 = localObject3;
//        }
        return false;
    }

    private int getPositionByIndex(int paramInt) {
        return getPositionByView(getChildAt(paramInt));
    }

    private int getPositionByView(View paramView) {
//        if (paramView == null) {
//            return -1;
//        }
//        paramView = (GridLayoutManager.LayoutParams) paramView.getLayoutParams();
//        if ((paramView == null) || (paramView.isItemRemoved())) {
//            return -1;
//        }
//        return paramView.getViewPosition();
        return 0;
    }

    private int getPrimarySystemScrollPosition(View paramView) {
//        boolean bool2 = true;
//        int j = this.mScrollOffsetPrimary;
//        int k = getViewCenter(paramView);
//        int m = getPositionByView(paramView);
//        int n = this.mGrid.getLocation(m).row;
//        boolean bool1;
//        int i1;
//        int i;
//        if (this.mFirstVisiblePos == 0) {
//            bool1 = true;
//            i1 = this.mLastVisiblePos;
//            if (this.mState != null) {
//                break label182;
//            }
//            i = getItemCount();
//            label64:
//            if (i1 != i - 1) {
//                break label193;
//            }
//            label72:
//            if ((!bool1) && (!bool2)) {
//                break label224;
//            }
//            i = getChildCount() - 1;
//            label89:
//            bool4 = bool2;
//            bool3 = bool1;
//            if (i < 0) {
//                break label232;
//            }
//            i1 = getPositionByIndex(i);
//            paramView = this.mGrid.getLocation(i1);
//            bool3 = bool2;
//            bool4 = bool1;
//            if (paramView != null) {
//                bool3 = bool2;
//                bool4 = bool1;
//                if (paramView.row == n) {
//                    if (i1 >= m) {
//                        break label199;
//                    }
//                    bool4 = false;
//                    bool3 = bool2;
//                }
//            }
//        }
//        for (; ; ) {
//            i -= 1;
//            bool2 = bool3;
//            bool1 = bool4;
//            break label89;
//            bool1 = false;
//            break;
//            label182:
//            i = this.mState.getItemCount();
//            break label64;
//            label193:
//            bool2 = false;
//            break label72;
//            label199:
//            bool3 = bool2;
//            bool4 = bool1;
//            if (i1 > m) {
//                bool3 = false;
//                bool4 = bool1;
//            }
//        }
//        label224:
//        boolean bool3 = bool1;
//        boolean bool4 = bool2;
//        label232:
//        return this.mWindowAlignment.mainAxis().getSystemScrollPos(j + k, bool3, bool4);
        return 0;
    }

    private int getRowSizeSecondary(int paramInt) {
        if (this.mFixedRowSizeSecondary != 0) {
            return this.mFixedRowSizeSecondary;
        }
        if (this.mRowSizeSecondary == null) {
            return 0;
        }
        return this.mRowSizeSecondary[paramInt];
    }

    private int getRowStartSecondary(int paramInt) {
        int i = 0;
        int j = 0;
        while (i < paramInt) {
            j += getRowSizeSecondary(i) + this.mMarginSecondary;
            i += 1;
        }
        return j;
    }

    private boolean getScrollPosition(View paramView, int[] paramArrayOfInt) {
        switch (this.mFocusScrollStrategy) {
            case RecyclerView.HORIZONTAL:
            case RecyclerView.VERTICAL:
                return getAlignedPosition(paramView, paramArrayOfInt);
        }
        return getNoneAlignedPosition(paramView, paramArrayOfInt);
    }

    private int getSecondarySystemScrollPosition(View paramView) {
        return 0;
//        boolean bool2 = true;
//        int i = this.mScrollOffsetSecondary;
//        int j = getViewCenterSecondary(paramView);
//        int k = getPositionByView(paramView);
//        k = this.mGrid.getLocation(k).row;
//        boolean bool1;
//        if (k == 0) {
//            bool1 = true;
//            if (k != this.mGrid.getNumRows() - 1) {
//                break label81;
//            }
//        }
//        for (; ; ) {
//            return this.mWindowAlignment.secondAxis().getSystemScrollPos(j + i, bool1, bool2);
//            bool1 = false;
//            break;
//            label81:
//            bool2 = false;
//        }
    }

    private int getSizeSecondary() {
        return getRowStartSecondary(this.mNumRows - 1) + getRowSizeSecondary(this.mNumRows - 1);
    }

    private String getTag() {
        return "GridLayoutManager:" + this.mBaseGridView.getId();
    }

    private int getViewCenter(View paramView) {
        if (this.mOrientation == 0) {
            return getViewCenterX(paramView);
        }
        return getViewCenterY(paramView);
    }

    private int getViewCenterSecondary(View paramView) {
        if (this.mOrientation == 0) {
            return getViewCenterY(paramView);
        }
        return getViewCenterX(paramView);
    }

    private int getViewCenterX(View paramView) {
        GridLayoutManager.LayoutParams localLayoutParams = (GridLayoutManager.LayoutParams) paramView.getLayoutParams();
        int i = localLayoutParams.getOpticalLeft(paramView);
        return localLayoutParams.getAlignX() + i;
    }

    private int getViewCenterY(View paramView) {
        GridLayoutManager.LayoutParams localLayoutParams = (GridLayoutManager.LayoutParams) paramView.getLayoutParams();
        int i = localLayoutParams.getOpticalTop(paramView);
        return localLayoutParams.getAlignY() + i;
    }

    private int getViewMax(View paramView) {
        if (this.mOrientation == 0) {
            return getOpticalRight(paramView);
        }
        return getOpticalBottom(paramView);
    }

    private int getViewMin(View paramView) {
        if (this.mOrientation == 0) {
            return getOpticalLeft(paramView);
        }
        return getOpticalTop(paramView);
    }

    private boolean gridOnRequestFocusInDescendantsAligned(RecyclerView paramRecyclerView, int paramInt, Rect paramRect) {
//        paramRecyclerView = findViewByPosition(this.mFocusPosition);
//        if (paramRecyclerView != null) {
//            boolean bool = paramRecyclerView.requestFocus(paramInt, paramRect);
//            if (!bool) {
//            }
//            return bool;
//        }
        return false;
    }

    private boolean gridOnRequestFocusInDescendantsUnaligned(RecyclerView paramRecyclerView, int paramInt, Rect paramRect) {
        return false;
//        int k = -1;
//        int j = getChildCount();
//        int i;
//        int m;
//        int n;
//        if ((paramInt & 0x2) != 0) {
//            k = 1;
//            i = 0;
//            m = this.mWindowAlignment.mainAxis().getPaddingLow();
//            n = this.mWindowAlignment.mainAxis().getClientSize();
//        }
//        for (; ; ) {
//            if (i == j) {
//                break label122;
//            }
//            paramRecyclerView = getChildAt(i);
//            if ((paramRecyclerView.getVisibility() == 0) && (getViewMin(paramRecyclerView) >= m) && (getViewMax(paramRecyclerView) <= n + m) && (paramRecyclerView.requestFocus(paramInt, paramRect))) {
//                return true;
//                i = j - 1;
//                j = -1;
//                break;
//            }
//            i += k;
//        }
//        label122:
//        return false;
    }

    private int init(int paramInt) {
//        int k = 0;
//        int m = 0;
//        int j = this.mState.getItemCount();
//        int i = paramInt;
//        if (paramInt == -1) {
//            i = paramInt;
//            if (j > 0) {
//                i = 0;
//            }
//        }
//        if ((this.mRows != null) && (this.mNumRows == this.mRows.length) && (this.mGrid != null) && (this.mGrid.getSize() > 0) && (i >= 0) && (i >= this.mGrid.getFirstIndex()) && (i <= this.mGrid.getLastIndex())) {
//            this.mGrid.stripDownTo(i);
//            int n = this.mGrid.getFirstIndex();
//            paramInt = this.mGrid.getLastIndex();
//            while (paramInt >= n) {
//                if (paramInt >= j) {
//                    this.mGrid.removeLast();
//                }
//                paramInt -= 1;
//            }
//            if (this.mGrid.getSize() == 0) {
//                j -= 1;
//                paramInt = 0;
//                for (; ; ) {
//                    i = j;
//                    if (paramInt >= this.mNumRows) {
//                        break;
//                    }
//                    this.mRows[paramInt].low = 0;
//                    this.mRows[paramInt].high = 0;
//                    paramInt += 1;
//                }
//            }
//            paramInt = 0;
//            while (paramInt < this.mNumRows) {
//                this.mRows[paramInt].low = Integer.MAX_VALUE;
//                this.mRows[paramInt].high = Integer.MIN_VALUE;
//                paramInt += 1;
//            }
//            j = this.mGrid.getFirstIndex();
//            n = this.mGrid.getLastIndex();
//            paramInt = i;
//            if (i > n) {
//                paramInt = this.mGrid.getLastIndex();
//            }
//            i = j;
//            Object localObject;
//            if (i <= n) {
//                localObject = findViewByPosition(i);
//                if (localObject == null) {
//                }
//                for (; ; ) {
//                    i += 1;
//                    break;
//                    int i1 = this.mGrid.getLocation(i).row;
//                    int i2 = getViewMin((View) localObject) + this.mScrollOffsetPrimary;
//                    if (i2 < this.mRows[i1].low) {
//                        localObject = this.mRows[i1];
//                        this.mRows[i1].high = i2;
//                        ((StaggeredGrid.Row) localObject).low = i2;
//                    }
//                }
//            }
//            i = this.mRows[this.mGrid.getLocation(j).row].low;
//            j = i;
//            if (i == Integer.MAX_VALUE) {
//                j = 0;
//            }
//            if (this.mState.didStructureChange()) {
//                k = m;
//                for (; ; ) {
//                    i = paramInt;
//                    if (k >= this.mNumRows) {
//                        break;
//                    }
//                    this.mRows[k].low = j;
//                    this.mRows[k].high = j;
//                    k += 1;
//                }
//            }
//            for (; ; ) {
//                i = paramInt;
//                if (k >= this.mNumRows) {
//                    break;
//                }
//                if (this.mRows[k].low == Integer.MAX_VALUE) {
//                    localObject = this.mRows[k];
//                    this.mRows[k].high = j;
//                    ((StaggeredGrid.Row) localObject).low = j;
//                }
//                k += 1;
//            }
//            detachAndScrapAttachedViews(this.mRecycler);
//            paramInt = i;
//            this.mGrid.setProvider(this.mGridProvider);
//            this.mGrid.setRows(this.mRows);
//            this.mLastVisiblePos = -1;
//            this.mFirstVisiblePos = -1;
//            initScrollController();
//            updateScrollSecondAxis();
//            return paramInt;
//        }
//        this.mRows = new StaggeredGrid.Row[this.mNumRows];
//        paramInt = 0;
//        while (paramInt < this.mNumRows) {
//            this.mRows[paramInt] = new StaggeredGrid.Row();
//            paramInt += 1;
//        }
//        this.mGrid = new StaggeredGridDefault();
//        if (j == 0) {
//            paramInt = -1;
//        }
//        for (; ; ) {
//            removeAndRecycleAllViews(this.mRecycler);
//            this.mScrollOffsetPrimary = 0;
//            this.mScrollOffsetSecondary = 0;
//            this.mWindowAlignment.reset();
//            break;
//            paramInt = i;
//            if (i >= j) {
//                paramInt = j - 1;
//            }
//        }
        return 0;
    }

    private void initScrollController() {
        int j;
        int i;
        if (this.mOrientation == 0) {
            j = getPaddingLeft() - this.mWindowAlignment.horizontal.getPaddingLow();
            i = getPaddingTop() - this.mWindowAlignment.vertical.getPaddingLow();
            this.mScrollOffsetPrimary -= j;
            this.mScrollOffsetSecondary -= i;
            if (this.mOrientation != 0) {
                this.mWindowAlignment.horizontal.setSize(getWidth());
                this.mWindowAlignment.vertical.setSize((int) (getHeight() * this.mPrimaryOverReach + 0.5F));
            }
            this.mWindowAlignment.horizontal.setSize((int) (getWidth() * this.mPrimaryOverReach + 0.5F));
            this.mWindowAlignment.vertical.setSize(getHeight());
        }
        j = getPaddingTop() - this.mWindowAlignment.vertical.getPaddingLow();
        i = getPaddingLeft() - this.mWindowAlignment.horizontal.getPaddingLow();
        this.mWindowAlignment.horizontal.setPadding(getPaddingLeft(), getPaddingRight());
        this.mWindowAlignment.vertical.setPadding(getPaddingTop(), getPaddingBottom());
        this.mSizePrimary = this.mWindowAlignment.mainAxis().getSize();
        return;
    }

    private void layoutChild(int paramInt1, View paramView, int paramInt2, int paramInt3, int paramInt4) {
//        int i;
//        int j;
//        int k;
//        int m;
//        if (this.mOrientation == 0) {
//            i = paramView.getMeasuredHeight();
//            j = i;
//            if (this.mFixedRowSizeSecondary > 0) {
//                j = Math.min(i, this.mFixedRowSizeSecondary);
//            }
//            k = this.mGravity & 0x70;
//            m = this.mGravity & 0x7;
//            if (this.mOrientation == 0) {
//                i = paramInt4;
//                if (k == 48) {
//                }
//            } else {
//                if ((this.mOrientation != 1) || (m != 3)) {
//                    break label142;
//                }
//                i = paramInt4;
//            }
//            label89:
//            if (this.mOrientation != 0) {
//                break label240;
//            }
//            paramInt4 = i + j;
//            paramInt1 = paramInt3;
//        }
//        for (; ; ) {
//            paramView.layout(paramInt2, i, paramInt1, paramInt4);
//            updateChildOpticalInsets(paramView, paramInt2, i, paramInt1, paramInt4);
//            updateChildAlignments(paramView);
//            return;
//            i = paramView.getMeasuredWidth();
//            break;
//            label142:
//            if (((this.mOrientation == 0) && (k == 80)) || ((this.mOrientation == 1) && (m == 5))) {
//                i = paramInt4 + (getRowSizeSecondary(paramInt1) - j);
//                break label89;
//            }
//            if ((this.mOrientation != 0) || (k != 16)) {
//                i = paramInt4;
//                if (this.mOrientation != 1) {
//                    break label89;
//                }
//                i = paramInt4;
//                if (m != 1) {
//                    break label89;
//                }
//            }
//            i = paramInt4 + (getRowSizeSecondary(paramInt1) - j) / 2;
//            break label89;
//            label240:
//            paramInt4 = i + j;
//            paramInt1 = paramInt2;
//            paramInt2 = i;
//            i = paramInt1;
//            paramInt1 = paramInt4;
//            paramInt4 = paramInt3;
//        }
    }

    private void leaveContext() {
        this.mRecycler = null;
        this.mState = null;
    }

    private void measureChild(View view) {
//        ViewGroup.LayoutParams localLayoutParams = view.getLayoutParams();
//        int j;
//        if (this.mRowSizeSecondaryRequested == -2) {
//            i = View.MeasureSpec.makeMeasureSpec(0, 0);
//            if (this.mOrientation != 0) {
//                break label74;
//            }
//            j = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), 0, localLayoutParams.width);
//        }
//        label74:
//        int k;
//        for (int i = ViewGroup.getChildMeasureSpec(i, 0, localLayoutParams.height); ; i = k) {
//            view.measure(j, i);
//            return;
//            i = View.MeasureSpec.makeMeasureSpec(this.mFixedRowSizeSecondary, 1073741824);
//            break;
//            k = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), 0, localLayoutParams.height);
//            j = ViewGroup.getChildMeasureSpec(i, 0, localLayoutParams.width);
//        }
    }

    private void measureScrapChild(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt) {
        View localView = this.mRecycler.getViewForPosition(paramInt1);
        if (localView != null) {
            GridLayoutManager.LayoutParams localLayoutParams = (GridLayoutManager.LayoutParams) localView.getLayoutParams();
            localView.measure(ViewGroup.getChildMeasureSpec(paramInt2, getPaddingLeft() + getPaddingRight(), localLayoutParams.width), ViewGroup.getChildMeasureSpec(paramInt3, getPaddingTop() + getPaddingBottom(), localLayoutParams.height));
            paramArrayOfInt[0] = localView.getMeasuredWidth();
            paramArrayOfInt[1] = localView.getMeasuredHeight();
            this.mRecycler.recycleView(localView);
        }
    }

    private boolean needsAppendVisibleItem() {
        return false;
//        boolean bool2 = false;
//        boolean bool1;
//        if (this.mLastVisiblePos < this.mFocusPosition) {
//            bool1 = true;
//            return bool1;
//        }
//        int i = this.mScrollOffsetPrimary;
//        int j = this.mSizePrimary + i;
//        i = 0;
//        for (; ; ) {
//            bool1 = bool2;
//            if (i >= this.mNumRows) {
//                break;
//            }
//            if (this.mRows[i].low == this.mRows[i].high) {
//                if (this.mRows[i].high < j) {
//                    return true;
//                }
//            } else if (this.mRows[i].high < j - this.mMarginPrimary) {
//                return true;
//            }
//            i += 1;
//        }
    }

    private boolean needsPrependVisibleItem() {
        return false;
//        boolean bool2 = false;
//        boolean bool1;
//        if (this.mFirstVisiblePos > this.mFocusPosition) {
//            bool1 = true;
//            return bool1;
//        }
//        int i = 0;
//        for (; ; ) {
//            bool1 = bool2;
//            if (i >= this.mNumRows) {
//                break;
//            }
//            if (this.mRows[i].low == this.mRows[i].high) {
//                if (this.mRows[i].low > this.mScrollOffsetPrimary) {
//                    return true;
//                }
//            } else if (this.mRows[i].low - this.mMarginPrimary > this.mScrollOffsetPrimary) {
//                return true;
//            }
//            i += 1;
//        }
    }

    private void offsetChildrenPrimary(int offset) {
        int i = 0;
        int j = 0;
        int k = getChildCount();
        if (this.mOrientation == 1) {
            i = j;
            while (i < k) {
                getChildAt(i).offsetTopAndBottom(offset);
                i += 1;
            }
        }
        while (i < k) {
            getChildAt(i).offsetLeftAndRight(offset);
            i += 1;
        }
    }

    private void offsetChildrenSecondary(int offset) {
        int i = 0;
        int j = 0;
        int k = getChildCount();
        if (this.mOrientation == 0) {
            i = j;
            while (i < k) {
                getChildAt(i).offsetTopAndBottom(offset);
                i += 1;
            }
        }
        while (i < k) {
            getChildAt(i).offsetLeftAndRight(offset);
            i += 1;
        }
    }

    private boolean prependOneVisibleItem() {
        while (this.mFirstVisiblePos > 0) {
            if (this.mFirstVisiblePos > this.mGrid.getFirstIndex()) {
                int i = this.mFirstVisiblePos - 1;
                int j = this.mGrid.getLocation(i).row;
                this.mGridProvider.createItem(i, j, false);
                if (j == 0) {
                    return false;
                }
            } else {
                this.mGrid.prependItems(this.mScrollOffsetPrimary);
                return false;
            }
        }
        return true;
    }

    private void prependVisibleItems() {
        while ((needsPrependVisibleItem()) && (!prependOneVisibleItem())) {
        }
    }

    private boolean processRowSizeSecondary(boolean paramBoolean) {
        return false;
//        if (this.mFixedRowSizeSecondary != 0) {
//            return false;
//        }
//        List[] arrayOfList;
//        int i1;
//        int n;
//        int m;
//        boolean bool;
//        if (this.mGrid == null) {
//            arrayOfList = null;
//            i1 = 0;
//            n = -1;
//            m = -1;
//            bool = false;
//            if (i1 >= this.mNumRows) {
//                break label363;
//            }
//            if (arrayOfList != null) {
//                break label121;
//            }
//        }
//        int i;
//        View localView;
//        int j;
//        label121:
//        for (int i2 = 0; ; i2 = arrayOfList[i1].size()) {
//            int i3 = 0;
//            for (i = -1; ; i = j) {
//                if (i3 >= i2) {
//                    break label191;
//                }
//                localView = findViewByPosition(((Integer) arrayOfList[i1].get(i3)).intValue());
//                if (localView != null) {
//                    break;
//                }
//                j = i;
//                i3 += 1;
//            }
//            arrayOfList = this.mGrid.getItemPositionsInRows(this.mFirstVisiblePos, this.mLastVisiblePos);
//            break;
//        }
//        if ((paramBoolean) && (localView.isLayoutRequested())) {
//            measureChild(localView);
//        }
//        if (this.mOrientation == 0) {
//        }
//        for (int k = localView.getMeasuredHeight(); ; k = localView.getMeasuredWidth()) {
//            j = k;
//            if (k > i) {
//                break;
//            }
//            j = i;
//            break;
//        }
//        label191:
//        if ((paramBoolean) && (i < 0) && (this.mState.getItemCount() > 0)) {
//            j = n;
//            i = m;
//            if (m < 0) {
//                j = n;
//                i = m;
//                if (n < 0) {
//                    if (this.mFocusPosition != -1) {
//                        break label347;
//                    }
//                    i = 0;
//                    label247:
//                    measureScrapChild(i, View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0), this.mMeasuredDimension);
//                    i = this.mMeasuredDimension[0];
//                    j = this.mMeasuredDimension[1];
//                }
//            }
//            if (this.mOrientation == 0) {
//                k = j;
//            }
//        }
//        for (; ; ) {
//            m = k;
//            if (k < 0) {
//                m = 0;
//            }
//            if (this.mRowSizeSecondary[i1] != m) {
//                this.mRowSizeSecondary[i1] = m;
//                bool = true;
//            }
//            i1 += 1;
//            n = j;
//            m = i;
//            break;
//            label347:
//            i = this.mFocusPosition;
//            break label247;
//            k = i;
//            continue;
//            label363:
//            return bool;
//            k = i;
//            j = n;
//            i = m;
//        }
    }

    private void removeChildAt(int paramInt) {
        View localView = findViewByPosition(paramInt);
        if (localView != null) {
            this.mChildrenStates.saveOffscreenView(localView, paramInt);
            removeAndRecycleView(localView, this.mRecycler);
        }
    }

    private void removeInvisibleViewsAtEnd() {
        int i = 0;
        if (!this.mPruneChild) {
            for (i = 0; (this.mLastVisiblePos > this.mFirstVisiblePos) && (this.mLastVisiblePos > this.mFocusPosition) && (getViewMin(findViewByPosition(this.mLastVisiblePos)) > this.mSizePrimary); i = 1) {
                removeChildAt(this.mLastVisiblePos);
                this.mLastVisiblePos -= 1;
            }
        }
        if (i == 0) {
            return;
        }
        updateRowsMinMax();
    }

    private void removeInvisibleViewsAtFront() {
        int i = 0;
        if (!this.mPruneChild) {
            for (i = 0; (this.mLastVisiblePos > this.mFirstVisiblePos) && (this.mFirstVisiblePos < this.mFocusPosition) && (getViewMax(findViewByPosition(this.mFirstVisiblePos)) < 0); i = 1) {
                removeChildAt(this.mFirstVisiblePos);
                this.mFirstVisiblePos += 1;
            }
        }
        if (i == 0) {
            return;
        }
        updateRowsMinMax();
    }

    private void saveContext(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((this.mRecycler != null) || (this.mState != null)) {
            Log.e("GridLayoutManager", "Recycler information was not released, bug!");
        }
        this.mRecycler = recycler;
        this.mState = state;
    }

    private int scrollDirectionPrimary(int paramInt) {
        return 0;
//        int j = 1;
//        int k;
//        int i;
//        if (paramInt > 0) {
//            if (this.mWindowAlignment.mainAxis().isMaxUnknown()) {
//                break label230;
//            }
//            k = this.mWindowAlignment.mainAxis().getMaxScroll();
//            i = paramInt;
//            if (this.mScrollOffsetPrimary + paramInt > k) {
//                i = k - this.mScrollOffsetPrimary;
//            }
//            paramInt = i;
//        }
//        label156:
//        label170:
//        label209:
//        label214:
//        label225:
//        label230:
//        for (; ; ) {
//            if (paramInt == 0) {
//                return 0;
//                if ((paramInt < 0) && (!this.mWindowAlignment.mainAxis().isMinUnknown())) {
//                    i = this.mWindowAlignment.mainAxis().getMinScroll();
//                    if (this.mScrollOffsetPrimary + paramInt < i) {
//                        paramInt = i - this.mScrollOffsetPrimary;
//                    }
//                }
//            } else {
//                offsetChildrenPrimary(-paramInt);
//                this.mScrollOffsetPrimary += paramInt;
//                if (this.mInLayout) {
//                    return paramInt;
//                }
//                i = getChildCount();
//                if (paramInt > 0) {
//                    appendVisibleItems();
//                    if (getChildCount() <= i) {
//                        break label209;
//                    }
//                    i = 1;
//                    k = getChildCount();
//                    if (paramInt <= 0) {
//                        break label214;
//                    }
//                    removeInvisibleViewsAtFront();
//                    if (getChildCount() >= k) {
//                        break label225;
//                    }
//                }
//                for (; ; ) {
//                    if ((i | j) != 0) {
//                        updateRowSecondarySizeRefresh();
//                    }
//                    this.mBaseGridView.invalidate();
//                    return paramInt;
//                    if (paramInt >= 0) {
//                        break;
//                    }
//                    prependVisibleItems();
//                    break;
//                    i = 0;
//                    break label156;
//                    if (paramInt >= 0) {
//                        break label170;
//                    }
//                    removeInvisibleViewsAtEnd();
//                    break label170;
//                    j = 0;
//                }
//            }
//        }
    }

    private int scrollDirectionSecondary(int offset) {
        if (offset == 0) {
            return 0;
        }
        offsetChildrenSecondary(-offset);
        this.mScrollOffsetSecondary += offset;
        this.mBaseGridView.invalidate();
        return offset;
    }

    private void scrollGrid(int toX, int toY, boolean immediate) {
        if (this.mInLayout) {
            scrollDirectionPrimary(toX);
            scrollDirectionSecondary(toY);
            return;
        }
        if (this.mOrientation == RecyclerView.HORIZONTAL) {
            int i = toX;
            toX = toY;
            toY = i;
        }
        if (!immediate) {
            this.mBaseGridView.smoothScrollBy(toX, toY);
            return;
        }
        this.mBaseGridView.scrollBy(toX, toY);
    }

    private void scrollToView(View view, boolean immediate) {
        int i = getPositionByView(view);
        if (i != this.mFocusPosition) {
            this.mFocusPosition = i;
            this.mFocusPositionOffset = 0;
            if (!this.mInLayout) {
                dispatchChildSelected();
            }
        }
        if (this.mBaseGridView.isChildrenDrawingOrderEnabledInternal()) {
            this.mBaseGridView.invalidate();
        }
        if (view == null) {
            return;
        }
        do {
            if ((!view.hasFocus()) && (this.mBaseGridView.hasFocus())) {
                view.requestFocus();
                return;
            }
        } while ((!this.mScrollEnabled) || (!getScrollPosition(view, this.mTempDeltas)));
        scrollGrid(this.mTempDeltas[0], this.mTempDeltas[1], immediate);
    }

    private void updateChildAlignments() {
        int i = 0;
        int j = getChildCount();
        while (i < j) {
            updateChildAlignments(getChildAt(i));
            i += 1;
        }
    }

    private void updateChildAlignments(View view) {
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.setAlignX(this.mItemAlignment.horizontal.getAlignmentPosition(view));
        lp.setAlignY(this.mItemAlignment.vertical.getAlignmentPosition(view));
    }

    private void updateChildOpticalInsets(View view, int leftInset, int topInset, int rightInset, int bottomInset) {
        ((GridLayoutManager.LayoutParams) view.getLayoutParams()).setOpticalInsets(leftInset - view.getLeft(), topInset - view.getTop(), view.getRight() - rightInset, view.getBottom() - bottomInset);
    }

    private void updateRowSecondarySizeRefresh() {
        this.mRowSecondarySizeRefresh = processRowSizeSecondary(false);
        if (this.mRowSecondarySizeRefresh) {
            forceRequestLayout();
        }
    }

    private void updateRowsMinMax() {
        if (this.mFirstVisiblePos < 0) {
            return;
        }
        int i = 0;
        while (i < this.mNumRows) {
            this.mRows[i].low = Integer.MAX_VALUE;
            this.mRows[i].high = Integer.MIN_VALUE;
            i += 1;
        }
        i = this.mFirstVisiblePos;
        while (i <= this.mLastVisiblePos) {
            View localView = findViewByPosition(i);
            int j = this.mGrid.getLocation(i).row;
            int k = getViewMin(localView) + this.mScrollOffsetPrimary;
            if (k < this.mRows[j].low) {
                this.mRows[j].low = k;
            }
            k = getViewMax(localView) + this.mScrollOffsetPrimary;
            if (k > this.mRows[j].high) {
                this.mRows[j].high = k;
            }
            i += 1;
        }
    }

    private void updateScrollMax() {
//        int i = 0;
//        if (this.mLastVisiblePos < 0) {
//            return;
//        }
//        if (this.mLastVisiblePos == this.mState.getItemCount() - 1) {
//        }
//        int k;
//        int m;
//        for (int j = 1; ; j = 0) {
//            boolean bool = this.mWindowAlignment.mainAxis().isMaxUnknown();
//            if ((j == 0) && (bool)) {
//                break;
//            }
//            k = Integer.MIN_VALUE;
//            m = -1;
//            while (i < this.mRows.length) {
//                int n = k;
//                if (this.mRows[i].high > k) {
//                    n = this.mRows[i].high;
//                    m = i;
//                }
//                i += 1;
//                k = n;
//            }
//        }
//        i = this.mLastVisiblePos;
//        label119:
//        if (i >= this.mFirstVisiblePos) {
//            StaggeredGrid.Location localLocation = this.mGrid.getLocation(i);
//            if ((localLocation != null) && (localLocation.row == m)) {
//                m = this.mWindowAlignment.mainAxis().getMaxEdge();
//                this.mWindowAlignment.mainAxis().setMaxEdge(k);
//                i = getPrimarySystemScrollPosition(findViewByPosition(i));
//                this.mWindowAlignment.mainAxis().setMaxEdge(m);
//            }
//        }
//        for (; ; ) {
//            if (j != 0) {
//                this.mWindowAlignment.mainAxis().setMaxEdge(k);
//                this.mWindowAlignment.mainAxis().setMaxScroll(i);
//                return;
//                i -= 1;
//                break label119;
//            }
//            if (i <= this.mWindowAlignment.mainAxis().getMaxScroll()) {
//                break;
//            }
//            this.mWindowAlignment.mainAxis().invalidateScrollMax();
//            return;
//            i = Integer.MAX_VALUE;
//        }
    }

    private void updateScrollMin() {
//        int i = 0;
//        if (this.mFirstVisiblePos < 0) {
//            return;
//        }
//        if (this.mFirstVisiblePos == 0) {
//        }
//        int k;
//        int m;
//        for (int j = 1; ; j = 0) {
//            boolean bool = this.mWindowAlignment.mainAxis().isMinUnknown();
//            if ((j == 0) && (bool)) {
//                break;
//            }
//            k = Integer.MAX_VALUE;
//            m = -1;
//            while (i < this.mRows.length) {
//                int n = k;
//                if (this.mRows[i].low < k) {
//                    n = this.mRows[i].low;
//                    m = i;
//                }
//                i += 1;
//                k = n;
//            }
//        }
//        i = this.mFirstVisiblePos;
//        for (; ; ) {
//            if (j != 0) {
//                this.mWindowAlignment.mainAxis().setMinEdge(k);
//                this.mWindowAlignment.mainAxis().setMinScroll(i);
//                return;
//                i += 1;
//                if (i <= this.mLastVisiblePos) {
//                    StaggeredGrid.Location localLocation = this.mGrid.getLocation(i);
//                    if ((localLocation != null) && (localLocation.row == m)) {
//                        m = this.mWindowAlignment.mainAxis().getMinEdge();
//                        this.mWindowAlignment.mainAxis().setMinEdge(k);
//                        i = getPrimarySystemScrollPosition(findViewByPosition(i));
//                        this.mWindowAlignment.mainAxis().setMinEdge(m);
//                    }
//                }
//            }
//            if (i >= this.mWindowAlignment.mainAxis().getMinScroll()) {
//                break;
//            }
//            this.mWindowAlignment.mainAxis().invalidateScrollMin();
//            return;
//            i = Integer.MIN_VALUE;
//        }
    }

    private void updateScrollSecondAxis() {
        this.mWindowAlignment.secondAxis().setMinEdge(0);
        this.mWindowAlignment.secondAxis().setMaxEdge(getSizeSecondary());
    }

    public boolean canScrollHorizontally() {
        return (this.mOrientation == 0) || (this.mNumRows > 1);
    }

    public boolean canScrollVertically() {
        return (this.mOrientation == 1) || (this.mNumRows > 1);
    }

    protected void fastRelayout(boolean paramBoolean) {
//        initScrollController();
//        List[] arrayOfList = this.mGrid.getItemPositionsInRows(this.mFirstVisiblePos, this.mLastVisiblePos);
//        int j = 0;
//        List localList;
//        int i1;
//        int i2;
//        int i3;
//        int k;
//        label66:
//        int m;
//        int i4;
//        int i;
//        View localView1;
//        int n;
//        if (j < this.mNumRows) {
//            localList = arrayOfList[j];
//            i1 = getRowStartSecondary(j);
//            i2 = this.mScrollOffsetSecondary;
//            i3 = localList.size();
//            k = 0;
//            if (k < i3) {
//                m = ((Integer) localList.get(k)).intValue();
//                View localView2 = findViewByPosition(m);
//                i4 = getViewMin(localView2);
//                if (this.mOrientation == 0) {
//                }
//                for (i = localView2.getMeasuredWidth(); ; i = localView2.getMeasuredHeight()) {
//                    localView1 = localView2;
//                    if (((GridLayoutManager.LayoutParams) localView2.getLayoutParams()).viewNeedsUpdate()) {
//                        n = this.mBaseGridView.indexOfChild(localView2);
//                        detachAndScrapView(localView2, this.mRecycler);
//                        localView1 = getViewForPosition(m);
//                        addView(localView1, n);
//                    }
//                    if (localView1.isLayoutRequested()) {
//                        measureChild(localView1);
//                    }
//                    if (this.mOrientation != 0) {
//                        break label291;
//                    }
//                    m = i4 + localView1.getMeasuredWidth();
//                    n = localView1.getMeasuredWidth() - i;
//                    i = m;
//                    if (n == 0) {
//                        break label441;
//                    }
//                    i = k + 1;
//                    while (i < i3) {
//                        findViewByPosition(((Integer) localList.get(i)).intValue()).offsetLeftAndRight(n);
//                        i += 1;
//                    }
//                }
//            }
//        }
//        for (; ; ) {
//            layoutChild(j, localView1, i4, m, i1 - i2);
//            k += 1;
//            break label66;
//            label291:
//            n = i4 + localView1.getMeasuredHeight();
//            int i5 = localView1.getMeasuredHeight() - i;
//            i = n;
//            if (i5 != 0) {
//                m = k + 1;
//                for (; ; ) {
//                    i = n;
//                    if (m >= i3) {
//                        break;
//                    }
//                    findViewByPosition(((Integer) localList.get(m)).intValue()).offsetTopAndBottom(i5);
//                    m += 1;
//                }
//                j += 1;
//                break;
//                updateRowsMinMax();
//                appendVisibleItems();
//                prependVisibleItems();
//                updateRowsMinMax();
//                updateScrollMin();
//                updateScrollMax();
//                updateScrollSecondAxis();
//                if (paramBoolean) {
//                    if (this.mFocusPosition != -1) {
//                        break label432;
//                    }
//                }
//                label432:
//                for (i = 0; ; i = this.mFocusPosition) {
//                    scrollToView(findViewByPosition(i), false);
//                    return;
//                }
//            }
//            label441:
//            m = i;
//        }
    }

    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attrs) {
        return new GridLayoutManager.LayoutParams(context, attrs);
    }

    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if ((lp instanceof GridLayoutManager.LayoutParams)) {
            return new GridLayoutManager.LayoutParams((GridLayoutManager.LayoutParams) lp);
        }
        if ((lp instanceof RecyclerView.LayoutParams)) {
            return new GridLayoutManager.LayoutParams((RecyclerView.LayoutParams) lp);
        }
        if ((lp instanceof ViewGroup.MarginLayoutParams)) {
            return new GridLayoutManager.LayoutParams((ViewGroup.MarginLayoutParams) lp);
        }
        return new GridLayoutManager.LayoutParams(lp);
    }

    int getChildDrawingOrder(RecyclerView recyclerView, int childCount, int index) {
        //Fixme: GridLayoutManager -> getChildDrawingOrder
//        View chidView = findViewByPosition(this.mFocusPosition);
//        if (chidView == null) {
//        }
//        int i;
//        do {
//            return index;
//            i = recyclerView.indexOfChild(chidView);
//        } while (index < i);
//        if (index < childCount - 1) {
//            return i + childCount - 1 - index;
//        }
//        return i;
        return index;
    }

    public int getFocusScrollStrategy() {
        return this.mFocusScrollStrategy;
    }

    public int getHorizontalMargin() {
        return this.mHorizontalMargin;
    }

    public int getItemAlignmentOffset() {
        return this.mItemAlignment.mainAxis().getItemAlignmentOffset();
    }

    public float getItemAlignmentOffsetPercent() {
        return this.mItemAlignment.mainAxis().getItemAlignmentOffsetPercent();
    }

    public int getItemAlignmentViewId() {
        return this.mItemAlignment.mainAxis().getItemAlignmentViewId();
    }

    final int getOpticalBottom(View view) {
        return ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getOpticalBottom(view);
    }

    final int getOpticalLeft(View view) {
        return ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getOpticalLeft(view);
    }

    final int getOpticalRight(View view) {
        return ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getOpticalRight(view);
    }

    final int getOpticalTop(View view) {
        return ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getOpticalTop(view);
    }

    public boolean getPruneChild() {
        return this.mPruneChild;
    }

    int getScrollOffsetX() {
        if (this.mOrientation == 0) {
            return this.mScrollOffsetPrimary;
        }
        return this.mScrollOffsetSecondary;
    }

    int getScrollOffsetY() {
        if (this.mOrientation == 0) {
            return this.mScrollOffsetSecondary;
        }
        return this.mScrollOffsetPrimary;
    }

    public int getSelection() {
        return this.mFocusPosition;
    }

    public int getVerticalMargin() {
        return this.mVerticalMargin;
    }

    protected View getViewForPosition(int position) {
        return this.mRecycler.getViewForPosition(position);
    }

    public void getViewSelectedOffsets(View view, int[] ints) {
        if (this.mOrientation == RecyclerView.HORIZONTAL) {
            ints[0] = (getPrimarySystemScrollPosition(view) - this.mScrollOffsetPrimary);
            ints[1] = (getSecondarySystemScrollPosition(view) - this.mScrollOffsetSecondary);
            return;
        }
        ints[1] = (getPrimarySystemScrollPosition(view) - this.mScrollOffsetPrimary);
        ints[0] = (getSecondarySystemScrollPosition(view) - this.mScrollOffsetSecondary);
    }

    public int getWindowAlignment() {
        return this.mWindowAlignment.mainAxis().getWindowAlignment();
    }

    public int getWindowAlignmentOffset() {
        return this.mWindowAlignment.mainAxis().getWindowAlignmentOffset();
    }

    public float getWindowAlignmentOffsetPercent() {
        return this.mWindowAlignment.mainAxis().getWindowAlignmentOffsetPercent();
    }

    boolean gridOnRequestFocusInDescendants(RecyclerView recyclerView, int i, Rect rect) {
        switch (this.mFocusScrollStrategy) {
            case RecyclerView.HORIZONTAL:
            case RecyclerView.VERTICAL:
                return gridOnRequestFocusInDescendantsAligned(recyclerView, i, rect);
            default:
                return gridOnRequestFocusInDescendantsUnaligned(recyclerView, i, rect);
        }
    }

    protected boolean hasDoneFirstLayout() {
        return this.mGrid != null;
    }

    boolean hasPreviousViewInSameRow(int pos) {
        //Fixme: GridLayoutManager -> hasPreviousViewInSameRow
        boolean hasInSameRow;
        if ((this.mGrid == null) || (pos == -1)) {
            hasInSameRow = false;
        }
//        do {
//            hasInSameRow = true;
//            return hasInSameRow;
//        } while (this.mFirstVisiblePos > 0);
        int j = this.mGrid.getLocation(pos).row;
        int i = getChildCount() - 1;
        for (; ; ) {
            if (i < 0) {
                return false;
            }
            int k = getPositionByIndex(i);
            StaggeredGrid.Location location = this.mGrid.getLocation(k);
            if ((location != null) && (location.row == j)) {
                hasInSameRow = true;
                if (k < pos) {
                    break;
                }
            }
            i -= 1;
        }
        return hasInSameRow;
    }

    boolean isFocusSearchDisabled() {
        return this.mFocusSearchDisabled;
    }

    public boolean isItemAlignmentOffsetWithPadding() {
        return this.mItemAlignment.mainAxis().isItemAlignmentOffsetWithPadding();
    }

    public boolean isScrollEnabled() {
        return this.mScrollEnabled;
    }

    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        if (oldAdapter != null) {
            discardLayoutInfo();
            this.mFocusPosition = -1;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.clear();
        }
        super.onAdapterChanged(oldAdapter, newAdapter);
    }

    public boolean onAddFocusables(RecyclerView recyclerView, ArrayList views, int direction, int focusableMode) {
        //Fixme: GridLayoutManager -> onAddFocusables
//        int j = 0;
//        if (this.mFocusSearchDisabled) {
//            return true;
//        }
//        int m;
//        int n;
//        int i1;
//        int i;
//        label121:
//        int k;
//        label138:
//        label167:
//        label179:
//        Object localObject;
//        if (recyclerView.hasFocus()) {
//            m = getMovement(direction);
//            if ((m != 0) && (m != 1)) {
//                return false;
//            }
//            n = getPositionByIndex(findImmediateChildIndex(recyclerView.findFocus()));
//            if (n != -1) {
//                findViewByPosition(n).addFocusables(views, direction, focusableMode);
//            }
//            int i2;
//            if ((this.mGrid != null) && (n != -1)) {
//                j = this.mGrid.getLocation(n).row;
//                if (this.mGrid == null) {
//                    break label267;
//                }
//                i1 = views.size();
//                i2 = getChildCount();
//                i = 0;
//                if (i >= i2) {
//                    break label267;
//                }
//                if (m != 1) {
//                    break label167;
//                }
//                k = i;
//                recyclerView = getChildAt(k);
//                if (recyclerView.getVisibility() == 0) {
//                    break label179;
//                }
//            }
//            do {
//                do {
//                    i += 1;
//                    break label121;
//                    j = -1;
//                    break;
//                    k = i2 - 1 - i;
//                    break label138;
//                    k = getPositionByIndex(k);
//                    localObject = this.mGrid.getLocation(k);
//                }
//                while (((j != -1) && ((localObject == null) || (((StaggeredGrid.Location) localObject).row != j))) || ((n != -1) && ((m != 1) || (k <= n)) && ((m != 0) || (k >= n))));
//                recyclerView.addFocusables(views, direction, focusableMode);
//            } while (views.size() <= i1);
//        }
//        for (; ; ) {
//            label267:
//            return true;
//            if (this.mFocusScrollStrategy != 0) {
//                m = this.mWindowAlignment.mainAxis().getPaddingLow();
//                n = this.mWindowAlignment.mainAxis().getClientSize();
//                k = views.size();
//                i1 = getChildCount();
//                i = 0;
//                while (i < i1) {
//                    localObject = getChildAt(i);
//                    if ((((View) localObject).getVisibility() == 0) && (getViewMin((View) localObject) >= m) && (getViewMax((View) localObject) <= n + m)) {
//                        ((View) localObject).addFocusables(views, direction, focusableMode);
//                    }
//                    i += 1;
//                }
//                if (views.size() == k) {
//                    m = getChildCount();
//                    i = j;
//                    while (i < m) {
//                        localObject = getChildAt(i);
//                        if (((View) localObject).getVisibility() == 0) {
//                            ((View) localObject).addFocusables(views, direction, focusableMode);
//                        }
//                        i += 1;
//                    }
//                    if (views.size() != k) {
//                        return true;
//                    }
//                } else {
//                    return true;
//                }
//            }
//            if (recyclerView.isFocusable()) {
//                views.add(recyclerView);
//            }
//        }
        return true;
    }

    public View onFocusSearchFailed(View focused, int direction, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //Fixme: GridLayoutManagre -> onFocusSearchFailed
//        Object localObject2 = null;
//        Object localObject1 = null;
//        Log.i("GridLayoutManager", "onFocusSearchFailed" + direction);
//        int i = getMovement(direction);
//        if (this.mNumRows == 1) {
//            if (i == 1) {
//                direction = this.mFocusPosition + this.mNumRows;
//                if (direction < getItemCount()) {
//                    setSelectionSmooth(this.mBaseGridView, direction);
//                    localObject1 = focused;
//                }
//            }
//        }
//        while (this.mNumRows <= 1) {
//            do {
//                for (; ; ) {
//                    return (View) localObject1;
//                    if (this.mFocusOutEnd) {
//                        focused = null;
//                    }
//                }
//            } while (i != 0);
//            direction = this.mFocusPosition - this.mNumRows;
//            if (direction >= 0) {
//                setSelectionSmooth(this.mBaseGridView, direction);
//            }
//            for (; ; ) {
//                return focused;
//                if (this.mFocusOutFront) {
//                    focused = null;
//                }
//            }
//        }
//        saveContext(recycler, state);
//        FocusFinder focusFinder = FocusFinder.getInstance();
//        if (i == 1) {
//            for (View view = null; ; view = focusFinder.findNextFocus(this.mBaseGridView, focused, direction)) {
//                recycler = view;
//                if (view != null) {
//                    break;
//                }
//                recycler = view;
//                if (appendOneVisibleItem()) {
//                    break;
//                }
//            }
//        }
//        if (i == 0) {
//            for (View view = null; ; view = focusFinder.findNextFocus(this.mBaseGridView, focused, direction)) {
//                recycler = view;
//                if (view != null) {
//                    break;
//                }
//                recycler = view;
//                if (prependOneVisibleItem()) {
//                    break;
//                }
//            }
//        }
//        recycler = null;
//        if (recycler == null) {
//            if (i == 0) {
//                if (this.mFocusOutFront) {
//                    recycler = (RecyclerView.Recycler) localObject2;
//                }
//            }
//        }
//        for (; ; ) {
//            leaveContext();
//            return recycler;
//            recycler = focused;
//            continue;
//            if (i == 1) {
//                recycler = (RecyclerView.Recycler) localObject2;
//                if (!this.mFocusOutEnd) {
//                    recycler = focused;
//                }
//            }
//        }
        return null;
    }

    public View onInterceptFocusSearch(View focused, int direction) {
        Log.i("GridLayoutManager", "onInterceptFocusSearch: " + direction);
        if (this.mFocusSearchDisabled) {
            return focused;
        }
        return null;
    }

    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        if ((this.mFocusPosition != -1) && (this.mFocusPositionOffset != Integer.MIN_VALUE) && (getChildAt(this.mFocusPosition) != null) && (positionStart <= this.mFocusPosition + this.mFocusPositionOffset)) {
            this.mFocusPositionOffset += itemCount;
        }
        this.mChildrenStates.clear();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.mFocusPositionOffset = 0;
        this.mChildrenStates.clear();
    }

    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        int i;
        if ((this.mFocusPosition != -1) && (this.mFocusPositionOffset != Integer.MIN_VALUE) && (getChildAt(this.mFocusPosition) != null)) {
            i = this.mFocusPosition + this.mFocusPositionOffset;
            if ((from > i) || (i >= from + itemCount)) {
                if ((from < i) && (to > i - itemCount)) {
                    this.mFocusPositionOffset -= itemCount;
                } else if ((from > i) && (to < i)) {
                    this.mFocusPositionOffset += itemCount;
                }
            }
            this.mFocusPositionOffset += to - from;
        }
        for (; ; ) {
            this.mChildrenStates.clear();
            return;
        }
    }

    public void onItemsRemoved(RecyclerView recyclerView, int position, int itemCount) {
        if ((this.mFocusPosition != -1) && (this.mFocusPositionOffset != Integer.MIN_VALUE) && (getChildAt(this.mFocusPosition) != null)) {
            int i = this.mFocusPosition + this.mFocusPositionOffset;
            if (position <= i) {
                if (position + itemCount <= i) {
                    for (this.mFocusPositionOffset = Integer.MIN_VALUE; ; this.mFocusPositionOffset -= itemCount) {
                        this.mChildrenStates.clear();
                        return;
                    }
                }
            }
        }
    }

    public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {
        int i = paramInt1;
        while (i < paramInt1 + paramInt2) {
            this.mChildrenStates.remove(i);
            i += 1;
        }
    }

    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //Fixme: GridLayoutManager -> onLayoutChildren
//        int k = 1;
//        if (this.mNumRows == 0) {
//        }
//        while (state.getItemCount() < 0) {
//            return;
//        }
//        if (!this.mLayoutEnabled) {
//            discardLayoutInfo();
//            removeAndRecycleAllViews(recycler);
//            return;
//        }
//        this.mInLayout = true;
//        boolean bool1;
//        int j;
//        int i;
//        if ((!isSmoothScrolling()) && (this.mFocusScrollStrategy == 0)) {
//            bool1 = true;
//            if ((this.mFocusPosition != -1) && (this.mFocusPositionOffset != Integer.MIN_VALUE)) {
//                this.mFocusPosition += this.mFocusPositionOffset;
//                this.mFocusPositionOffset = 0;
//            }
//            saveContext(recycler, state);
//            if ((this.mFocusPosition == -1) || (!bool1)) {
//                break label567;
//            }
//            recycler = findViewByPosition(this.mFocusPosition);
//            if (recycler == null) {
//                break label567;
//            }
//            j = this.mWindowAlignment.mainAxis().getSystemScrollPos(this.mScrollOffsetPrimary + getViewCenter(recycler), false, false) - this.mScrollOffsetPrimary;
//            state = this.mWindowAlignment.secondAxis();
//            i = this.mScrollOffsetSecondary;
//            i = state.getSystemScrollPos(getViewCenterSecondary(recycler) + i, false, false) - this.mScrollOffsetSecondary;
//        }
//        for (; ; ) {
//            boolean bool2 = hasDoneFirstLayout();
//            int i1 = this.mFocusPosition;
//            int m;
//            if ((!this.mState.didStructureChange()) && (!this.mForceFullLayout) && (bool2)) {
//                fastRelayout(bool1);
//                m = i;
//                i = k;
//                label232:
//                this.mForceFullLayout = false;
//                if (bool1) {
//                    scrollDirectionPrimary(-j);
//                    scrollDirectionSecondary(-m);
//                }
//                appendVisibleItems();
//                prependVisibleItems();
//                removeInvisibleViewsAtFront();
//                removeInvisibleViewsAtEnd();
//                if (!this.mRowSecondarySizeRefresh) {
//                    break label560;
//                }
//                this.mRowSecondarySizeRefresh = false;
//            }
//            for (; ; ) {
//                if ((i != 0) && (this.mFocusPosition != i1)) {
//                    dispatchChildSelected();
//                }
//                this.mInLayout = false;
//                leaveContext();
//                return;
//                bool1 = false;
//                break;
//                bool2 = this.mBaseGridView.hasFocus();
//                this.mFocusPosition = init(this.mFocusPosition);
//                if (this.mFocusPosition != i1) {
//                }
//                this.mWindowAlignment.mainAxis().invalidateScrollMin();
//                this.mWindowAlignment.mainAxis().invalidateScrollMax();
//                if (this.mGrid.getSize() == 0) {
//                    this.mGrid.setStart(this.mFocusPosition, -1);
//                    m = 0;
//                    k = 0;
//                    appendVisibleItems();
//                    prependVisibleItems();
//                    do {
//                        updateScrollMin();
//                        updateScrollMax();
//                        i = this.mFirstVisiblePos;
//                        j = this.mLastVisiblePos;
//                        recycler = findViewByPosition(this.mFocusPosition);
//                        scrollToView(recycler, false);
//                        if ((recycler != null) && (bool2)) {
//                            recycler.requestFocus();
//                        }
//                        appendVisibleItems();
//                        prependVisibleItems();
//                        removeInvisibleViewsAtFront();
//                        removeInvisibleViewsAtEnd();
//                    } while ((this.mFirstVisiblePos != i) || (this.mLastVisiblePos != j));
//                    i = 0;
//                    j = k;
//                    break label232;
//                }
//                int n = this.mGrid.getFirstIndex();
//                int i2 = this.mGrid.getLastIndex();
//                for (; ; ) {
//                    m = i;
//                    k = j;
//                    if (n > i2) {
//                        break;
//                    }
//                    this.mGridProvider.createItem(n, this.mGrid.getLocation(n).row, true);
//                    n += 1;
//                }
//                label560:
//                updateRowSecondarySizeRefresh();
//            }
//            label567:
//            i = 0;
//            j = 0;
//        }
    }

    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        //Fixme: GridLayoutManager -> onMeasure
//        int m = 1;
//        saveContext(recycler, state);
//        int i;
//        int k;
//        int j;
//        if (this.mOrientation == 0) {
//            i = View.MeasureSpec.getSize(widthSpec);
//            widthSpec = View.MeasureSpec.getSize(heightSpec);
//            k = View.MeasureSpec.getMode(heightSpec);
//            j = getPaddingTop() + getPaddingBottom();
//            heightSpec = widthSpec;
//            label49:
//            this.mMaxSizeSecondary = heightSpec;
//            if (this.mRowSizeSecondaryRequested != -2) {
//                break label269;
//            }
//            if (this.mNumRowsRequested != 0) {
//                break label208;
//            }
//        }
//        label208:
//        for (widthSpec = 1; ; widthSpec = this.mNumRowsRequested) {
//            this.mNumRows = widthSpec;
//            this.mFixedRowSizeSecondary = 0;
//            if ((this.mRowSizeSecondary == null) || (this.mRowSizeSecondary.length != this.mNumRows)) {
//                this.mRowSizeSecondary = new int[this.mNumRows];
//            }
//            processRowSizeSecondary(true);
//            switch (k) {
//                default:
//                    throw new IllegalStateException("wrong spec");
//                    i = View.MeasureSpec.getSize(widthSpec);
//                    int n = View.MeasureSpec.getSize(heightSpec);
//                    k = View.MeasureSpec.getMode(widthSpec);
//                    j = getPaddingLeft() + getPaddingRight();
//                    heightSpec = i;
//                    i = n;
//                    break label49;
//            }
//        }
//        widthSpec = getSizeSecondary() + j;
//        if (this.mOrientation == 0) {
//            setMeasuredDimension(i, widthSpec);
//        }
//        for (; ; ) {
//            leaveContext();
//            return;
//            widthSpec = Math.min(j + getSizeSecondary(), this.mMaxSizeSecondary);
//            break;
//            widthSpec = this.mMaxSizeSecondary;
//            break;
//            switch (k) {
//                default:
//                    throw new IllegalStateException("wrong spec");
//                case 0:
//                    label269:
//                    if (this.mRowSizeSecondaryRequested == 0) {
//                        if (this.mOrientation == 0) {
//                            throw new IllegalStateException("Must specify rowHeight or view height");
//                        }
//                        throw new IllegalStateException("Must specify columnWidth or view width");
//                    }
//                    this.mFixedRowSizeSecondary = this.mRowSizeSecondaryRequested;
//                    if (this.mNumRowsRequested == 0) {
//                    }
//                    for (widthSpec = m; ; widthSpec = this.mNumRowsRequested) {
//                        this.mNumRows = widthSpec;
//                        widthSpec = this.mFixedRowSizeSecondary * this.mNumRows + this.mMarginSecondary * (this.mNumRows - 1) + j;
//                        break;
//                    }
//            }
//            if ((this.mNumRowsRequested == 0) && (this.mRowSizeSecondaryRequested == 0)) {
//                this.mNumRows = 1;
//                this.mFixedRowSizeSecondary = (heightSpec - j);
//            }
//            for (; ; ) {
//                widthSpec = heightSpec;
//                if (k != Integer.MIN_VALUE) {
//                    break;
//                }
//                j += this.mFixedRowSizeSecondary * this.mNumRows + this.mMarginSecondary * (this.mNumRows - 1);
//                widthSpec = heightSpec;
//                if (j >= heightSpec) {
//                    break;
//                }
//                widthSpec = j;
//                break;
//                if (this.mNumRowsRequested == 0) {
//                    this.mFixedRowSizeSecondary = this.mRowSizeSecondaryRequested;
//                    this.mNumRows = ((this.mMarginSecondary + heightSpec) / (this.mRowSizeSecondaryRequested + this.mMarginSecondary));
//                } else if (this.mRowSizeSecondaryRequested == 0) {
//                    this.mNumRows = this.mNumRowsRequested;
//                    this.mFixedRowSizeSecondary = ((heightSpec - j - this.mMarginSecondary * (this.mNumRows - 1)) / this.mNumRows);
//                } else {
//                    this.mNumRows = this.mNumRowsRequested;
//                    this.mFixedRowSizeSecondary = this.mRowSizeSecondaryRequested;
//                }
//            }
//            setMeasuredDimension(widthSpec, i);
//        }
    }

    public boolean onRequestChildFocus(RecyclerView parent, View child, View focused) {
        if (this.mFocusSearchDisabled) {
        }
        while ((this.mInLayout) || (this.mInSelection)) {
            return true;
        }
        scrollToView(child, true);
        return true;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof GridLayoutManager.SavedState)) {
            return;
        }
        SavedState savedState = (SavedState) state;
        this.mFocusPosition = savedState.index;
        this.mFocusPositionOffset = 0;
        this.mChildrenStates.loadFromBundle(savedState.childStates);
        this.mForceFullLayout = true;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        GridLayoutManager.SavedState localSavedState = new GridLayoutManager.SavedState();
        int i = 0;
        int j = getChildCount();
        while (i < j) {
            View localView = getChildAt(i);
            int k = getPositionByView(localView);
            if (k != -1) {
                this.mChildrenStates.saveOnScreenView(localView, k);
            }
            i += 1;
        }
        localSavedState.index = getSelection();
        localSavedState.childStates = this.mChildrenStates.saveAsBundle();
        return localSavedState;
    }

    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        int i = getChildCount() - 1;
        while (i >= 0) {
            removeAndRecycleViewAt(i, recycler);
            i -= 1;
        }
    }

    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
        return false;
    }

    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((!this.mLayoutEnabled) || (!hasDoneFirstLayout())) {
            return 0;
        }
        saveContext(recycler, state);
        if (this.mOrientation == 0) {
        }
        for (dx = scrollDirectionPrimary(dx); ; dx = scrollDirectionSecondary(dx)) {
            leaveContext();
            return dx;
        }
    }

    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((!this.mLayoutEnabled) || (!hasDoneFirstLayout())) {
            return 0;
        }
        saveContext(recycler, state);
        if (this.mOrientation == 1) {
        }
        for (dy = scrollDirectionPrimary(dy); ; dy = scrollDirectionSecondary(dy)) {
            leaveContext();
            return dy;
        }
    }

    void setChildrenVisibility(int visibility) {
        this.mChildVisibility = visibility;
        if (this.mChildVisibility != -1) {
            int i = getChildCount();
            visibility = 0;
            while (visibility < i) {
                getChildAt(visibility).setVisibility(this.mChildVisibility);
                visibility += 1;
            }
        }
    }

    public void setFocusOutAllowed(boolean focusOutFront, boolean focusOutEnd) {
        this.mFocusOutFront = focusOutFront;
        this.mFocusOutEnd = focusOutEnd;
    }

    public void setFocusScrollStrategy(int focusScrollStrategy) {
        this.mFocusScrollStrategy = focusScrollStrategy;
    }

    void setFocusSearchDisabled(boolean focusSearchDisabled) {
        this.mFocusSearchDisabled = focusSearchDisabled;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public void setHorizontalMargin(int horizontalMargin) {
        if (this.mOrientation == 0) {
            this.mHorizontalMargin = horizontalMargin;
            this.mMarginPrimary = horizontalMargin;
            return;
        }
        this.mHorizontalMargin = horizontalMargin;
        this.mMarginSecondary = horizontalMargin;
    }

    public void setItemAlignmentOffset(int itemAlignmentOffset) {
        this.mItemAlignment.mainAxis().setItemAlignmentOffset(itemAlignmentOffset);
        updateChildAlignments();
    }

    public void setItemAlignmentOffsetPercent(float itemAlignmentOffsetPercent) {
        this.mItemAlignment.mainAxis().setItemAlignmentOffsetPercent(itemAlignmentOffsetPercent);
        updateChildAlignments();
    }

    public void setItemAlignmentOffsetWithPadding(boolean b) {
        this.mItemAlignment.mainAxis().setItemAlignmentOffsetWithPadding(b);
        updateChildAlignments();
    }

    public void setItemAlignmentViewId(int i) {
        this.mItemAlignment.mainAxis().setItemAlignmentViewId(i);
        updateChildAlignments();
    }

    public void setItemMargin(int i) {
        this.mHorizontalMargin = i;
        this.mVerticalMargin = i;
        this.mMarginSecondary = i;
        this.mMarginPrimary = i;
    }

    public void setLayoutEnabled(boolean b) {
        if (this.mLayoutEnabled != b) {
            this.mLayoutEnabled = b;
            requestLayout();
        }
    }

    public void setNumRows(int paramInt) {
        if (paramInt < 0) {
            throw new IllegalArgumentException();
        }
        this.mNumRowsRequested = paramInt;
        this.mForceFullLayout = true;
    }

    public void setOnChildSelectedListener(OnChildSelectedListener childSelectedListener) {
        this.mChildSelectedListener = childSelectedListener;
    }

    public void setOrientation(int orientation) {
        if ((orientation != 0) && (orientation != 1)) {
            return;
        }
        this.mOrientation = orientation;
        this.mWindowAlignment.setOrientation(orientation);
        this.mItemAlignment.setOrientation(orientation);
        this.mForceFullLayout = true;
    }

    public void setPrimaryOverReach(float v) {
        if (v != this.mPrimaryOverReach) {
            this.mPrimaryOverReach = v;
            requestLayout();
        }
    }

    public void setPruneChild(boolean b) {
        if (this.mPruneChild != b) {
            this.mPruneChild = b;
            if (this.mPruneChild) {
                requestLayout();
            }
        }
    }

    public void setRowHeight(int rowHeight) {
        if ((rowHeight >= 0) || (rowHeight == -2)) {
            this.mRowSizeSecondaryRequested = rowHeight;
            return;
        }
        throw new IllegalArgumentException("Invalid row height: " + rowHeight);
    }

    public void setScrollEnabled(boolean enabled) {
        if (this.mScrollEnabled != enabled) {
            this.mScrollEnabled = enabled;
            if ((this.mScrollEnabled) && (this.mFocusScrollStrategy == 0)) {
                if (this.mFocusPosition != -1) {
                    for (int i = 0; ; i = this.mFocusPosition) {
                        View localView = findViewByPosition(i);
                        if (localView != null) {
                            scrollToView(localView, true);
                        }
                        return;
                    }
                }
            }
        }
    }

    public void setSelection(RecyclerView view, int position) {
        setSelection(view, position, false);
    }

    public void setSelection(RecyclerView recyclerView, int position, boolean paramBoolean) {
        if (this.mFocusPosition == position) {
        }
        do {
            View localView = findViewByPosition(position);
            if (localView != null) {
                this.mInSelection = true;
                scrollToView(localView, paramBoolean);
                this.mInSelection = false;
                return;
            }
            this.mFocusPosition = position;
            this.mFocusPositionOffset = 0;
        } while (!this.mLayoutEnabled);
        if (paramBoolean) {
            if (!hasDoneFirstLayout()) {
                Log.w(getTag(), "setSelectionSmooth should not be called before first layout pass");
                return;
            }
            GridSmoothScroller smoothScroller = new GridSmoothScroller(this, recyclerView.getContext());
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
            return;
        }
        this.mForceFullLayout = true;
        recyclerView.requestLayout();
    }

    public void setSelectionSmooth(RecyclerView view, int pos) {
        setSelection(view, pos, true);
    }

    public void setVerticalMargin(int margin) {
        if (this.mOrientation == 0) {
            this.mVerticalMargin = margin;
            this.mMarginSecondary = margin;
            return;
        }
        this.mVerticalMargin = margin;
        this.mMarginPrimary = margin;
    }

    public void setWindowAlignment(int i) {
        this.mWindowAlignment.mainAxis().setWindowAlignment(i);
    }

    public void setWindowAlignmentOffset(int i) {
        this.mWindowAlignment.mainAxis().setWindowAlignmentOffset(i);
    }

    public void setWindowAlignmentOffsetPercent(float v) {
        this.mWindowAlignment.mainAxis().setWindowAlignmentOffsetPercent(v);
    }

    public final class LayoutParams extends RecyclerView.LayoutParams {
        private int mAlignX;
        private int mAlignY;
        private int mBottomInset;
        private int mLeftInset;
        private int mRighInset;
        private int mTopInset;
        private View mView;

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }

        private void invalidateItemDecoration() {
            ViewParent localViewParent = this.mView.getParent();
            if ((localViewParent instanceof RecyclerView)) {
                ((RecyclerView) localViewParent).invalidate();
            }
        }

        int getAlignX() {
            return this.mAlignX;
        }

        int getAlignY() {
            return this.mAlignY;
        }

        int getOpticalBottom(View view) {
            return view.getBottom() - this.mBottomInset;
        }

        int getOpticalBottomInset() {
            return this.mBottomInset;
        }

        int getOpticalHeight(View view) {
            return view.getHeight() - this.mTopInset - this.mBottomInset;
        }

        int getOpticalLeft(View view) {
            return view.getLeft() + this.mLeftInset;
        }

        int getOpticalLeftInset() {
            return this.mLeftInset;
        }

        int getOpticalRight(View view) {
            return view.getRight() - this.mRighInset;
        }

        int getOpticalRightInset() {
            return this.mRighInset;
        }

        int getOpticalTop(View view) {
            return view.getTop() + this.mTopInset;
        }

        int getOpticalTopInset() {
            return this.mTopInset;
        }

        int getOpticalWidth(View view) {
            return view.getWidth() - this.mLeftInset - this.mRighInset;
        }

        void setAlignX(int alignX) {
            this.mAlignX = alignX;
        }

        void setAlignY(int alignY) {
            this.mAlignY = alignY;
        }

        void setOpticalInsets(int leftInset, int topInset, int rightInset, int bottomInset) {
            this.mLeftInset = leftInset;
            this.mTopInset = topInset;
            this.mRighInset = rightInset;
            this.mBottomInset = bottomInset;
        }
    }

    public final class SavedState implements Parcelable {

        public final Creator CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new GridLayoutManager.SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new GridLayoutManager.SavedState[size];
            }
        };

        Bundle childStates = Bundle.EMPTY;
        int index;

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.index = parcel.readInt();
            this.childStates = parcel.readBundle(GridLayoutManager.class.getClassLoader());
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.index);
            dest.writeBundle(this.childStates);
        }
    }

    public final class GridSmoothScroller extends LinearSmoothScroller {
        GridLayoutManager layoutManager;

        GridSmoothScroller(GridLayoutManager layoutManager, Context context) {
            super(context);
            layoutManager = layoutManager;
        }

        public PointF computeScrollVectorForPosition(int pos) {
            if (getChildCount() == 0) {
                return null;
            }
            if (pos < getPosition(getChildAt(0))) {
            }
            for (pos = -1; mOrientation == 0; pos = 1) {
                return new PointF(pos, 0.0F);
            }
            return new PointF(0.0F, pos);
        }

        protected void onTargetFound(View targetView, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
            if (hasFocus()) {
                targetView.requestFocus();
            }
            dispatchChildSelected();
            int j = 0;
            if (getScrollPosition(targetView, mTempDeltas)) {
                if (mOrientation != 0) {
                    j = mTempDeltas[1];
                } else
                    j = mTempDeltas[0];
            }
            for (int i = mTempDeltas[1]; ; i = mTempDeltas[0]) {
                action.update(j, i, calculateTimeForDeceleration((int) Math.sqrt(j * j + i * i)), this.mDecelerateInterpolator);
                return;
            }
        }
    }
}
