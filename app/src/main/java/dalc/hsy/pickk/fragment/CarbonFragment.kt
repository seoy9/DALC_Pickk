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
import kotlinx.android.synthetic.main.fragment_caffeine.*
import kotlinx.android.synthetic.main.fragment_carbon.*

class CarbonFragment : Fragment() {

    lateinit var navController: NavController
    var result = "0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carbon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey5") { requestKey, bundle ->
            result = bundle.getString("bundleKey5").toString()
            Log.d("데이터 ", "(CarbonFragment) 현재 응답 데이터 : ${result}")
        }
        navController = Navigation.findNavController(view)

        btn_carbon_back.setOnClickListener {
            result = result.substring(0, result.length-1)
            setFragmentResult("requestKey4", bundleOf("bundleKey4" to result))
            navController.navigate(R.id.action_carbonFragment_to_tasteFragment) }
        btn_carbon_home.setOnClickListener { navController.navigate(R.id.action_carbonFragment_to_mainFragment) }

        btn_carbon.setOnClickListener {
            result+="1"
            setFragmentResult("requestKey6", bundleOf("bundleKey6" to result))
            navController.navigate(R.id.action_carbonFragment_to_cafeFragment) }
        btn_non_carbon.setOnClickListener {
            result+="0"
            setFragmentResult("requestKey6", bundleOf("bundleKey6" to result))
            navController.navigate(R.id.action_carbonFragment_to_cafeFragment) }
    }
}