package com.tdi.holyquran.ui.main.quran_ayah

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tdi.holyquran.R
import com.tdi.holyquran.data.adapter.AyahRecyclerAdapter
import com.tdi.holyquran.data.db.entities.Sura
import com.tdi.holyquran.databinding.FragmentQuranAyahBinding
import com.tdi.holyquran.ui.base.BaseFragment
import com.tdi.holyquran.ui.main.MainActivity

class QuranAyahFragment : BaseFragment<FragmentQuranAyahBinding, QuranAyahViewModel>() {

    private lateinit var sura: Sura
    private lateinit var adapter: AyahRecyclerAdapter

    override var getLayoutId: Int = R.layout.fragment_quran_ayah
    override var getViewModel: Class<QuranAyahViewModel> = QuranAyahViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFragment()
        getDataAyah()
    }

    private fun setUpFragment() {
        adapter = AyahRecyclerAdapter(mViewModel.getFontSize())
        arguments?.let {
            sura = QuranAyahFragmentArgs.fromBundle(it).sura
        }

        (activity as MainActivity).supportActionBar?.title = "${sura.name} / ${sura.englishName}"

        mViewBinding.rvQuranAyah.layoutManager = LinearLayoutManager(activity)
        mViewBinding.rvQuranAyah.adapter = adapter
    }

    private fun getDataAyah() {
        mViewModel.getAyahBySura(sura.id).observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                adapter.updateList(list)
            }
        })
    }
}
