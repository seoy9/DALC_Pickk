package dalc.hsy.pickk.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dalc.hsy.pickk.R
import kotlinx.android.synthetic.main.fragment_coffee_non.*
import kotlinx.android.synthetic.main.fragment_hot_ice.*

class HotIceFragment : Fragment() {

    lateinit var navController: NavController
    var result="0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hot_ice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        btn_hot_ice_back.setOnClickListener { navController.navigate(R.id.action_hotIceFragment_to_wayFragment) }
        btn_hot_ice_home.setOnClickListener { navController.navigate(R.id.action_hotIceFragment_to_mainFragment) }

        btn_hot.setOnClickListener {
            result="0"
            setFragmentResult("requestKey1", bundleOf("bundleKey1" to result))
            navController.navigate(R.id.action_hotIceFragment_to_milkFragment)
        }
        btn_ice.setOnClickListener {
            result="1"
            setFragmentResult("requestKey1", bundleOf("bundleKey1" to result))
            navController.navigate(R.id.action_hotIceFragment_to_milkFragment)
        }
    }
}