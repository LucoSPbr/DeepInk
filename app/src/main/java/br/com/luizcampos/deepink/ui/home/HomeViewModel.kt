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
import br.com.luizcampos.deepink.utils.firebase.RemoteConfigUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class HomeViewModel : ViewModel() {
    val menuState = MutableLiveData<RequestState<List<DashboardItem>>>()
    private val db = FirebaseFirestore.getInstance()
    var dashboardMenu: DashboardMenu? = null
    val userNameState = MutableLiveData<RequestState<String>>()

    fun createMenu() {
        menuState.value = RequestState.Loading
        //val dashboardMenu =
        //    Gson().fromRemoteConfig(
        //        RemoteConfigKeys.MENU_DASHBOARD,
        //        DashboardMenu::class.java)
        RemoteConfigUtils.fetchAndActivate()
            .addOnCompleteListener {
                dashboardMenu = Gson().fromRemoteConfig(
                    RemoteConfigKeys.MENU_DASHBOARD,
                    DashboardMenu::class.java
                )
                getUser()
            }

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
        menuState.value = RequestState.Success(dashBoardItems)
    }

    private fun getUser() {
        userNameState.value = RequestState.Loading
        val user = FirebaseAuth.getInstance().uid
        if (user == null) {
            userNameState.value = RequestState.Error(Throwable("Usuário deslogado")) } else {
            db.collection("users") .document(FirebaseAuth.getInstance().uid ?: "") .get()
                .addOnSuccessListener { documentSnapshot ->
                    val userName = documentSnapshot.data?.get("username") as String
                    userNameState.value = RequestState.Success(userName) }
                .addOnFailureListener {
                    userNameState.value = RequestState.Error(it)
                }
        }
    }
}