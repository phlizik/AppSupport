package com.app.support.widget.gridview;

/**
 * Created by liyang on 16/1/12.
 */
public class WindowAlignment {
    public final WindowAlignment.Axis horizontal = new WindowAlignment.Axis("horizontal");
    private WindowAlignment.Axis mMainAxis = this.horizontal;
    private int mOrientation = 0;
    private WindowAlignment.Axis mSecondAxis = this.vertical;
    public final WindowAlignment.Axis vertical = new WindowAlignment.Axis("vertical");

    public final int getOrientation() {
        return this.mOrientation;
    }

    public final WindowAlignment.Axis mainAxis() {
        return this.mMainAxis;
    }

    public final void reset() {
        mainAxis().reset();
    }

    public final WindowAlignment.Axis secondAxis() {
        return this.mSecondAxis;
    }

    public final void setOrientation(int orientation) {
        this.mOrientation = orientation;
        if (this.mOrientation == 0) {
            this.mMainAxis = this.horizontal;
            this.mSecondAxis = this.vertical;
            return;
        }
        this.mMainAxis = this.vertical;
        this.mSecondAxis = this.horizontal;
    }

    public String toString() {
        return "horizontal=" + this.horizontal.toString() + "vertical=" + this.vertical.toString();
    }

    class Axis {
        private int mMaxEdge;
        private int mMaxScroll;
        private int mMinEdge;
        private int mMinScroll;
        private String mName;
        private int mPaddingHigh;
        private int mPaddingLow;
        private float mScrollCenter;
        private int mSize;
        private int mWindowAlignment = 3;
        private int mWindowAlignmentOffset = 0;
        private float mWindowAlignmentOffsetPercent = 50.0F;

        public Axis(String name) {
            reset();
            this.mName = name;
        }

        private void reset() {
            this.mScrollCenter = -2.14748365E9F;
            this.mMinEdge = Integer.MIN_VALUE;
            this.mMaxEdge = Integer.MAX_VALUE;
        }

        public final int getClientSize() {
            return this.mSize - this.mPaddingLow - this.mPaddingHigh;
        }

        public final int getMaxEdge() {
            return this.mMaxEdge;
        }

        public final int getMaxScroll() {
            return this.mMaxScroll;
        }

        public final int getMinEdge() {
            return this.mMinEdge;
        }

        public final int getMinScroll() {
            return this.mMinScroll;
        }

        public final int getPaddingHigh() {
            return this.mPaddingHigh;
        }

        public final int getPaddingLow() {
            return this.mPaddingLow;
        }

        public final int getScrollCenter() {
            return (int) this.mScrollCenter;
        }

        public final int getSize() {
            return this.mSize;
        }

        public final int getSystemScrollPos(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
            //Fixme: WindowAlignment->getSystemScrollPos
            if (this.mWindowAlignmentOffset >= 0) {
            }
            int j;
            boolean bool1;
            boolean bool2;
            for (int i = this.mWindowAlignmentOffset - this.mPaddingLow; ; i = this.mSize + this.mWindowAlignmentOffset - this.mPaddingLow) {
                j = i;
                if (this.mWindowAlignmentOffsetPercent != -1.0F) {
                    j = i + (int) (this.mSize * this.mWindowAlignmentOffsetPercent / 100.0F);
                }
                i = getClientSize();
                bool1 = isMinUnknown();
                bool2 = isMaxUnknown();
                if ((bool1) || (bool2) || ((this.mWindowAlignment & 0x3) != 3) || (this.mMaxEdge - this.mMinEdge > i)) {
                    break;
                }
                if ((!bool2) && ((this.mWindowAlignment & 0x2) != 0) && ((paramBoolean2) || (this.mMaxEdge - paramInt <= i - j))) {
                    return this.mMaxEdge - this.mPaddingLow - i;
                }
                return this.mMinEdge - this.mPaddingLow;
            }
            if ((!bool1) && ((this.mWindowAlignment & 0x1) != 0) && ((paramBoolean1) || (paramInt - this.mMinEdge <= j))) {
                return this.mMinEdge - this.mPaddingLow;
            }
            return paramInt - j - this.mPaddingLow;
        }

        public final int getSystemScrollPos(boolean paramBoolean1, boolean paramBoolean2) {
            return getSystemScrollPos((int) this.mScrollCenter, paramBoolean1, paramBoolean2);
        }

        public final int getWindowAlignment() {
            return this.mWindowAlignment;
        }

        public final int getWindowAlignmentOffset() {
            return this.mWindowAlignmentOffset;
        }

        public final float getWindowAlignmentOffsetPercent() {
            return this.mWindowAlignmentOffsetPercent;
        }

        public final void invalidateScrollMax() {
            this.mMaxEdge = Integer.MAX_VALUE;
            this.mMaxScroll = Integer.MAX_VALUE;
        }

        public final void invalidateScrollMin() {
            this.mMinEdge = Integer.MIN_VALUE;
            this.mMinScroll = Integer.MIN_VALUE;
        }

        public final boolean isMaxUnknown() {
            return this.mMaxEdge == Integer.MAX_VALUE;
        }

        public final boolean isMinUnknown() {
            return this.mMinEdge == Integer.MIN_VALUE;
        }

        public final void setMaxEdge(int maxEdge) {
            this.mMaxEdge = maxEdge;
        }

        public final void setMaxScroll(int maxScroll) {
            this.mMaxScroll = maxScroll;
        }

        public final void setMinEdge(int minEdge) {
            this.mMinEdge = minEdge;
        }

        public final void setMinScroll(int minScroll) {
            this.mMinScroll = minScroll;
        }

        public final void setPadding(int low, int high) {
            this.mPaddingLow = low;
            this.mPaddingHigh = high;
        }

        public final void setSize(int size) {
            this.mSize = size;
        }

        public final void setWindowAlignment(int alignment) {
            this.mWindowAlignment = alignment;
        }

        public final void setWindowAlignmentOffset(int offset) {
            this.mWindowAlignmentOffset = offset;
        }

        public final void setWindowAlignmentOffsetPercent(float percent) {
            if (((percent < 0.0F) || (percent > 100.0F)) && (percent != -1.0F)) {
                throw new IllegalArgumentException();
            }
            this.mWindowAlignmentOffsetPercent = percent;
        }

        public String toString() {
            return "center: " + this.mScrollCenter + " min:" + this.mMinEdge + " max:" + this.mMaxEdge;
        }

        public final float updateScrollCenter(float scrollCenter) {
            this.mScrollCenter = scrollCenter;
            return scrollCenter;
        }
    }
}
