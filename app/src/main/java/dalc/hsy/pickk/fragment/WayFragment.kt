package dalc.hsy.pickk.fragment

import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_taste.*
import kotlinx.android.synthetic.main.fragment_way.*
import java.security.SecureRandom

class WayFragment : Fragment() {

    lateinit var navController: NavController
    var isRandom = "null"

    // 기존 random은 규칙성을 가지고 값을 뽑음 (매번 1회, 2회, 3회, ... 등의 번호가 같음)
    // secureRandom은 예측 불가능한 랜덤 숫자 생성
    val secureRandom = SecureRandom()
    val random = secureRandom.nextInt(377)

    var randomId = random.toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_way, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        btn_way_back.setOnClickListener { navController.navigate(R.id.action_wayFragment_to_mainFragment) }
        btn_way_home.setOnClickListener { navController.navigate(R.id.action_wayFragment_to_mainFragment) }

        btn_random.setOnClickListener {
            isRandom="random"
            setFragmentResult("requestKeyRandom", bundleOf("bundleKeyRandom" to isRandom))
            setFragmentResult("menuId_resultKey1", bundleOf("menuId_bundleKey1" to randomId))
            navController.navigate(R.id.action_wayFragment_to_choiceFragment)
        }
        btn_question.setOnClickListener { navController.navigate(R.id.action_wayFragment_to_hotIceFragment) }
    }
}