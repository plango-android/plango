package com.plango.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.plango.app.data.user.UserPrefs
import com.plango.app.databinding.FragmentHomeStep3Binding
import com.plango.app.ui.main.MainPageActivity
import com.plango.app.viewmodel.TravelViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeStep3 : Fragment() {
    private var _binding: FragmentHomeStep3Binding? = null
    private val binding get() = _binding!!
    
    private val travelViewModel: TravelViewModel by activityViewModels()
    private lateinit var adapter: TravelSummaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TravelSummaryAdapter { travel ->
            // 여행 클릭 시 상세 화면으로 이동
            lifecycleScope.launch {
                travelViewModel.getTravelDetail(travel.travelId)
                // 한 번만 응답을 받기 위해 first() 사용
                val detail = travelViewModel.travelDetailFlow.value
                if (detail != null && detail.travelId == travel.travelId) {
                    val intent = Intent(requireContext(), MainPageActivity::class.java).apply {
                        putExtra("travelDetail", detail)
                    }
                    startActivity(intent)
                } else {
                    // 응답 대기
                    travelViewModel.travelDetailFlow.collectLatest { detail ->
                        if (detail != null && detail.travelId == travel.travelId) {
                            val intent = Intent(requireContext(), MainPageActivity::class.java).apply {
                                putExtra("travelDetail", detail)
                            }
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // 데이터 관찰
        lifecycleScope.launch {
            travelViewModel.travelListFlow.collectLatest { travels ->
                adapter.submitList(travels)
                Log.d("HomeStep3", "지난 여행 ${travels.size}건 표시")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Fragment가 실제로 보일 때만 데이터 로드
        if (isVisible && isResumed) {
            loadData()
        }
    }
    
    fun loadData() {
        if (!isAdded) return
        lifecycleScope.launch {
            val userId = UserPrefs.getUserIdOnce(requireContext())
            if (!userId.isNullOrEmpty()) {
                Log.d("HomeStep3", "데이터 로드 시작: userId=$userId")
                travelViewModel.getFinishedTravels(userId)
            } else {
                Log.w("HomeStep3", "userId가 null이거나 비어있음")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}