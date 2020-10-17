package br.com.luizcampos.deepink.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.luizcampos.deepink.extensions.fromRemoteConfig
import br.com.luizcampos.deepink.model.RequestState
import br.com.luizcampos.deepink.model.dashboardmenu.DashboardItem
import br.com.luizcampos.deepink.model.dashboardmenu.DashboardMenu
import br.com.luizcampos.deepink.utils.featuretoggle.FeatureToggleHelper
import br.com.luizcampos.deepink.utils.featuretoggle.FeatureToggleListener
import br.com.luizcampos.deepink.utils.firebase.RemoteConfigKeys
import com.google.gson.Gson

class HomeViewModel : ViewModel() {
    val menuState = MutableLiveData<RequestState<List<DashboardItem>>>()

    fun createMenu() {
        menuState.value = RequestState.Loading
        val dashboardMenu = Gson().fromRemoteConfig(
            RemoteConfigKeys.MENU_DASHBOARD,
            DashboardMenu::class.java)
        val dashBoardItems = arrayListOf<DashboardItem>()
        val itemsMenu = dashboardMenu?.items ?: listOf()

        for (itemMenu in itemsMenu) {
            FeatureToggleHelper().configureFeature(
            itemMenu.feature,
            object : FeatureToggleListener {
                override fun onEnabled() {
                    dashBoardItems.add(itemMenu)
                }
                override fun onInvisible() {}
                override fun onDisabled(clickListener: (Context) -> Unit) {
                    itemMenu.onDisabledListener = clickListener
                    dashBoardItems.add(itemMenu)
                } })
        }
        menuState.value = RequestState.Success(dashBoardItems) }
}