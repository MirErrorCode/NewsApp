package mir.errorcode.newsapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import mir.errorcode.newsapp.databinding.FragmentDetailsBinding
import androidx.core.net.toUri
import androidx.fragment.app.viewModels

;
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mir.errorcode.newsapp.R

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!
    private val bundleArgs: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleArg = bundleArgs.article

        articleArg.let { article ->
            article.urlToImage.let {
                Glide.with(this).load(article.urlToImage).into(mBinding.headerImage)
            }
            mBinding.headerImage.clipToOutline = true
            mBinding.articleDetailsTitle.text = article.title
            mBinding.articleDetailsDescriptionText.text = article.description


            viewModel.viewModelScope.launch(Dispatchers.IO) {
                val isFavorite = viewModel.isFavorite(article)
                val iconRes = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
                requireActivity().runOnUiThread {
                    mBinding.iconFavorite.setImageResource(iconRes)
                }
            }



            mBinding.articleDetailsButton.setOnClickListener {
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData((takeIf { URLUtil.isValidUrl(article.url) }
                            ?.let {
                            article.url
                        } ?: "https://google.com").toUri()).let {
                    ContextCompat.startActivity(requireContext(), it, null)
                }
            } catch (e: Exception){
            Toast.makeText(
                context, "The device doesn`t have any browser to view the page!",
                Toast.LENGTH_SHORT
            ).show()
        }
        }
            mBinding.iconFavorite.setOnClickListener {
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    viewModel.toggleFavorite(article)
                    val isNowFavorite = viewModel.isFavorite(article)
                    requireActivity().runOnUiThread {
                        mBinding.iconFavorite.setImageResource(
                            if (isNowFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
                        )
                        parentFragmentManager.setFragmentResult(
                            "favorite_changed", Bundle().apply { putBoolean("changed", true) }
                        )
                    }
                }
            }

            mBinding.iconBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

    }
}
}