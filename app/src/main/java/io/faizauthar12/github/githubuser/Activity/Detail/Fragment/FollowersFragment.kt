package io.faizauthar12.github.githubuser.Activity.Detail.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.faizauthar12.github.githubuser.Activity.Detail.Fragment.Adapter.FollowersAdapter
import io.faizauthar12.github.githubuser.R
import io.faizauthar12.github.githubuser.Activity.Detail.Fragment.ViewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {
    private lateinit var followersAdapter : FollowersAdapter
    private lateinit var followersViewModel : FollowersViewModel

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME,username)
            fragment.arguments = bundle
            return  fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)

        rvFollowers(username)
    }

    private fun rvFollowers(username: String?) {
        showLoading(true)

        followersAdapter = FollowersAdapter()
        followersAdapter.notifyDataSetChanged()

        rv_followers.layoutManager = LinearLayoutManager(activity)
        rv_followers.adapter = followersAdapter

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        if (username != null) {
            followersViewModel.setFollowers(username)
        }

        followersViewModel.getFollowers().observe(viewLifecycleOwner, {FollowersItems ->
            if (FollowersItems != null) {
                followersAdapter.setData(FollowersItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_FragmentFollowers.visibility = View.VISIBLE
        } else {
            pb_FragmentFollowers.visibility = View.GONE
        }
    }
}