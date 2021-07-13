package com.soushin.tinmvvm.widget

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.blankj.ALog

/**
 * 用以解决navigation会导致fragment重复创建的问题
 * 如果用navigation会造成fragment重复创建 解决办法
 * {@see https://zhuanlan.zhihu.com/p/65200770}
 * {@see https://blog.csdn.net/siyemuzi/article/details/106527527}
 * 缺点:回退不会走onResume生命周期 因为tinmvvm有用户可见监听 所以并不会有太大的问题
 * 参见[androidx.navigation.fragment.FragmentNavigator]
 */
@Navigator.Name("fixFragment") //这是新的Navigator得名称,千万别忘了加
class FixFragmentNavigator(val context: Context, val fragmentManager: FragmentManager, val containerId: Int) :
    FragmentNavigator(
        context,
        fragmentManager,
        containerId
    ) {
    private val savedIds = mutableSetOf<String>()

    /**
     * {@inheritDoc}
     *
     * This method must call
     * [FragmentTransaction.setPrimaryNavigationFragment]
     * if the pop succeeded so that the newly visible Fragment can be retrieved with
     * [FragmentManager.getPrimaryNavigationFragment].
     *
     * Note that the default implementation pops the Fragment
     * asynchronously, so the newly visible Fragment from the back stack
     * is not instantly available after this call completes.
     */
    override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        if (fragmentManager.isStateSaved) {
            ALog.i("Ignoring popBackStack() call: FragmentManager has already saved its state")
            return
        }
        if (savedState) {
            val beforePopList = state.backStack.value
            val initialEntry = beforePopList.first()
            // Get the set of entries that are going to be popped
            val poppedList = beforePopList.subList(
                beforePopList.indexOf(popUpTo),
                beforePopList.size
            )
            // Now go through the list in reversed order (i.e., started from the most added)
            // and save the back stack state of each.
            for (entry in poppedList.reversed()) {
                if (entry == initialEntry) {
                    ALog.i("FragmentManager cannot save the state of the initial destination $entry")
                } else {
                    fragmentManager.saveBackStack(entry.id)
                    savedIds += entry.id
                }
            }
        } else {
            fragmentManager.popBackStack(
                popUpTo.id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        state.pop(popUpTo, savedState)
    }

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
//        super.navigate(entries, navOptions, navigatorExtras)
        if (fragmentManager.isStateSaved) {
            ALog.i( "Ignoring navigate() call: FragmentManager has already saved its state")
            return
        }
        entries.forEach {
            navigate(it,navOptions,navigatorExtras)
        }
    }

    private fun navigate(
        entry: NavBackStackEntry,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        val backStack = state.backStack.value
        val initialNavigation = backStack.isEmpty()
        val restoreState = (
                navOptions != null && !initialNavigation &&
                        navOptions.shouldRestoreState() && savedIds.remove(entry.id)
                )
        if (restoreState) {
            // Restore back stack does all the work to restore the entry
            fragmentManager.restoreBackStack(entry.id)
            state.add(entry)
            return
        }
        val destination = entry.destination as Destination
        val args = entry.arguments
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }
        val frag = fragmentManager.fragmentFactory.instantiate(context.classLoader, className)
        frag.arguments = args
        val ft = fragmentManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
//        ft.replace(containerId, frag)
        fragmentManager.primaryNavigationFragment?.let {
            ft.hide(it)
        }
        if (!frag.isAdded) {
            ft.add(containerId, frag, className);
        }
        ft.show(frag);
        ft.setPrimaryNavigationFragment(frag)
        @IdRes val destId = destination.id
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (
                navOptions != null && !initialNavigation &&
                        navOptions.shouldLaunchSingleTop() &&
                        backStack.last().destination.id == destId
                )
        val isAdded = when {
            initialNavigation -> {
                true
            }
            isSingleTopReplacement -> {
                // Single Top means we only want one instance on the back stack
                if (backStack.size > 1) {
                    // If the Fragment to be replaced is on the FragmentManager's
                    // back stack, a simple replace() isn't enough so we
                    // remove it from the back stack and put our replacement
                    // on the back stack in its place
                    fragmentManager.popBackStack(
                        entry.id,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    ft.addToBackStack(entry.id)
                }
                false
            }
            else -> {
                ft.addToBackStack(entry.id)
                true
            }
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        if (isAdded) {
            state.add(entry)
        }
    }


    public override fun onSaveState(): Bundle? {
        if (savedIds.isEmpty()) {
            return null
        }
        return bundleOf(KEY_SAVED_IDS to ArrayList(savedIds))
    }

    public override fun onRestoreState(savedState: Bundle) {
        val savedIds = savedState.getStringArrayList(KEY_SAVED_IDS)
        if (savedIds != null) {
            this.savedIds.clear()
            this.savedIds += savedIds
        }
    }

    private companion object {
        private const val KEY_SAVED_IDS = "androidx-nav-fragment:navigator:savedIds"
    }

}