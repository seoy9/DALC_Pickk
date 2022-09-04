package dalc.hsy.pickk.fragment

import android.app.AlertDialog
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dalc.hsy.pickk.MainActivity
import dalc.hsy.pickk.R
import kotlinx.android.synthetic.main.fragment_beverage_info.*
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var mainActivity: MainActivity
    var menuId = 0
    var menuId1 = 0
    var menuId2 = 0
    var menuId3 = 0
    var result = "0"
    var isRandom = "null"
    var count = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setFragmentResultListener("menuId_resultKey3") { requestKey, bundle ->
            menuId = bundle.getInt("menuId_bundleKey3")
            Log.d("데이터 ", "(InfoFragment) menuID : $menuId")
        }
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // '질문지로 음료 찾기'로 왔을 경우 현재 응답 데이터
        setFragmentResultListener("requestKey10") { requestKey, bundle ->
            result = bundle.getString("bundleKey10").toString()
            menuId1 = bundle.getInt("bundleKey10_menuId1")
            menuId2 = bundle.getInt("bundleKey10_menuId2")
            menuId3 = bundle.getInt("bundleKey10_menuId3")
            Log.d("데이터", "(InfoFragment) 현재 응답 데이터 : ${result}, menuId1 : ${menuId1}")
        }

        //Recommend에서 음료가 선택 된 적이 있는 경우
        setFragmentResultListener("count_resultKey4") { requestKey, bundle ->
            count = bundle.getInt("count_bundleKey4")
            Log.d("데이터", "(BeverageInfoFragment) 현재 음료 번호 : ${count}")
        }

        // 기본 '뒤로가기 버튼'의 기능 (info > beverageInfo)
        btn_info_back.setOnClickListener {
            //setFragmentResult("requestKey9", bundleOf("bundleKey9" to result))
            setFragmentResult("requestKey9", bundleOf("bundleKey9" to result, "bundleKey9_menuId1" to menuId1, "bundleKey9_menuId2" to menuId2, "bundleKey9_menuId3" to menuId3))
            setFragmentResult("menuId_resultKey2", bundleOf("menuId_bundleKey2" to menuId))
            setFragmentResult("count_resultKey3", bundleOf("count_bundleKey3" to count))
            navController.navigate(R.id.action_infoFragment_to_beverageInfoFragment) }

        // '랜덤 버튼'으로 왔다면 random값 넘기기 (info > beverageInfo)
        setFragmentResultListener("requestKeyRandom3") { requestKey, bundle ->
            isRandom = bundle.getString("bundleKeyRandom3").toString()
            Log.d("데이터", "(InfoFragment) isRandom : ${isRandom}")

            if(isRandom.equals("random")) {
                btn_info_back.setOnClickListener {
                    setFragmentResult("requestKeyRandom2", bundleOf("bundleKeyRandom2" to isRandom))
                    setFragmentResult("requestKey9", bundleOf("bundleKey9" to result))
                    setFragmentResult("menuId_resultKey2", bundleOf("menuId_bundleKey2" to menuId))
                    navController.navigate(R.id.action_infoFragment_to_beverageInfoFragment)
                }
            }
        }

        btn_info_home.setOnClickListener { navController.navigate(R.id.action_infoFragment_to_mainFragment) }

        btn_email.setOnClickListener {
            // 메일 앱과 연결
            val email = Intent(Intent.ACTION_SEND)  // 메일 전송 설정
            email.setType("plain/text")   // 데이터 타입 설정
            email.setPackage("com.google.android.gm");  // GMail 바로 연동 ("com.google.android.gm" = GMail앱 패키지명)
            val address = arrayOf("pickk@gmail.com")   // 메일 수신 주소
            email.putExtra(Intent.EXTRA_EMAIL, address)   // 메일 수신 주소 목록
            email.putExtra(Intent.EXTRA_SUBJECT, "Pickk 문의 메일입니다.")  // 메일 제목 설정
            email.putExtra(Intent.EXTRA_TEXT, "문의 내용을 적어주세요.")   // 메일 본문 설정
            startActivity(Intent.createChooser(email, "메일 전송하기"))   // 메일 앱으로 전환
        }
    }
}