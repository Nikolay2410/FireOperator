package com.example.fireoperator.ui.uslugi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fireoperator.R
import kotlinx.android.synthetic.main.fragment_uslugi.*

class UslugiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_uslugi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
        uBtn.setOnClickListener {
            setUpTabs()
        }
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(My(), "Мои")
        adapter.addFragment(All(), "Все")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}