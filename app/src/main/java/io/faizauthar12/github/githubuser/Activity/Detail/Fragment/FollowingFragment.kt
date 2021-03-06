package io.faizauthar12.github.githubuser.Activity.Detail.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.faizauthar12.github.githubuser.Activity.Detail.Fragment.Adapter.FollowingAdapter
import io.faizauthar12.github.githubuser.R
import io.faizauthar12.github.githubuser.Activity.Detail.Fragment.ViewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME,username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)

        rvFollowing(username)
    }

    private fun rvFollowing(username: String?) {
        showLoading(true)

        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()

        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.adapter = followingAdapter

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        if (username != null) {
            followingViewModel.setFollowing(username)
        }

        followingViewModel.getFollowing().observe(viewLifecycleOwner, {FollowingItems ->
            if (FollowingItems != null) {
                followingAdapter.setData(FollowingItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_FragmentFollowing.visibility = View.VISIBLE
        } else {
            pb_FragmentFollowing.visibility = View.GONE
        }
    }
}