package com.soushin.tinmvvm.base

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.ALog
import com.caesarlib.brvahbinding.CSBindingAdapter
import com.caesarlib.brvahbinding.CSBravhItemBinding
import com.caesarlib.brvahbinding.CSItemBindingAdapter
import com.caesarlib.brvahbinding.CSbrvahLog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.animation.BaseAnimation
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.soushin.tinmvvm.R
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

/**
 * 这是一个封装了BaseAdapter的ViewModel
 * @auther SouShin
 * @time 2020/3/2 17:31
 */
abstract class BaseBindingViewModel<B, M : BaseModel> :
    BaseViewModel<M> {

    //数据集
    var items: ObservableArrayList<B>? = null
    //数据绑定的布局及BR和data
    var itemBinding: HashMap<Int, CSBravhItemBinding<*>>? = null
    //适配器,可以用用户自己的,但是必须要继承CSItemBindingAdapter
    var bindingAdapter: CSItemBindingAdapter<B, BaseViewHolder>? = null
    //设置每个item的占的格数
    var spanSizeLookup: BaseQuickAdapter.SpanSizeLookup? = null
    //当多布局时,如果item不想继承MultiItemEntity,可以用这个来设置每个itemType
    var multiTypeDelegat: MultiTypeDelegate<B>? = null
    //空布局
    var emptyResId: ObservableInt? = null
    //头部数据的绑定的布局及BR和data
    var headBinding: ArrayList<CSBravhItemBinding<*>>? = null
    //脚部数据的绑定的布局及BR和data
    var footBinding: ArrayList<CSBravhItemBinding<*>>? = null
    //是否处于刷新状态
    var isRefreshing: ObservableBoolean
    //SwipeRefreshLayout的刷新回调,是AndroidX的SwipeRefreshLayout
    var refreshListener: SwipeRefreshLayout.OnRefreshListener
    //Empty View 点击回调
    var emptyOnClickListener: View.OnClickListener? = null
    //加载的动画
    var animationType: ObservableInt? = null
    //加载的自定义动画
    var customAnimation = onCustomAnimation()

    var itemDecoration = onitemDecoration()

    var mRecyclerView: RecyclerView? = null

    protected var disposable: Disposable? = null

    //滑动删除监听
    var onItemSwipeListener: OnItemSwipeListener? = null
    var swipeMoveFrags: ObservableInt? = null
    //长按拖动监听
    var onItemDragListener: OnItemDragListener? = null

    constructor(application: Application, model: M):super(application, model){
        initDatas()//在这里初始化itemBinding/bindingAdapter/headBinding/footBinding
        items = ObservableArrayList()
//        bindingAdapter = getBindingAdapter()
        emptyResId = ObservableInt(getEmptyViewRes(EmptyViewType.EMPTY))
//        headBinding = getHeadBinding()
//        footBinding = getFootBinding()
        isRefreshing = ObservableBoolean()
        refreshListener = SwipeRefreshLayout.OnRefreshListener {
            isRefreshing.set(true)
            reload()
        }
        emptyOnClickListener = View.OnClickListener {
            ALog.e("点击了空布局按钮")
            if (emptyResId?.get() != getEmptyViewRes(EmptyViewType.LOADING)) {
                reload()
                emptyResId?.set(getEmptyViewRes(EmptyViewType.LOADING))
            }
        }
        animationType = ObservableInt(BaseQuickAdapter.SLIDEIN_BOTTOM)
        onItemSwipeListener = getItemSwipeListener()
        swipeMoveFrags = ObservableInt(getOnSwipeMoveFrags())
        onItemDragListener = getItemDragListener()
        if (bindingAdapter == null) {
            bindingAdapter = CSBindingAdapter(itemBinding, items)
        }
        bindingAdapter!!.isFirstOnly(isAnimationFirstOnley()!!)
    }

    abstract fun initDatas()

    protected fun load(flowable: Flowable<List<B>>) {
        if (isRefreshing.get()) {
            emptyResId?.set(getEmptyViewRes(EmptyViewType.REFRESH))
        } else {
            ALog.e("调用了正在加载界面")
            emptyResId?.set(getEmptyViewRes(EmptyViewType.LOADING))
        }
        disposable = flowable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ bs ->
                ALog.e("收到数据了")
                addItems(bs)
            }, {
                ALog.e("出现异常")
                emptyResId?.set(getEmptyViewRes(EmptyViewType.ERROR))
                isRefreshing.set(false)
            }, {
                ALog.e("完成加载了")
                emptyResId?.set(getEmptyViewRes(EmptyViewType.EMPTY))
                isRefreshing.set(false)
                onDataLoadComplete()
            })
    }

    //空布局的点击事件,里面做判断,如果当前空布局不是正在加载的状态,点击之后,就重新获取数据
    /*protected fun getEmptyOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            CSbrvahLog.Print("点击了空布局按钮")
            if (emptyResId?.get() != getEmptyViewRes(EmptyViewType.LOADING)) {
                reload()
                emptyResId?.set(getEmptyViewRes(EmptyViewType.LOADING))
            }
        }
    }*/

    /**
     * 重新加载
     */
    fun reload() {
        dispose()
        items?.clear()
        load()
    }

    fun addItems(newData: List<B>?) {
        newData?.let {
            items?.addAll(newData)
        }
    }

    fun addItems(newData: List<B>?, index: Int) {
        newData?.let {
            items?.addAll(index, newData)
        }
    }

    abstract fun load()

    fun setSpan(spanSizeLookup: BaseQuickAdapter.SpanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup
    }


    fun getEmptyViewRes(type: EmptyViewType): Int {
        return when (type) {
            EmptyViewType.ERROR -> R.layout.layout_error_view
            EmptyViewType.LOADING -> R.layout.layout_loading_view
            EmptyViewType.REFRESH -> R.layout.layout_refresh_view
            else -> R.layout.layout_empty_view
        }
    }

    fun onCustomAnimation(): BaseAnimation? {
        return null
    }

    fun onitemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    //下拉刷新控件的监听器,里面调用重新加载数据的方法
    /*fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener {
        return object : SwipeRefreshLayout.OnRefreshListener() {
            fun onRefresh() {
                isRefreshing.set(true)
                reload()
            }
        }
    }*/

    private fun dispose() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    fun onBack(view: View) {

    }

    fun isAnimationFirstOnley(): Boolean? {
        return false
    }

    fun onDataLoadComplete() {

    }

    fun getItemSwipeListener(): OnItemSwipeListener? {
        return null
    }

    fun getOnSwipeMoveFrags(): Int {
        return -1
    }

    fun getItemDragListener(): OnItemDragListener? {
        return null
    }

    enum class EmptyViewType{
        EMPTY,ERROR,LOADING,REFRESH
        //空/错误/正在加载/刷新
    }


}