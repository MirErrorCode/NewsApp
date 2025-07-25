package mir.errorcode.newsapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mir.errorcode.newsapp.R
import mir.errorcode.newsapp.databinding.FragmentDetailsBinding
import mir.errorcode.newsapp.databinding.FragmentFavoriteBinding
import mir.errorcode.newsapp.ui.adapters.NewsAdapter
import mir.errorcode.newsapp.ui.details.DetailsFragment

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsAdapter()

        adapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            view.findNavController().navigate(
                R.id.action_favoriteFragment_to_detailsFragment,
                bundle
            )
        }
        adapter.setOnFavoriteClickListener { article ->
            viewModel.deleteArticle(article)
        }


        mBinding.recyclerViewFavorites.adapter = adapter
        mBinding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())

        viewModel.favoriteArticles.observe(viewLifecycleOwner) { articles ->
            articles.forEach { it.isFavorite = true }
            adapter.differ.submitList(articles)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}