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
import kotlinx.android.synthetic.main.fragment_cafe.*
import kotlinx.android.synthetic.main.fragment_caffeine.*

class CaffeineFragment : Fragment() {

    lateinit var navController: NavController
    var result = "0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_caffeine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey2") { requestKey, bundle ->
            result = bundle.getString("bundleKey2").toString()
            Log.d("데이터 ", "(CaffeineFragment) 현재 응답 데이터 : ${result}")
        }
        navController = Navigation.findNavController(view)

        btn_caffeine_back.setOnClickListener {
            result = result.substring(0, result.length-1)
            setFragmentResult("requestKey1", bundleOf("bundleKey1" to result))
            navController.navigate(R.id.action_caffeineFragment_to_milkFragment) }
        btn_caffeine_home.setOnClickListener { navController.navigate(R.id.action_caffeineFragment_to_mainFragment) }

        btn_caffeine.setOnClickListener {
            result+="1"
            setFragmentResult("requestKey3", bundleOf("bundleKey3" to result))
            navController.navigate(R.id.action_caffeineFragment_to_coffeeNonFragment) }
        btn_non_caffeine.setOnClickListener {
            result+="0"
            setFragmentResult("requestKey3", bundleOf("bundleKey3" to result))
            navController.navigate(R.id.action_caffeineFragment_to_coffeeNonFragment) }
    }
}