package com.app.support.widget.gridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.app.support.R;

/**
 * Created by liyang on 16/1/12.
 */
public class BaseGridView extends RecyclerView {

    public static final int FOCUS_SCROLL_ALIGNED = 0;
    public static final int FOCUS_SCROLL_ITEM = 1;
    public static final int FOCUS_SCROLL_PAGE = 2;
    public static final float ITEM_ALIGN_OFFSET_PERCENT_DISABLED = -1.0F;
    public static final int SAVE_ALL_CHILD = 3;
    public static final int SAVE_LIMITED_CHILD = 2;
    public static final int SAVE_NO_CHILD = 0;
    public static final int SAVE_ON_SCREEN_CHILD = 1;
    public static final int WINDOW_ALIGN_BOTH_EDGE = 3;
    public static final int WINDOW_ALIGN_HIGH_EDGE = 2;
    public static final int WINDOW_ALIGN_LOW_EDGE = 1;
    public static final int WINDOW_ALIGN_NO_EDGE = 0;
    public static final float WINDOW_ALIGN_OFFSET_PERCENT_DISABLED = -1.0F;
    private boolean mAnimateChildLayout = true;
    private boolean mHasOverlappingRendering = true;
    protected final GridLayoutManager mLayoutManager = new GridLayoutManager(this);
    private BaseGridView.OnKeyInterceptListener mOnKeyInterceptListener;
    private BaseGridView.OnMotionInterceptListener mOnMotionInterceptListener;
    private BaseGridView.OnTouchInterceptListener mOnTouchInterceptListener;
    private RecyclerView.ItemAnimator mSavedItemAnimator;

    public interface OnKeyInterceptListener {
        boolean onInterceptKeyEvent(KeyEvent paramKeyEvent);
    }

    public interface OnMotionInterceptListener {
        boolean onInterceptMotionEvent(MotionEvent paramMotionEvent);
    }

    public interface OnTouchInterceptListener {
        boolean onInterceptTouchEvent(MotionEvent paramMotionEvent);
    }


