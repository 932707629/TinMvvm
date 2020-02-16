package com.soushin.tinmvvm.mvvm.model.provider

import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.blankj.ALog
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.tabs.TabLayout
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.mvvm.adapter.CategoryPagerAdapter
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity
import com.soushin.tinmvvm.mvvm.model.entity.CategoryBean
import com.soushin.tinmvvm.widget.CategoryView
import com.soushin.tinmvvm.widget.ChildRecyclerView

/**
 *
 * @auther SouShin
 * @time 2020/2/16 15:24
 */
class RecyclerCategoryProvider : BaseItemProvider<AuthorEntity>() {
    override val itemViewType: Int
        get() = BaseAdapter.ITEM_TWO
    override val layoutId: Int
        get() = R.layout.layout_item_category

    private var mCurrentRecyclerView : ChildRecyclerView? = null
    val viewList = ArrayList<ChildRecyclerView>()


    override fun convert(helper: BaseViewHolder, data: AuthorEntity?) {
        val mTabLayout=helper.getView<TabLayout>(R.id.tabs)
        val mViewPager=helper.getView<ViewPager>(R.id.viewPager)

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if(viewList.isNotEmpty()) {
                    mCurrentRecyclerView = viewList[position]
                    ALog.d("onPageSelected: $position $mCurrentRecyclerView")
                }
            }
            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        data?.apply {
            viewList.clear()
            tabTitleList?.forEach{ _ ->
                val categoryView = CategoryView(helper.itemView.context)
                viewList.add(categoryView)
            }
            mCurrentRecyclerView = viewList[mViewPager.currentItem]
            val lastItem = mViewPager.currentItem
            ALog.d("bindData: ${mViewPager.currentItem} $mCurrentRecyclerView")
            tabTitleList?.apply {
                mViewPager.adapter = CategoryPagerAdapter(viewList,this)
            }

            mTabLayout.setupWithViewPager(mViewPager)
            mViewPager.currentItem = lastItem
        }
    }

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        return mCurrentRecyclerView
    }

}