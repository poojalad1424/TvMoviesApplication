package com.example.tvmoviesapplication.movies.utils

import android.content.Context
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import java.util.ArrayList

class PersistentWrapper(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val mPersistFocusVertical = true

    private var mSelectedPosition = -1

    internal fun getGrandChildCount(): Int {
        val wrapper = getChildAt(0) as ViewGroup
        return wrapper?.childCount ?: 0
    }

    private fun shouldPersistFocusFromDirection(direction: Int): Boolean {
        return mPersistFocusVertical && (direction == View.FOCUS_UP || direction == View.FOCUS_DOWN) || !mPersistFocusVertical && (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT)
    }

    override fun addFocusables(views: ArrayList<View>?, direction: Int, focusableMode: Int) {
        if (hasFocus() || getGrandChildCount() == 0 ||
            !shouldPersistFocusFromDirection(direction)
        ) {
            super.addFocusables(views, direction, focusableMode)
        } else {
            // Select a child in requestFocus
            views?.add(this)
        }
    }

    override fun requestChildFocus(child: View?, focused: View?) {
        super.requestChildFocus(child, focused)
        var view: View? = focused
        while (view != null && view.parent !== child) {
            try {
                view = view.parent as View
            } catch (e: ClassCastException) {
                view = null
            }

        }
        mSelectedPosition = if (view == null) -1 else (child as ViewGroup).indexOfChild(view)
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        val wrapper = getChildAt(0) as ViewGroup
        if (wrapper != null && mSelectedPosition >= 0 && mSelectedPosition < getGrandChildCount()) {
            if (wrapper.getChildAt(mSelectedPosition).requestFocus(
                    direction, previouslyFocusedRect
                )
            ) {
                return true
            }
        }
        return super.requestFocus(direction, previouslyFocusedRect)
    }

    internal class SavedState : View.BaseSavedState {

        var mSelectedPosition: Int = 0

        constructor(`in`: Parcel) : super(`in`) {
            mSelectedPosition = `in`.readInt()
        }

        constructor(superState: Parcelable?) : super(superState) {}

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(mSelectedPosition)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(
                    parcel
                )
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val savedState =
            SavedState(super.onSaveInstanceState())
        savedState.mSelectedPosition = mSelectedPosition
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        mSelectedPosition = state.mSelectedPosition
        super.onRestoreInstanceState(savedState.superState)
    }
}