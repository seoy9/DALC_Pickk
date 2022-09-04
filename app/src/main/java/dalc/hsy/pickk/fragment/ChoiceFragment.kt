package dalc.hsy.pickk.fragment

import android.content.Context
import android.graphics.Color
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
import com.bumptech.glide.Glide
import dalc.hsy.pickk.Menu
import dalc.hsy.pickk.R
import dalc.hsy.pickk.menuDBHelper
import kotlinx.android.synthetic.main.fragment_choice.*
import kotlinx.android.synthetic.main.fragment_recommend.*

class ChoiceFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var dbHelper: menuDBHelper
    lateinit var con: Context
    lateinit var info : Menu
    var menuId = 0
    var color = arrayOf("#1B3C35", "#EDE7DB", "#F4EFDB", "#94CFCD") //0 : 초록, 1 : 하양, 2 : 노랑, 3 : 파랑
    var isRandom = "null"
    var result = "0"
    var count = -1
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
        info = dbHelper.selectMenu(menuId)
        return inflater.inflate(R.layout.fragment_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // '질문지로 음료 찾기'로 왔을 경우 현재 응답 데이터
        setFragmentResultListener("requestKey8") { requestKey, bundle ->
            result = bundle.getString("bundleKey8").toString()
          //  /*
            menuId1 = bundle.getInt("bundleKey8_menuId1")
            menuId2 = bundle.getInt("bundleKey8_menuId2")
            menuId3 = bundle.getInt("bundleKey8_menuId3")
            // */
            //Log.d("데이터", "(ChoiceFragment) 현재 응답 데이터 : ${result}")//변경
            Log.d("데이터", "(ChoiceFragment) 현재 응답 데이터 : ${result}, 추천 음료 1 ${menuId1}")
        }

        //Recommend에서 음료가 선택 된 적이 있는 경우
        setFragmentResultListener("count_resultKey2") { requestKey, bundle ->
            count = bundle.getInt("count_bundleKey2")
            Log.d("데이터", "(ChoiceFragment) 현재 음료 번호 : ${count}")
        }

        // 기본 '뒤로가기 버튼'의 기능 (choice > recommend)
        btn_choice_back.setOnClickListener {
            //setFragmentResult("requestKey7", bundleOf("bundleKey7" to result)) //지우기
            setFragmentResult("requestKey7", bundleOf("bundleKey7" to result, "bundleKey7_menuId1" to menuId1, "bundleKey7_menuId2" to menuId2, "bundleKey7_menuId3" to menuId3))
            setFragmentResult("count_resultKey1", bundleOf("count_bundleKey1" to count))
            navController.navigate(R.id.action_choiceFragment_to_recommendFragment)
        }

        // '랜덤 버튼'으로 왔다면 '뒤로가기 버튼'의 기능 변경 (choice > way)
        setFragmentResultListener("requestKeyRandom") { requestKey, bundle ->
            isRandom = bundle.getString("bundleKeyRandom").toString()
            Log.d("데이터", "(ChoiceFragment) isRandom : ${isRandom}")

            if(isRandom.equals("random")) {
                btn_choice_back.setOnClickListener { navController.navigate(R.id.action_choiceFragment_to_wayFragment) }
            }
        }

        btn_choice_home.setOnClickListener { navController.navigate(R.id.action_choiceFragment_to_mainFragment) }

        setFragmentResultListener("menuId_resultKey1") { requestKey, bundle ->
            menuId = bundle.getInt("menuId_bundleKey1")
            Log.d("데이터", "(ChoiceFragment) menuID : $menuId")
            info = dbHelper.selectMenu(menuId)

            // 스타벅스 진초록일 경우만 로고색 변경
            if(info.color == 0)
                img_choice_logo.setColorFilter(Color.parseColor("#FFFFFF"))

            // 스타벅스의 경우만 카드색 변경
            if(info.color != 4)
                img_choice_card.setColorFilter(Color.parseColor(color[info.color]))

            val url = info.image
            Glide.with(this)
                .load(url)
                .into(img_choice_beverage)

            // 음료 정보
            tv_choice_name.text = info.menuName
            tv_choice_cafe_kcal.text = info.cafeName + " / " + info.calorie.toString() + "Kcal"

            img_choice_card.setOnClickListener {
                setFragmentResult("requestKeyRandom2", bundleOf("bundleKeyRandom2" to isRandom))
            //    setFragmentResult("requestKey9", bundleOf("bundleKey9" to result)) //지우기
                //변경
                setFragmentResult("requestKey9", bundleOf("bundleKey9" to result, "bundleKey9_menuId1" to menuId1, "bundleKey9_menuId2" to menuId2, "bundleKey9_menuId3" to menuId3))
                setFragmentResult("menuId_resultKey2", bundleOf("menuId_bundleKey2" to menuId))
                setFragmentResult("count_resultKey3", bundleOf("count_bundleKey3" to count))
                navController.navigate(R.id.action_choiceFragment_to_beverageInfoFragment) }
            img_choice_logo.setOnClickListener {
                setFragmentResult("requestKeyRandom2", bundleOf("bundleKeyRandom2" to isRandom))
                //setFragmentResult("requestKey9", bundleOf("bundleKey9" to result))//지우기
                //변경
                setFragmentResult("requestKey9", bundleOf("bundleKey9" to result, "bundleKey9_menuId1" to menuId1, "bundleKey9_menuId2" to menuId2, "bundleKey9_menuId3" to menuId3))
                setFragmentResult("menuId_resultKey2", bundleOf("menuId_bundleKey2" to menuId))
                setFragmentResult("count_resultKey3", bundleOf("count_bundleKey3" to count))
                navController.navigate(R.id.action_choiceFragment_to_beverageInfoFragment) }
            img_choice_beverage.setOnClickListener {
                setFragmentResult("requestKeyRandom2", bundleOf("bundleKeyRandom2" to isRandom))
                //setFragmentResult("requestKey9", bundleOf("bundleKey9" to result))//지우기
                //변경
                setFragmentResult("requestKey9", bundleOf("bundleKey9" to result, "bundleKey9_menuId1" to menuId1, "bundleKey9_menuId2" to menuId2, "bundleKey9_menuId3" to menuId3))
                setFragmentResult("menuId_resultKey2", bundleOf("menuId_bundleKey2" to menuId))
                setFragmentResult("count_resultKey3", bundleOf("count_bundleKey3" to count))
                navController.navigate(R.id.action_choiceFragment_to_beverageInfoFragment) }
        }
    }
}