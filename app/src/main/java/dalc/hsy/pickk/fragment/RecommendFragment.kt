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
import dalc.hsy.pickk.RecommendMenuDBHelper
import dalc.hsy.pickk.menuDBHelper
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment : Fragment() {

    lateinit var navController: NavController
    var count = 1
    lateinit var dbHelper: menuDBHelper
    lateinit var recommdbHelper: RecommendMenuDBHelper
    lateinit var con: Context
    lateinit var info1 : Menu
    lateinit var info2 : Menu
    lateinit var info3 : Menu
    lateinit var info : Menu
    var menuId1 = 0
    var menuId2 = 0
    var menuId3= 0
    var result = "0"
    var color = arrayOf("#1B3C35", "#EDE7DB", "#F4EFDB", "#94CFCD") //0 : 초록, 1 : 하양, 2 : 노랑, 3 : 파랑

    var menuId = IntArray(3)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        con = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = menuDBHelper(con, "menu", null, 1)
        recommdbHelper = RecommendMenuDBHelper(con, "recommmenu", null, 1)
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey7") { requestKey, bundle ->
            result = bundle.getString("bundleKey7").toString()
            menuId1 = bundle.getInt("bundleKey7_menuId1")
            menuId2 = bundle.getInt("bundleKey7_menuId2")
            menuId3 = bundle.getInt("bundleKey7_menuId3")
            //var test = bundle.getString("test").toString() //지우기
            Log.d("데이터", "(RecommendFragment) 현재 응답 데이터 : ${result}")
            //Log.d("데이터", "(RecommendFragment) 2~test : ${test}") //지우기

            Log.d("데이터", "(RecommendFragment) 추천 음료 id : ${menuId1}  ${menuId2}  ${menuId3}")
            menuId[0] = menuId1
            menuId[1] = menuId2
            menuId[2] = menuId3
            info = dbHelper.selectMenu(menuId[0])
            info1 = dbHelper.selectMenu(menuId[0])
            info2 = dbHelper.selectMenu(menuId[1])
            info3 = dbHelper.selectMenu(menuId[2])
            navController = Navigation.findNavController(view)
            Log.d("데이터", "(RecommendFragment) menuID1 : ${menuId1}")
            //(cafeFragment > Recommend) count == 1
            //(Choice > Recommend : 뒤로가기로 온 경우) count == Recommend에서 선택한 음료 번호
            setFragmentResultListener("count_resultKey1") { requestKey, bundle ->
                count = bundle.getInt("count_bundleKey1")
                Log.d("데이터", "(RecommendFragment) 현재 음료 번호 : ${count}")

                info = dbHelper.selectMenu(menuId[count-1])
                val url = info.image
                Glide.with(this)
                    .load(url) // 불러올 이미지 url
                    .into(img_recommend_image)

                if(count == 2) {
                    img_recommend_page_control.setImageResource(R.drawable.recommend_position_2)
                } else if(count == 3) {
                    img_recommend_page_control.setImageResource(R.drawable.recommend_position_3)
                } else {
                    img_recommend_page_control.setImageResource(R.drawable.recommend_position_1)
                }

                //img_recommend_page_control.setImageResource(R.drawable.recommend_position_1)
                tv_recommend_name.text = info.menuName
                tv_recommend_cafe_kcal.text =
                    info.cafeName + " / " + info.calorie.toString() + "Kcal"

                btn_recommend_back.setOnClickListener {
                    result = result.substring(0, result.length-1)
                    Log.d("데이터", "(RecommendFragment) 뒤로가기로 보낼 응답 데이터 : ${result}")
                    setFragmentResult("requestKey6", bundleOf("bundleKey6" to result))
                    navController.navigate(R.id.action_recommendFragment_to_cafeFragment) }
                btn_recommend_home.setOnClickListener { navController.navigate(R.id.action_recommendFragment_to_mainFragment) }

                btn_recommend_beverage_back.setOnClickListener {
                    if (count == 1) {
                        count = 3
                    } else {
                        count = count - 1
                    }
                    when (count) {
                        1 -> {
                            val url = info1.image
                            Glide.with(this)
                                .load(url) // 불러올 이미지 url
                                .into(img_recommend_image)
                            img_recommend_page_control.setImageResource(R.drawable.recommend_position_1)
                            tv_recommend_name.text = info1.menuName
                            tv_recommend_cafe_kcal.text =
                                info1.cafeName + " / " + info1.calorie.toString() + "Kcal"
                        }
                        2 -> {
                            val url = info2.image
                            Glide.with(this)
                                .load(url) // 불러올 이미지 url
                                .into(img_recommend_image)
                            img_recommend_page_control.setImageResource(R.drawable.recommend_position_2)
                            tv_recommend_name.text = info2.menuName
                            tv_recommend_cafe_kcal.text =
                                info2.cafeName + " / " + info2.calorie.toString() + "Kcal"
                        }
                        3 -> {
                            val url = info3.image
                            Glide.with(this)
                                .load(url) // 불러올 이미지 url
                                .into(img_recommend_image)
                            img_recommend_page_control.setImageResource(R.drawable.recommend_position_3)
                            tv_recommend_name.text = info3.menuName
                            tv_recommend_cafe_kcal.text =
                                info3.cafeName + " / " + info3.calorie.toString() + "Kcal"
                        }
                    }
                }
                btn_recommend_beverage_next.setOnClickListener {
                    if (count == 3) {
                        count = 1
                    } else {
                        count = count + 1
                    }
                    when (count) {
                        1 -> {
                            val url = info1.image
                            Glide.with(this)
                                .load(url) // 불러올 이미지 url
                                .into(img_recommend_image)
                            img_recommend_page_control.setImageResource(R.drawable.recommend_position_1)
                            tv_recommend_name.text = info1.menuName
                            tv_recommend_cafe_kcal.text =
                                info1.cafeName + " / " + info1.calorie.toString() + "Kcal"
                        }
                        2 -> {
                            val url = info2.image
                            Glide.with(this)
                                .load(url) // 불러올 이미지 url
                                .into(img_recommend_image)
                            img_recommend_page_control.setImageResource(R.drawable.recommend_position_2)
                            tv_recommend_name.text = info2.menuName
                            tv_recommend_cafe_kcal.text =
                                info2.cafeName + " / " + info2.calorie.toString() + "Kcal"
                        }
                        3 -> {
                            val url = info3.image
                            Glide.with(this)
                                .load(url) // 불러올 이미지 url
                                .into(img_recommend_image)
                            img_recommend_page_control.setImageResource(R.drawable.recommend_position_3)
                            tv_recommend_name.text = info3.menuName
                            tv_recommend_cafe_kcal.text =
                                info3.cafeName + " / " + info3.calorie.toString() + "Kcal"
                        }
                    }
                }

                img_recommend_image.setOnClickListener {
                    //setFragmentResult("requestKey8", bundleOf("bundleKey8" to result, "test" to count)) //지우기
                    setFragmentResult("requestKey8", bundleOf("bundleKey8" to result, "bundleKey8_menuId1" to menuId1, "bundleKey8_menuId2" to menuId2, "bundleKey8_menuId3" to menuId3))

                    setFragmentResult("menuId_resultKey1", bundleOf("menuId_bundleKey1" to menuId[count-1]))
                    setFragmentResult("count_resultKey2", bundleOf("count_bundleKey2" to count))
                    navController.navigate(R.id.action_recommendFragment_to_choiceFragment)
                }
            }
        }
    }
}