package dalc.hsy.pickk.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dalc.hsy.pickk.R
import kotlinx.android.synthetic.main.fragment_hot_ice.*
import kotlinx.android.synthetic.main.fragment_milk.*

class MilkFragment : Fragment() {

    lateinit var navController: NavController
    var result = "0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_milk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey1") { requestKey, bundle ->
            result = bundle.getString("bundleKey1").toString()
            Log.d("데이터", "(MilkFragment) 현재 응답 데이터 : ${result}")
        }
        navController = Navigation.findNavController(view)

        btn_milk_back.setOnClickListener { navController.navigate(R.id.action_milkFragment_to_hotIceFragment) }
        btn_milk_home.setOnClickListener { navController.navigate(R.id.action_milkFragment_to_mainFragment) }

        btn_milk.setOnClickListener {
            result+="1"
            setFragmentResult("requestKey2", bundleOf("bundleKey2" to result))
            navController.navigate(R.id.action_milkFragment_to_caffeineFragment)
        }
        btn_non_milk.setOnClickListener {
            result+="0"
            setFragmentResult("requestKey2", bundleOf("bundleKey2" to result))
            navController.navigate(R.id.action_milkFragment_to_caffeineFragment)
        }
    }
}