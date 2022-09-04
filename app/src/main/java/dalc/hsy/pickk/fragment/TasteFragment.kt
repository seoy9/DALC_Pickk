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
import kotlinx.android.synthetic.main.fragment_milk.*
import kotlinx.android.synthetic.main.fragment_taste.*

class TasteFragment : Fragment() {

    lateinit var navController: NavController
    var result = "0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_taste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey4") { requestKey, bundle ->
            result = bundle.getString("bundleKey4").toString()
            Log.d("데이터 ", "(TasteFragment) 현재 응답 데이터 : ${result}")
        }
        navController = Navigation.findNavController(view)

        btn_taste_back.setOnClickListener {
            result = result.substring(0, result.length-1)
            setFragmentResult("requestKey3", bundleOf("bundleKey3" to result))
            navController.navigate(R.id.action_tasteFragment_to_coffeeNonFragment) }
        btn_taste_home.setOnClickListener { navController.navigate(R.id.action_tasteFragment_to_mainFragment) }

        btn_sweet.setOnClickListener {
            result+="0"
            setFragmentResult("requestKey5", bundleOf("bundleKey5" to result))
            navController.navigate(R.id.action_tasteFragment_to_carbonFragment) }
        btn_fresh.setOnClickListener {
            result+="1"
            setFragmentResult("requestKey5", bundleOf("bundleKey5" to result))
            navController.navigate(R.id.action_tasteFragment_to_carbonFragment) }
        btn_bitter.setOnClickListener {
            result+="2"
            setFragmentResult("requestKey5", bundleOf("bundleKey5" to result))
            navController.navigate(R.id.action_tasteFragment_to_carbonFragment) }
        btn_aromatic.setOnClickListener {
            result+="3"
            setFragmentResult("requestKey5", bundleOf("bundleKey5" to result))
            navController.navigate(R.id.action_tasteFragment_to_carbonFragment) }


    }
}