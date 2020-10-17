package br.com.luizcampos.deepink.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import br.com.luizcampos.deepink.BuildConfig
import br.com.luizcampos.deepink.R
import br.com.luizcampos.deepink.utils.firebase.RemoteConfigKeys
import br.com.luizcampos.deepink.utils.firebase.RemoteConfigUtils


abstract class BaseFragment : Fragment() {

    abstract val layout: Int
    private lateinit var loadingView: View

    override fun onCreateView(
     inflater: LayoutInflater,
     container: ViewGroup?,
     savedInstanceState: Bundle?
    ): View? {
        val screenRootView = FrameLayout(requireContext())
        val screenView = inflater.inflate(layout, container, false)
        loadingView = inflater.inflate(R.layout.include_loading, container, false)
        screenRootView.addView(screenView)
        screenRootView.addView(loadingView)
        return screenRootView
    }

    fun showLoading(message: String = "Processando a requisição") {
        loadingView.visibility = View.VISIBLE
        if (message.isNotEmpty())
            loadingView.findViewById<TextView>(R.id.tvLoading).text = message
    }

    fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        checkMinVersion()
    }

    private fun checkMinVersion() {
        val minVersionApp = RemoteConfigUtils.getFirebaseRemoteConfig()
            .getLong(RemoteConfigKeys.MIN_VERSION_APP) //"min_version_app")
        if (minVersionApp > BuildConfig.VERSION_CODE) {
            startUpdateApp()
        }
    }

    private fun startUpdateApp() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.updateAppFragment, true) .build()
        findNavController().setGraph(R.navigation.update_app_nav_graph)
        findNavController().navigate(R.id.updateAppFragment, null, navOptions) }
}