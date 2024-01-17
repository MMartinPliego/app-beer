package com.orumgames.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.orumgames.ui.R
import com.orumgames.ui.base.BaseFragment
import com.orumgames.ui.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val mViewModel: SplashViewModel by viewModels()
    override fun getViewBinding(): FragmentSplashBinding = FragmentSplashBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = FragmentSplashBinding.inflate(inflater)
        setupAnim()
        navigate()
        return mViewBinding.root
    }

    private fun setupAnim() {
        mViewBinding.animationView.setAnimation(R.raw.splash_lottie)
        mViewBinding.animationView.repeatCount = LottieDrawable.INFINITE
        mViewBinding.animationView.playAnimation()
    }

    private fun navigate() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashFragmentDirections.goToHome())
        }, SPLASH_DELAY)
    }

    companion object {
        const val SPLASH_DELAY = 4000L
    }
}