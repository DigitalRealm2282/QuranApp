package com.tdi.holyquran.ui.main.quran_sura

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tdi.holyquran.R
import com.tdi.holyquran.data.adapter.SurahRecyclerAdapter
import com.tdi.holyquran.data.db.entities.Sura
import com.tdi.holyquran.data.listener.SurahRecyclerListener
import com.tdi.holyquran.data.util.changeNavigation
import com.tdi.holyquran.databinding.FragmentQuranSuraBinding
import com.tdi.holyquran.ui.base.BaseFragment

class QuranSuraFragment : BaseFragment<FragmentQuranSuraBinding, QuranSuraViewModel>(),
    SurahRecyclerListener {

    private lateinit var adapter: SurahRecyclerAdapter

    override var getLayoutId: Int = R.layout.fragment_quran_sura
    override var getViewModel: Class<QuranSuraViewModel> = QuranSuraViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFragment()
        getDataSura()
    }

    private fun setUpFragment() {
        adapter = SurahRecyclerAdapter(this)
        mViewBinding.rvQuranSura.layoutManager = LinearLayoutManager(activity)
        mViewBinding.rvQuranSura.adapter = adapter
    }

    private fun getDataSura() {
        getSuraSearch("")

        with(mViewBinding.persistentSearchView) {
            setOnSearchConfirmedListener { searchView, query ->
                searchView.collapse()
                getSuraSearch(query)
            }

            hideLeftButton()
            hideRightButton()
            isVoiceInputButtonEnabled = false

            setOnClearInputBtnClickListener {
                getSuraSearch("")
            }
        }
    }

    private fun getSuraSearch(s: String) {
        mViewModel.getAllSura(s).observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                adapter.updateList(list)
            }
        })
    }

    override fun onClick(view: View, sura: Sura) {
        val action = QuranSuraFragmentDirections.actionNavigationQuranToQuranAyahFragment(sura)
        view.changeNavigation(action)
    }
}
