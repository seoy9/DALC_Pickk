package dalc.hsy.pickk.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
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
import dalc.hsy.pickk.Menu
import dalc.hsy.pickk.R
import dalc.hsy.pickk.menuDBHelper
import kotlinx.android.synthetic.main.fragment_beverage_info.*
import kotlinx.android.synthetic.main.fragment_choice.*

class BeverageInfoFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var dbHelper: menuDBHelper
    lateinit var con: Context
    lateinit var info : Menu
    var result = "0"
    var menuId = 0
    var color = arrayOf("#1B3C35", "#EDE7DB", "#F4EFDB", "#94CFCD") //0 : 초록, 1 : 하양, 2 : 노랑, 3 : 파랑
    var isRandom = "null"
    var count = 0
    var menuId1 = 0
    var menuId2 = 0
    var menuId3= 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        con = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = menuDBHelper(con, "menu", null, 1)
        return inflater.inflate(R.layout.fragment_beverage_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // '질문지로 음료 찾기'로 왔을 경우 현재 응답 데이터
        setFragmentResultListener("requestKey9") { requestKey, bundle ->
            result = bundle.getString("bundleKey9").toString()
            ///*
            menuId1 = bundle.getInt("bundleKey9_menuId1")
            menuId2 = bundle.getInt("bundleKey9_menuId2")
            menuId3 = bundle.getInt("bundleKey9_menuId3")
             //*/
            //Log.d("데이터", "(BeverageInfoFragment) 현재 응답 데이터 : ${result}")//변경
            Log.d("데이터", "(ChoiceFragment) 현재 응답 데이터 : ${result}, 추천 음료 1 ${menuId1}")
        }

        //Recommend에서 음료가 선택 된 적이 있는 경우
        setFragmentResultListener("count_resultKey3") { requestKey, bundle ->
            count = bundle.getInt("count_bundleKey3")
            Log.d("데이터", "(BeverageInfoFragment) 현재 음료 번호 : ${count}")
        }

        // 기본 '뒤로가기 버튼'의 기능 (beverageInfo > choice)
        btn_beverage_info_back.setOnClickListener {
            //setFragmentResult("requestKey8", bundleOf("bundleKey8" to result)) //지우기
            //
            setFragmentResult("requestKey8", bundleOf("bundleKey8" to result, "bundleKey8_menuId1" to menuId1, "bundleKey8_menuId2" to menuId2, "bundleKey8_menuId3" to menuId3))
            setFragmentResult("menuId_resultKey1", bundleOf("menuId_bundleKey1" to menuId))
            setFragmentResult("count_resultKey2", bundleOf("count_bundleKey2" to count))
            navController.navigate(R.id.action_beveragerInfoFragment_to_choiceFragment) }

        // '랜덤 버튼'으로 왔다면 random값 넘기기 (beverageInfo > choice)
        setFragmentResultListener("requestKeyRandom2") { requestKey, bundle ->
            isRandom = bundle.getString("bundleKeyRandom2").toString()
            Log.d("데이터", "(BeverageInfoFragment) isRandom : ${isRandom}")

            if(isRandom.equals("random")) {
                btn_beverage_info_back.setOnClickListener {
                    setFragmentResult("requestKeyRandom", bundleOf("bundleKeyRandom" to isRandom))
                    setFragmentResult("requestKey8", bundleOf("bundleKey8" to result))
                    setFragmentResult("menuId_resultKey1", bundleOf("menuId_bundleKey1" to menuId))
                    navController.navigate(R.id.action_beveragerInfoFragment_to_choiceFragment)
                }
            }
        }

        btn_beverage_info_home.setOnClickListener { navController.navigate(R.id.action_berverageInfoFragment_to_mainFragment) }

        setFragmentResultListener("menuId_resultKey2") { requestKey, bundle ->
            menuId = bundle.getInt("menuId_bundleKey2")
            Log.d("데이터", "(BeverageInfoFragment) menuID : $menuId")
            info = dbHelper.selectMenu(menuId)
            tv_beverage.background.setTint(Color.parseColor("#F5EFD9"))

            // 스타벅스의 경우만 카드색 변경
            if(info.color != 4)
                tv_beverage.background.setTint(Color.parseColor(color[info.color]))

            // 음료 정보
            var textView = tv_beverage
            val name = info.menuName
            var menuInfo : String = ""

            menuInfo += info.menuName + "\n\n\n"
            menuInfo += info.desc + "\n\n\n"
            menuInfo += "1회 제공량 (kcal) : " + info.calorie + "\n"
            menuInfo += "포화지방 (g) : " + info.saturatedFat + "\n"
            menuInfo += "단백질 (g) : " + info.protein + "\n"
            menuInfo += "당류 (g) : " + info.sugars + "\n"
            menuInfo += "카페인 (mg) : " + info.caffeine

            // 프론트
            var start = 0
            var end = start + name.length

            val builder = SpannableStringBuilder(menuInfo)

            val sizeBigSpan = RelativeSizeSpan(1.3f)
            builder.setSpan(sizeBigSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val boldSpan = StyleSpan(Typeface.BOLD)
            builder.setSpan(boldSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            textView.text = builder

            // 스타벅스 진초록일 경우만 글씨색 변경
            if(info.color == 0)
                textView.setTextColor(Color.parseColor("#FFFFFF"))

            btn_info.setOnClickListener {
                setFragmentResult("requestKeyRandom3", bundleOf("bundleKeyRandom3" to isRandom))
                setFragmentResult("requestKey10", bundleOf("bundleKey10" to result, "bundleKey10_menuId1" to menuId1, "bundleKey10_menuId2" to menuId2, "bundleKey10_menuId3" to menuId3))
                //setFragmentResult("requestKey10", bundleOf("bundleKey10" to result))
                setFragmentResult("menuId_resultKey3", bundleOf("menuId_bundleKey3" to menuId))
                setFragmentResult("count_resultKey4", bundleOf("count_bundleKey4" to count))
                navController.navigate(R.id.action_beverageInfoFragment_to_infoFragment) }
        }
    }
}