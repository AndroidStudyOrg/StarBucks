package org.shop.starbucks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.shop.starbucks.data.Menu
import org.shop.starbucks.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuData = context?.readData("menu.json", Menu::class.java) ?: return

        menuAdapter = MenuAdapter().apply {
            submitList(menuData.coffee)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuAdapter
        }
    }
}