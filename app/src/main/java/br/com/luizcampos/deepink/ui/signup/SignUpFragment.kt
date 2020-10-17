package br.com.luizcampos.deepink.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import br.com.luizcampos.deepink.R
import br.com.luizcampos.deepink.extensions.hideKeyboard
import br.com.luizcampos.deepink.ui.base.BaseFragment
import br.com.luizcampos.deepink.ui.base.auth.NAVIGATION_KEY

class SignUpFragment : BaseFragment() {
    override val layout = R.layout.fragment_sign_up
    private lateinit var tvTerms: TextView
    private lateinit var btCreateAccount: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTerms = view.findViewById(R.id.tvTerms)
        btCreateAccount= view.findViewById(R.id.btCreateAccount)
        tvTerms.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_signUpFragment_to_termsFragment)
        }
        btCreateAccount.setOnClickListener {
            hideKeyboard()
            showSuccess()
        }
    }

    private fun showSuccess() {
        hideLoading()
        val navIdFromArguments = arguments?.getInt(NAVIGATION_KEY)
        if (navIdFromArguments == null) {
            findNavController().navigate(R.id.main_nav_graph)
        } else {
            findNavController().popBackStack(navIdFromArguments, false)
        }
    }
}
