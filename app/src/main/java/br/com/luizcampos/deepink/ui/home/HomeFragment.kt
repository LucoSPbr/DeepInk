package br.com.luizcampos.deepink.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import br.com.luizcampos.deepink.R
import br.com.luizcampos.deepink.model.RequestState
import br.com.luizcampos.deepink.model.dashboardmenu.DashboardItem
import br.com.luizcampos.deepink.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override val layout = R.layout.fragment_home
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var rvHomeDashboard: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        homeViewModel.createMenu()
        registerObserver()
    }
    private fun setUpView(view: View) {
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
    }

    private fun registerObserver() {
        homeViewModel.menuState.observe(viewLifecycleOwner, Observer {
        when (it) {
            is RequestState.Loading -> {
                showLoading() }
            is RequestState.Success -> {
                hideLoading()
                setUpMenu(it.data)
            }
            is RequestState.Error -> {
                hideLoading() }
        } })
        homeViewModel.userNameState.observe(viewLifecycleOwner, Observer { when (it) {
            is RequestState.Loading -> {
                tvHomeHelloUser.text = "Carregando ..."
            }
            is RequestState.Success -> {
                tvHomeHelloUser.text = String.format(homeViewModel.dashboardMenu?.title ?: "", it.data)
                tvSubTitleSignUp.text = homeViewModel.dashboardMenu?.subTitle }
            is RequestState.Error -> {
                tvHomeHelloUser.text = "Bem-vindo"
                tvSubTitleSignUp.text = homeViewModel.dashboardMenu?.subTitle
            } }
        })
    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter = HomeListAdapter(items, this::clickItem)
    }

   private fun clickItem(item: DashboardItem) {
        item.onDisabledListener.let {
            it?.invoke(requireContext())
        }
        if (item.onDisabledListener == null) {
            showMessage(item.label)
        }
    }
}