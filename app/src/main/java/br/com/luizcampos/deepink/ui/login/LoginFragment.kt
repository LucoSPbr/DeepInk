package br.com.luizcampos.deepink.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import br.com.luizcampos.deepink.R
import br.com.luizcampos.deepink.extensions.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*
import br.com.luizcampos.deepink.ui.base.auth.NAVIGATION_KEY

class LoginFragment : Fragment() {

    private lateinit var containerLogin: LinearLayout
    private lateinit var tvSubTitleLogin: TextView
    private lateinit var tvNewAccount: TextView
    private lateinit var tvResetPassword: TextView

    private lateinit var btLogin: Button
    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
    }

    private fun setUpView(view: View) {
        containerLogin = view.findViewById(R.id.containerLogin)
        tvSubTitleLogin = view.findViewById(R.id.tvSubTitleLogin)
        tvNewAccount = view.findViewById(R.id.tvNewAccount)
        tvResetPassword= view.findViewById(R.id.tvResetPassword)
        btLogin= view.findViewById(R.id.btLogin)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        tvNewAccount.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        btLogin.setOnClickListener {

            showSuccess()
        }
        startLoginAnimation()
        registerBackPressedAction()
    }

    private fun showSuccess() {
        val navIdFromArguments = arguments?.getInt(NAVIGATION_KEY)
        if (navIdFromArguments == null) {
            //hideKeyboard()
            findNavController().navigate(R.id.main_nav_graph)
        } else {
            findNavController().popBackStack(navIdFromArguments, false)
        }
    }
    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun startLoginAnimation() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_form_login)
        containerLogin.startAnimation(anim)
        tvSubTitleLogin.startAnimation(anim)
        tvNewAccount.startAnimation(anim)
        tvResetPassword.startAnimation(anim)
    }
}