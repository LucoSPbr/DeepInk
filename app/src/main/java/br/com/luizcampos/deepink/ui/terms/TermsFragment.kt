package br.com.luizcampos.deepink.ui.terms

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import br.com.luizcampos.deepink.R
import br.com.luizcampos.deepink.ui.base.BaseFragment

class TermsFragment : BaseFragment() {
    override val layout = R.layout.fragment_terms
    private lateinit var wvTerms: WebView
    private lateinit var ivBack: ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wvTerms = view.findViewById(R.id.wvTerms)
        ivBack = view.findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        wvTerms.loadUrl("https://deep-ink-d2dd6.web.app")
    }
}