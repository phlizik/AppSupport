package com.app.support.widget.gridview;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by liyang on 16/1/12.
 */
public class ItemAlignment {

    public final ItemAlignment.Axis horizontal = new ItemAlignment.Axis(0);
    private ItemAlignment.Axis mMainAxis = this.horizontal;
    private int mOrientation = 0;
    private ItemAlignment.Axis mSecondAxis = this.vertical;
    public final ItemAlignment.Axis vertical = new ItemAlignment.Axis(1);

    public final int getOrientation() {
        return this.mOrientation;
    }

    public final ItemAlignment.Axis mainAxis() {
        return this.mMainAxis;
    }

    public final ItemAlignment.Axis secondAxis() {
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

    class Axis {
        private int mOffset = 0;
        private float mOffsetPercent = 50.0F;
        private boolean mOffsetWithPadding = false;
        private int mOrientation;
        private Rect mRect = new Rect();
        private int mViewId = 0;

        Axis(int paramInt) {
            this.mOrientation = paramInt;
        }

        public int getAlignmentPosition(View view) {
            GridLayoutManager.LayoutParams localLayoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            int i, j;
            float f;
            //Fixme: ItemAlignment->getAlignmentPosition
//            for (View localView1 = view; ; localView1 = view) {
//                if (this.mViewId != 0) {
//                    View localView2 = view.findViewById(this.mViewId);
//                    localView1 = localView2;
//                    if (localView2 != null) {
//                    }
//                }
//                if (this.mOrientation == 0) {
//                    if (this.mOffset >= 0) {
//                        j = this.mOffset;
//                        i = j;
//                        if (this.mOffsetWithPadding) {
//                            i = j + localView1.getPaddingLeft();
//                        }
//                        j = i;
//                        if (this.mOffsetPercent != -1.0F) {
//                            f = i;
//                            if (localView1 != view) {
//                                label217:
//                                for (i = localLayoutParams.getOpticalWidth(localView1); ; i = localView1.getWidth()) {
//                                    j = (int) (i * this.mOffsetPercent / 100.0F + f);
//                                    i = j;
//                                    if (view == localView1) {
//                                        return i;
//                                    }
//                                    this.mRect.left = j;
//                                    ((ViewGroup) view).offsetDescendantRectToMyCoords(localView1, this.mRect);
//                                    return this.mRect.left - localLayoutParams.getOpticalLeftInset();
//                                    if (localView1 == view) {
//                                    }
//                                    for (j = localLayoutParams.getOpticalWidth(localView1); ; j = localView1.getWidth() + this.mOffset) {
//                                        i = j;
//                                        if (!this.mOffsetWithPadding) {
//                                            break;
//                                        }
//                                        i = j - localView1.getPaddingRight();
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                if (this.mOffset >= 0) {
//                    j = this.mOffset;
//                    i = j;
//                    if (this.mOffsetWithPadding) {
//                        i = j + localView1.getPaddingTop();
//                    }
//                    j = i;
//                    if (this.mOffsetPercent != -1.0F) {
//                        f = i;
//                        if (localView1 != view) {
//                            for (int i = localLayoutParams.getOpticalHeight(localView1); ; i = localView1.getHeight()) {
//                                j = (int) (i * this.mOffsetPercent / 100.0F + f);
//                                i = j;
//                                if (view == localView1) {
//                                    return i;
//                                }
//                                this.mRect.top = j;
//                                ((ViewGroup) view).offsetDescendantRectToMyCoords(localView1, this.mRect);
//                                return this.mRect.top - localLayoutParams.getOpticalTopInset();
//                                if (localView1 == view) {
//                                }
//                                for (j = localLayoutParams.getOpticalHeight(localView1); ; j = localView1.getHeight() + this.mOffset) {
//                                    i = j;
//                                    if (!this.mOffsetWithPadding) {
//                                        break;
//                                    }
//                                    i = j + localView1.getPaddingBottom();
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
            return 0;
        }

        public int getItemAlignmentOffset() {
            return this.mOffset;
        }

        public float getItemAlignmentOffsetPercent() {
            return this.mOffsetPercent;
        }

        public int getItemAlignmentViewId() {
            return this.mViewId;
        }

        public boolean isItemAlignmentOffsetWithPadding() {
            return this.mOffsetWithPadding;
        }

        public void setItemAlignmentOffset(int offset) {
            this.mOffset = offset;
        }

        public void setItemAlignmentOffsetPercent(float percent) {
            if (((percent < 0.0F) || (percent > 100.0F)) && (percent != -1.0F)) {
                throw new IllegalArgumentException();
            }
            this.mOffsetPercent = percent;
        }

        public void setItemAlignmentOffsetWithPadding(boolean offset) {
            this.mOffsetWithPadding = offset;
        }

        public void setItemAlignmentViewId(int viewId) {
            this.mViewId = viewId;
        }

    }
}
