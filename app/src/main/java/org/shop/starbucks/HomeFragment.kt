package org.shop.starbucks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.shop.starbucks.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeData = context?.readData() ?: return

        binding.appbarTitleTextView.text =
            resources.getString(R.string.appbar_title_text, homeData.user.nickname)

        binding.startCountTextView.text = resources.getString(
            R.string.appbar_star_title,
            homeData.user.starCount,
            homeData.user.totalCount
        )

        binding.appbarProgressBar.progress = homeData.user.starCount
        binding.appbarProgressBar.max = homeData.user.totalCount

        Glide.with(binding.appbarImageView).load(homeData.appbarImage).into(binding.appbarImageView)
    }
}