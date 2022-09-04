package dalc.hsy.pickk.fragment

import android.content.Context
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
import dalc.hsy.pickk.*
import kotlinx.android.synthetic.main.fragment_beverage_info.*
import kotlinx.android.synthetic.main.fragment_cafe.*

class CafeFragment : Fragment() {

    lateinit var navController: NavController
    var result = "0"
    var resultArray = IntArray(7)
    lateinit var recommdbHelper: RecommendMenuDBHelper
    lateinit var con: Context
    lateinit var recommInfo : RecommendMenu
    override fun onAttach(context: Context) {
        super.onAttach(context)
        con = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recommdbHelper = RecommendMenuDBHelper(con, "recommmenu", null, 1)
        return inflater.inflate(R.layout.fragment_cafe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey6") { requestKey, bundle ->
            result = bundle.getString("bundleKey6").toString()
            Log.d("데이터", "(CafeFragment) 현재 응답 데이터 : ${result}")
        }
        navController = Navigation.findNavController(view)

        btn_cafe_back.setOnClickListener {
            result = result.substring(0, result.length-1)
            setFragmentResult("requestKey5", bundleOf("bundleKey5" to result))
            navController.navigate(R.id.action_cafeFragment_to_carbonFragment) }
        btn_cafe_home.setOnClickListener { navController.navigate(R.id.action_cafeFragment_to_mainFragment) }

        btn_starbucks.setOnClickListener {
            result+="0"
            //info1 = dbHelper.selectMenu(14) //변경
            recommInfo = recommdbHelper.recommMenu(result)
            //var t = info1.menuName //지우기
            // 주석 해제
            var menuId1 = recommInfo.menuId1
            var menuId2 = recommInfo.menuId2
            var menuId3= recommInfo.menuId3
             //*/
            for(i: Int in 0..6)
                resultArray[i] = Character.getNumericValue(result[i])
            //setFragmentResult("requestKey7", bundleOf("bundleKey7" to result, "test" to t)) //지우기
            setFragmentResult("requestKey7", bundleOf("bundleKey7" to result, "bundleKey7_menuId1" to menuId1, "bundleKey7_menuId2" to menuId2, "bundleKey7_menuId3" to menuId3))
            setFragmentResult("count_resultKey1", bundleOf("count_bundleKey1" to 1))
            navController.navigate(R.id.action_cafeFragment_to_recommendFragment) }
        btn_ediya.setOnClickListener {
            result+="1"
            for(i: Int in 0..6)
                resultArray[i] = Character.getNumericValue(result[i])
            ///* 주석 해제
            recommInfo = recommdbHelper.recommMenu(result)
            var menuId1 = recommInfo.menuId1
            var menuId2 = recommInfo.menuId2
            var menuId3= recommInfo.menuId3
             //*/
            //setFragmentResult("requestKey7", bundleOf("bundleKey7" to result)) //지우기
            setFragmentResult("requestKey7", bundleOf("bundleKey7" to result, "bundleKey7_menuId1" to menuId1, "bundleKey7_menuId2" to menuId2, "bundleKey7_menuId3" to menuId3))
            setFragmentResult("count_resultKey1", bundleOf("count_bundleKey1" to 1))
            navController.navigate(R.id.action_cafeFragment_to_recommendFragment) }
        btn_packs.setOnClickListener {
            result+="2"
            for(i: Int in 0..6)
                resultArray[i] = Character.getNumericValue(result[i])
            ///* 주석 해제
            recommInfo = recommdbHelper.recommMenu(result)
            var menuId1 = recommInfo.menuId1
            var menuId2 = recommInfo.menuId2
            var menuId3= recommInfo.menuId3
             //*/
            //setFragmentResult("requestKey7", bundleOf("bundleKey7" to result))//지우기
            setFragmentResult("requestKey7", bundleOf("bundleKey7" to result, "bundleKey7_menuId1" to menuId1, "bundleKey7_menuId2" to menuId2, "bundleKey7_menuId3" to menuId3))
            setFragmentResult("count_resultKey1", bundleOf("count_bundleKey1" to 1))
            navController.navigate(R.id.action_cafeFragment_to_recommendFragment) }
    }
}