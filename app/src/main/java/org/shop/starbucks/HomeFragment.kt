package org.shop.starbucks

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.shop.starbucks.data.Home
import org.shop.starbucks.data.Menu
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

        val homeData = context?.readData("home.json", Home::class.java) ?: return
        val menuData = context?.readData("menu.json", Menu::class.java) ?: return

        initAppBar(homeData)
        initRecommendMenuList(homeData, menuData)
        initBanner(homeData)
        initFoodList(menuData)

        binding.scrollView.setOnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY == 0) {
                binding.floatingActionButton.extend()
            } else {
                binding.floatingActionButton.shrink()
            }
        }
    }

    private fun initFoodList(menuData: Menu) {
        binding.foodMenuList.titleTextView.text =
            getString(R.string.food_menu_title)

        menuData.food.forEach { foodItem ->
            binding.foodMenuList.menuLayout.addView(MenuView(context = requireContext()).apply {
                setTitle(foodItem.name)
                setImageUrl(foodItem.image)
            })
        }
    }

    private fun initBanner(homeData: Home) {
        binding.bannerLayout.bannerImageView.apply {
            Glide.with(this).load(homeData.banner.image).into(this)
            this.contentDescription = homeData.banner.contentDescription
        }
    }

    private fun initRecommendMenuList(
        homeData: Home,
        menuData: Menu
    ) {
        binding.recommendMenuList.titleTextView.text =
            getString(R.string.recommend_title, homeData.user.nickname)

        menuData.coffee.forEach { menuItem ->
            binding.recommendMenuList.menuLayout.addView(MenuView(context = requireContext()).apply {
                setTitle(menuItem.name)
                setImageUrl(menuItem.image)
            })
        }
    }

    private fun initAppBar(homeData: Home) {
        binding.appbarTitleTextView.text =
            resources.getString(R.string.appbar_title_text, homeData.user.nickname)

        binding.startCountTextView.text = resources.getString(
            R.string.appbar_star_title,
            homeData.user.starCount,
            homeData.user.totalCount
        )

        binding.appbarProgressBar.max = homeData.user.totalCount

        Glide.with(binding.appbarImageView).load(homeData.appbarImage).into(binding.appbarImageView)

        ValueAnimator.ofInt(0, homeData.user.starCount).apply {
            duration = 1000
            addUpdateListener {
                binding.appbarProgressBar.progress = it.animatedValue as Int
            }
            start()
        }
    }
}