    public BaseGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        setLayoutManager(this.mLayoutManager);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setHasFixedSize(true);
        setChildrenDrawingOrderEnabled(true);
        setWillNotDraw(true);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public boolean dispatchGenericFocusedEvent(MotionEvent paramMotionEvent) {
        if ((this.mOnMotionInterceptListener != null) && (this.mOnMotionInterceptListener.onInterceptMotionEvent(paramMotionEvent))) {
            return true;
        }
        return super.dispatchGenericFocusedEvent(paramMotionEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
        if ((this.mOnKeyInterceptListener != null) && (this.mOnKeyInterceptListener.onInterceptKeyEvent(paramKeyEvent))) {
            return true;
        }
        return super.dispatchKeyEvent(paramKeyEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
        if ((this.mOnTouchInterceptListener != null) && (this.mOnTouchInterceptListener.onInterceptTouchEvent(paramMotionEvent))) {
            return true;
        }
        return super.dispatchTouchEvent(paramMotionEvent);
    }

    public int getChildDrawingOrder(int paramInt1, int paramInt2) {
        return this.mLayoutManager.getChildDrawingOrder(this, paramInt1, paramInt2);
    }

    public int getFocusScrollStrategy() {
        return this.mLayoutManager.getFocusScrollStrategy();
    }

    public int getHorizontalMargin() {
        return this.mLayoutManager.getHorizontalMargin();
    }

    public int getItemAlignmentOffset() {
        return this.mLayoutManager.getItemAlignmentOffset();
    }

    public float getItemAlignmentOffsetPercent() {
        return this.mLayoutManager.getItemAlignmentOffsetPercent();
    }

    public int getItemAlignmentViewId() {
        return this.mLayoutManager.getItemAlignmentViewId();
    }

    public final int getSaveChildrenLimitNumber() {
        return this.mLayoutManager.mChildrenStates.getLimitNumber();
    }

    public final int getSaveChildrenPolicy() {
        return this.mLayoutManager.mChildrenStates.getSavePolicy();
    }

    public int getSelectedPosition() {
        return this.mLayoutManager.getSelection();
    }

    public int getVerticalMargin() {
        return this.mLayoutManager.getVerticalMargin();
    }

    public void getViewSelectedOffsets(View paramView, int[] paramArrayOfInt) {
        this.mLayoutManager.getViewSelectedOffsets(paramView, paramArrayOfInt);
    }

    public int getWindowAlignment() {
        return this.mLayoutManager.getWindowAlignment();
    }

    public int getWindowAlignmentOffset() {
        return this.mLayoutManager.getWindowAlignmentOffset();
    }

    public float getWindowAlignmentOffsetPercent() {
        return this.mLayoutManager.getWindowAlignmentOffsetPercent();
    }

    public boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    public boolean hasPreviousViewInSameRow(int paramInt) {
        return this.mLayoutManager.hasPreviousViewInSameRow(paramInt);
    }

    protected void initBaseGridViewAttributes(Context paramContext, AttributeSet paramAttributeSet) {
        TypedArray array = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BaseGridView);
        boolean focusOutFront = array.getBoolean(R.styleable.BaseGridView_focusOutFront, false);
        boolean focusOutEnd = array.getBoolean(R.styleable.BaseGridView_focusOutEnd, false);
        this.mLayoutManager.setFocusOutAllowed(focusOutFront, focusOutEnd);
        this.mLayoutManager.setVerticalMargin(array.getDimensionPixelSize(R.styleable.BaseGridView_verticalMargin, 0));
        this.mLayoutManager.setHorizontalMargin(array.getDimensionPixelSize(R.styleable.BaseGridView_horizontalMargin, 0));
        if (array.hasValue(R.styleable.BaseGridView_android_gravity)) {
            setGravity(array.getInt(0, 0));
        }
        array.recycle();
    }

    public boolean isChildLayoutAnimated() {
        return this.mAnimateChildLayout;
    }

    final boolean isChildrenDrawingOrderEnabledInternal() {
        return isChildrenDrawingOrderEnabled();
    }

    public boolean isFocusDrawingOrderEnabled() {
        return super.isChildrenDrawingOrderEnabled();
    }

    public final boolean isFocusSearchDisabled() {
        return this.mLayoutManager.isFocusSearchDisabled();
    }

    public boolean isItemAlignmentOffsetWithPadding() {
        return this.mLayoutManager.isItemAlignmentOffsetWithPadding();
    }

    public boolean isScrollEnabled() {
        return this.mLayoutManager.isScrollEnabled();
    }

    public boolean onRequestFocusInDescendants(int paramInt, Rect paramRect) {
        return this.mLayoutManager.gridOnRequestFocusInDescendants(this, paramInt, paramRect);
    }

    public void setAnimateChildLayout(boolean paramBoolean) {
        if (this.mAnimateChildLayout != paramBoolean) {
            this.mAnimateChildLayout = paramBoolean;
            if (!this.mAnimateChildLayout) {
                this.mSavedItemAnimator = getItemAnimator();
                super.setItemAnimator(null);
            }
        } else {
            return;
        }
        super.setItemAnimator(this.mSavedItemAnimator);
    }

    public void setChildrenVisibility(int paramInt) {
        this.mLayoutManager.setChildrenVisibility(paramInt);
    }

    public void setFocusDrawingOrderEnabled(boolean paramBoolean) {
        super.setChildrenDrawingOrderEnabled(paramBoolean);
    }

    public void setFocusScrollStrategy(int paramInt) {
        if ((paramInt != 0) && (paramInt != 1) && (paramInt != 2)) {
            throw new IllegalArgumentException("Invalid scrollStrategy");
        }
        this.mLayoutManager.setFocusScrollStrategy(paramInt);
        requestLayout();
    }

    public final void setFocusSearchDisabled(boolean paramBoolean) {
        this.mLayoutManager.setFocusSearchDisabled(paramBoolean);
    }

    public void setGravity(int paramInt) {
        this.mLayoutManager.setGravity(paramInt);
        requestLayout();
    }

    public void setHasOverlappingRendering(boolean paramBoolean) {
        this.mHasOverlappingRendering = paramBoolean;
    }

    public void setHorizontalMargin(int paramInt) {
        this.mLayoutManager.setHorizontalMargin(paramInt);
        requestLayout();
    }

    public void setItemAlignmentOffset(int paramInt) {
        this.mLayoutManager.setItemAlignmentOffset(paramInt);
        requestLayout();
    }

    public void setItemAlignmentOffsetPercent(float paramFloat) {
        this.mLayoutManager.setItemAlignmentOffsetPercent(paramFloat);
        requestLayout();
    }

    public void setItemAlignmentOffsetWithPadding(boolean paramBoolean) {
        this.mLayoutManager.setItemAlignmentOffsetWithPadding(paramBoolean);
        requestLayout();
    }

    public void setItemAlignmentViewId(int paramInt) {
        this.mLayoutManager.setItemAlignmentViewId(paramInt);
    }

    public void setItemMargin(int paramInt) {
        this.mLayoutManager.setItemMargin(paramInt);
        requestLayout();
    }

    public void setLayoutEnabled(boolean paramBoolean) {
        this.mLayoutManager.setLayoutEnabled(paramBoolean);
    }

    public void setOnChildSelectedListener(OnChildSelectedListener paramOnChildSelectedListener) {
        this.mLayoutManager.setOnChildSelectedListener(paramOnChildSelectedListener);
    }

    public void setOnKeyInterceptListener(BaseGridView.OnKeyInterceptListener paramOnKeyInterceptListener) {
        this.mOnKeyInterceptListener = paramOnKeyInterceptListener;
    }

    public void setOnMotionInterceptListener(BaseGridView.OnMotionInterceptListener paramOnMotionInterceptListener) {
        this.mOnMotionInterceptListener = paramOnMotionInterceptListener;
    }

    public void setOnTouchInterceptListener(BaseGridView.OnTouchInterceptListener paramOnTouchInterceptListener) {
        this.mOnTouchInterceptListener = paramOnTouchInterceptListener;
    }

    public final void setPrimaryOverReach(float paramFloat) {
        this.mLayoutManager.setPrimaryOverReach(paramFloat);
    }

    public void setPruneChild(boolean paramBoolean) {
        this.mLayoutManager.setPruneChild(paramBoolean);
    }

    public final void setSaveChildrenLimitNumber(int paramInt) {
        this.mLayoutManager.mChildrenStates.setLimitNumber(paramInt);
    }

    public final void setSaveChildrenPolicy(int paramInt) {
        this.mLayoutManager.mChildrenStates.setSavePolicy(paramInt);
    }

    public void setScrollEnabled(boolean paramBoolean) {
        this.mLayoutManager.setScrollEnabled(paramBoolean);
    }

    public void setSelectedPosition(int paramInt) {
        this.mLayoutManager.setSelection(this, paramInt);
    }

    public void setSelectedPositionSmooth(int paramInt) {
        this.mLayoutManager.setSelectionSmooth(this, paramInt);
    }

    public void setVerticalMargin(int paramInt) {
        this.mLayoutManager.setVerticalMargin(paramInt);
        requestLayout();
    }

    public void setWindowAlignment(int paramInt) {
        this.mLayoutManager.setWindowAlignment(paramInt);
        requestLayout();
    }

    public void setWindowAlignmentOffset(int paramInt) {
        this.mLayoutManager.setWindowAlignmentOffset(paramInt);
        requestLayout();
    }

    public void setWindowAlignmentOffsetPercent(float paramFloat) {
        this.mLayoutManager.setWindowAlignmentOffsetPercent(paramFloat);
        requestLayout();
    }
}
