package com.soushin.tinmvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soushin.tinmvvm.mvvm.adapter.MultiTypeAdapter
import com.soushin.tinmvvm.mvvm.adapter.MultiplexAdapter
import com.soushin.tinmvvm.utils.FlingHelper
import me.jessyan.autosize.AutoSizeConfig

class ParentRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RecyclerView(context, attrs, defStyleAttr)  {

    private var mMaxDistance:Int = 0

    private val mFlingHelper = FlingHelper(context)
    /**
     * 记录上次Event事件的y坐标
     */
    private var lastY:Float = 0f

    var totalDy = 0
    /**
     * 用于判断RecyclerView是否在fling
     */
    var isStartFling =  false
    /**
     * 记录当前滑动的y轴加速度
     */
    private var velocityY: Int = 0

    init {
        mMaxDistance = mFlingHelper.getVelocityByDistance((AutoSizeConfig.getInstance().screenHeight * 4).toDouble())

        addOnScrollListener(object :OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //如果父RecyclerView fling过程中已经到底部，需要让子RecyclerView滑动神域的fling
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    dispatchChildFling()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(isStartFling) {
                    totalDy = 0
                    isStartFling = false
                }
                //在RecyclerView fling情况下，记录当前RecyclerView在y轴的便宜
                totalDy += dy
            }
        })
    }

    private fun dispatchChildFling() {
        if (isScrollEnd() && velocityY != 0) {
            val splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY)
            if (splineFlingDistance > totalDy) {
                childFling(mFlingHelper.getVelocityByDistance(splineFlingDistance - totalDy.toDouble()))
            }
        }
        totalDy = 0
        velocityY = 0
    }

    private fun childFling(velY: Int) {
        findNestedScrollingChildRecyclerView()?.fling(0,velY)
    }

    fun initLayoutManager() {
        val linearLayoutManager = object : LinearLayoutManager(context) {
            override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: State?): Int {
                return try {
                    super.scrollVerticallyBy(dy, recycler, state)
                } catch (e: Exception) {
                    e.printStackTrace()
                    0
                }
            }

            override fun onLayoutChildren(recycler: Recycler?, state: State?) {
                try {
                    super.onLayoutChildren(recycler, state)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun canScrollVertically(): Boolean {
                val childRecyclerView = findNestedScrollingChildRecyclerView()
                return childRecyclerView == null || childRecyclerView.isScrollTop()
            }

            override fun addDisappearingView(child: View?) {
                try {
                    super.addDisappearingView(child)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun supportsPredictiveItemAnimations(): Boolean {
                return false
            }
        }
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager = linearLayoutManager
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev != null && ev.action == MotionEvent.ACTION_DOWN) {
            //ACTION_DOWN的时候重置加速度
            velocityY = 0
            stopScroll()
        }
        if((ev == null || ev.action == MotionEvent.ACTION_MOVE).not()) {
            //在ACTION_MOVE的情况下，将lastY置为0
            lastY = 0f
        }
        return try {
            super.dispatchTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if(lastY == 0f) {
            lastY = e.y
        }
        if(isScrollEnd()) {
            //如果父RecyclerView已经滑动到底部，需要让子RecyclerView滑动剩余的距离
            val childRecyclerView = findNestedScrollingChildRecyclerView()
            childRecyclerView?.run {
                val deltaY = (lastY - e.y).toInt()
                if(deltaY != 0) {
                    scrollBy(0,deltaY)
                }
            }
        }
        lastY = e.y
        return try {
            super.onTouchEvent(e)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun fling(velX: Int, velY: Int): Boolean {
        val fling = super.fling(velX, velY)
        if (!fling || velY <= 0) {
            velocityY = 0
        } else {
            isStartFling = true
            velocityY = velY
        }
        return fling
    }

    private fun isScrollEnd(): Boolean {
        //RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
        return !canScrollVertically(1)
    }

    private fun findNestedScrollingChildRecyclerView():ChildRecyclerView? {
        (adapter as? MultiTypeAdapter)?.apply {
            return getCurrentChildRecyclerView()
        }
        (adapter as? MultiplexAdapter)?.apply {
            return getCurrentChildRecyclerView()
        }
        return null
    }


    override fun scrollToPosition(position: Int) {
        //处理一键置顶会出现卡顿的问题
        findNestedScrollingChildRecyclerView()?.scrollToPosition(position)
        postDelayed({
            super.scrollToPosition(position)
        },50)
    }

}