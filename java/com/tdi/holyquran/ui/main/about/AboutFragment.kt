package com.tdi.holyquran.ui.main.about

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.tdi.holyquran.R
import com.tdi.holyquran.data.model.Status
import com.tdi.holyquran.databinding.FragmentAboutBinding
import com.tdi.holyquran.ui.base.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>() {

    override var getLayoutId: Int = R.layout.fragment_about
    override var getViewModel: Class<AboutViewModel> = AboutViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGithubProfile()
        mViewBinding.srlAbout.setOnRefreshListener {
            observeGithubProfile()
        }
    }

    private fun observeGithubProfile() {
        mViewModel.getUserProfile().observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status.status) {
                    Status.StatusType.LOADING -> {
                        mViewBinding.srlAbout.isRefreshing = true
                    }
                    Status.StatusType.SUCCESS -> {
                        mViewBinding.srlAbout.isRefreshing = false
                        mViewBinding.profile = status.data
                    }
                    Status.StatusType.ERROR -> {
                        Toast.makeText(activity, "Error : ${status.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }
}
