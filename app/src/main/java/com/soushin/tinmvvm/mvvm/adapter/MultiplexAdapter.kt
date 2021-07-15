package com.soushin.tinmvvm.mvvm.adapter

import androidx.viewpager.widget.ViewPager
import com.blankj.ALog
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.tabs.TabLayout
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.mvvm.repository.entity.AuthorEntity
import com.soushin.tinmvvm.app.widget.CategoryView
import com.soushin.tinmvvm.app.widget.ChildRecyclerView

/**
 * @author created by Soushin
 * @time 2020/1/20 15 43
 */
class MultiplexAdapter : BaseAdapter<AuthorEntity, BaseViewHolder>() {

    private var childRecyclerView: ChildRecyclerView?=null
    val viewList = ArrayList<ChildRecyclerView>()
    init {
        addItemType(ITEM_ONE, R.layout.layout_item_text)
        addItemType(ITEM_TWO,R.layout.layout_item_category)
    }

    override fun convert(helper: BaseViewHolder, item: AuthorEntity) {
        super.convert(helper, item)
        when (helper.itemViewType){
            ITEM_ONE ->{
                helper.setText(R.id.textView,item.itemType.toString()+"0")
            }
            ITEM_TWO ->{
                val mTabLayout=helper.getView<TabLayout>(R.id.tabs)
                val mViewPager=helper.getView<ViewPager>(R.id.viewPager)

                mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                    }
                    override fun onPageSelected(position: Int) {
                        if(viewList.isNotEmpty()) {
                            childRecyclerView = viewList[position]
                            ALog.d("onPageSelected: $position $childRecyclerView")
                        }
                    }
                    override fun onPageScrollStateChanged(state: Int) {

                    }
                })
                item.apply {
                    viewList.clear()
                    tabTitleList?.forEach{ _ ->
                        val categoryView = CategoryView(helper.itemView.context)
                        viewList.add(categoryView)
                    }
                    childRecyclerView = viewList[mViewPager.currentItem]
                    val lastItem = mViewPager.currentItem
                    ALog.d("bindData: ${mViewPager.currentItem} $childRecyclerView")
                    tabTitleList?.apply {
                        mViewPager.adapter = CategoryPagerAdapter(viewList,this)
                    }

                    mTabLayout.setupWithViewPager(mViewPager)
                    mViewPager.currentItem = lastItem
                }
            }
        }
    }

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        return childRecyclerView
    }


}