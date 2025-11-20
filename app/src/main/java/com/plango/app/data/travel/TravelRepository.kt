package com.plango.app.data.travel

import android.util.Log
import com.plango.app.api.ApiProvider
import com.plango.app.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object TravelRepository {
    private val api: ApiService = ApiProvider.api

    // 여행 생성 후 상세정보 캐시
    private val _travelDetailFlow = MutableStateFlow<TravelDetailResponse?>(null)
    val travelDetailFlow: StateFlow<TravelDetailResponse?> = _travelDetailFlow

    // Intent로 받은 데이터를 설정하기 위한 함수
    fun setTravelDetail(detail: TravelDetailResponse) {
        _travelDetailFlow.value = detail
    }

    // 목록 캐시 (각각 별도로 관리)
    private val _upcomingTravelsFlow = MutableStateFlow<List<TravelSummaryResponse>>(emptyList())
    val upcomingTravelsFlow: StateFlow<List<TravelSummaryResponse>> = _upcomingTravelsFlow
    
    private val _ongoingTravelsFlow = MutableStateFlow<List<TravelSummaryResponse>>(emptyList())
    val ongoingTravelsFlow: StateFlow<List<TravelSummaryResponse>> = _ongoingTravelsFlow
    
    private val _finishedTravelsFlow = MutableStateFlow<List<TravelSummaryResponse>>(emptyList())
    val finishedTravelsFlow: StateFlow<List<TravelSummaryResponse>> = _finishedTravelsFlow
    
    // 하위 호환성을 위한 기존 flow (deprecated)
    @Deprecated("각각의 flow를 사용하세요")
    private val _travelListFlow = MutableStateFlow<List<TravelSummaryResponse>>(emptyList())
    val travelListFlow: StateFlow<List<TravelSummaryResponse>> = _travelListFlow


    // 여행 생성
    suspend fun createTravel(request: TravelCreateRequest) {
        try {
            val response = api.createTravel(request)
            Log.d("TravelRepository", "여행 생성 성공: $response")
            _travelDetailFlow.value = response
        } catch (e: Exception) {
            Log.e("TravelRepository", "여행 생성 실패: ${e.message}", e)
        }
    }


    //다가 올 여행
    suspend fun getUpcomingTravels(userPublicId: String) {
        try {
            val response = api.getUpcomingTravels(userPublicId)
            Log.d("TravelRepository", "다가올 여행 ${response.size}건 수신")
            _upcomingTravelsFlow.value = response
            _travelListFlow.value = response // 하위 호환성
        } catch (e: Exception) {
            Log.e("TravelRepository", "다가올 여행 불러오기 실패: ${e.message}", e)
        }
    }

    //지난여행
    suspend fun getFinishedTravels(userPublicId: String) {
        try {
            val response = api.getFinishedTravels(userPublicId)
            Log.d("TravelRepository", "지난 여행 ${response.size}건 수신")
            _finishedTravelsFlow.value = response
            _travelListFlow.value = response // 하위 호환성
        } catch (e: Exception) {
            Log.e("TravelRepository", "지난 여행 불러오기 실패: ${e.message}", e)
        }
    }

    //진행중인 여행
    suspend fun getOngoingTravels(userPublicId: String) {
        try {
            val response = api.getOngoingTravels(userPublicId)
            Log.d("TravelRepository", "진행 중 여행 ${response.size}건 수신")
            _ongoingTravelsFlow.value = response
            _travelListFlow.value = response // 하위 호환성
        } catch (e: Exception) {
            Log.e("TravelRepository", "진행 중 여행 불러오기 실패: ${e.message}", e)
        }
    }
    
    // 모든 여행 목록을 한 번에 로드
    suspend fun loadAllTravels(userPublicId: String) {
        try {
            coroutineScope {
                launch { getUpcomingTravels(userPublicId) }
                launch { getOngoingTravels(userPublicId) }
                launch { getFinishedTravels(userPublicId) }
            }
            Log.d("TravelRepository", "모든 여행 목록 로드 완료")
        } catch (e: Exception) {
            Log.e("TravelRepository", "모든 여행 목록 로드 실패: ${e.message}", e)
        }
    }

    //  특정 여행 상세정보 불러오기
    suspend fun getTravelDetail(travelId: Long) {
        try {
            val response = api.getTravelDetail(travelId)
            Log.d("TravelRepository", "여행 상세 조회 성공: $response")
            _travelDetailFlow.value = response
        } catch (e: Exception) {
            Log.e("TravelRepository", "여행 상세 조회 실패: ${e.message}", e)
        }
    }
}
