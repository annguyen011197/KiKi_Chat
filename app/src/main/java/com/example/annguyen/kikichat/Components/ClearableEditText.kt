package com.example.annguyen.kikichat.Components

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.Drawable.Callback
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.example.annguyen.kikichat.R

class ClearableEditText : EditText {
    lateinit var mClearIconDrawable: Drawable
    var mIsClearIconShown = false
    var mClearIconDrawWhenFocus = false

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }



    fun init() {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(hasFocus()){
                   showClearIcon(!s.isNullOrEmpty())
               }
            }

        })
        mClearIconDrawable = resources.getDrawable(R.drawable.ic_clear_black_24dp)
        mClearIconDrawable.setColorFilter(resources.getColor(R.color.colorAccent),PorterDuff.Mode.SRC_IN)
        this.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_UP){
                if(this.compoundDrawables[2] != null) {
                    if(event.rawX >= (this.right - this.compoundDrawables[2].bounds.width())){
                        this.setText("")
                        this.showClearIcon(false)
                        true
                    }
                }
            }
            false
        }

        this.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                if(this.text.isNotEmpty()){
                   this.showClearIcon(true)
                }
            }else{
                this.showClearIcon(false)
            }
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    fun showClearIcon(show:Boolean){
        if(show){
            setCompoundDrawablesWithIntrinsicBounds(null,null,mClearIconDrawable,null)
        }else{
            setCompoundDrawables(null,null,null,null)
        }
        mIsClearIconShown = show
    }
}