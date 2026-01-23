package com.ezlevup.ganbyeong24.presentation.screens.admin.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.model.CareRequest
import com.ezlevup.ganbyeong24.data.model.Caregiver
import com.ezlevup.ganbyeong24.data.model.Match
import com.ezlevup.ganbyeong24.data.repository.CareRequestRepository
import com.ezlevup.ganbyeong24.data.repository.CaregiverRepository
import com.ezlevup.ganbyeong24.data.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 매칭 관리 ViewModel
 *
 * 간병 신청과 간병사를 매칭하는 3단계 프로세스를 관리합니다.
 *
 * @property matchRepository 매칭 Repository
 * @property careRequestRepository 간병 신청 Repository
 * @property caregiverRepository 간병사 Repository
 */
class MatchManagementViewModel(
        private val matchRepository: MatchRepository,
        private val careRequestRepository: CareRequestRepository,
        private val caregiverRepository: CaregiverRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MatchManagementState())
    val state: StateFlow<MatchManagementState> = _state.asStateFlow()

    init {
        loadData()
    }

    /** 데이터를 로드합니다. */
    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            // pending 상태 간병 신청 로드
            val requestsResult = careRequestRepository.getAllCareRequests()
            val pendingRequests =
                    requestsResult.getOrNull()?.filter { it.status == "pending" } ?: emptyList()

            // 간병사 로드
            val caregiversResult = caregiverRepository.getAllCaregivers()
            val caregivers = caregiversResult.getOrNull() ?: emptyList()

            _state.update {
                it.copy(
                        isLoading = false,
                        careRequests = pendingRequests,
                        caregivers = caregivers,
                        filteredCaregivers = caregivers
                )
            }
        }
    }

    /** 간병 신청을 선택합니다. */
    fun selectRequest(request: CareRequest) {
        _state.update {
            it.copy(
                    selectedRequest = request,
                    // 선택된 신청의 지역과 일치하는 간병사 우선 표시
                    filteredCaregivers = filterCaregiversByRegion(it.caregivers, request.location)
            )
        }
    }

    /** 간병사를 선택합니다. */
    fun selectCaregiver(caregiver: Caregiver) {
        _state.update { it.copy(selectedCaregiver = caregiver) }
    }

    /** 관리자 메모를 설정합니다. */
    fun setNotes(notes: String) {
        _state.update { it.copy(notes = notes) }
    }

    /** 다음 단계로 이동합니다. */
    fun goToNextStep() {
        val currentState = _state.value

        when (currentState.currentStep) {
            1 -> {
                if (currentState.selectedRequest == null) {
                    _state.update { it.copy(errorMessage = "간병 신청을 선택해주세요") }
                    return
                }
                _state.update { it.copy(currentStep = 2, errorMessage = null) }
            }
            2 -> {
                if (currentState.selectedCaregiver == null) {
                    _state.update { it.copy(errorMessage = "간병사를 선택해주세요") }
                    return
                }
                _state.update { it.copy(currentStep = 3, errorMessage = null) }
            }
        }
    }

    /** 이전 단계로 이동합니다. */
    fun goToPreviousStep() {
        val currentStep = _state.value.currentStep
        if (currentStep > 1) {
            _state.update { it.copy(currentStep = currentStep - 1, errorMessage = null) }
        }
    }

    /** 매칭을 생성합니다. */
    fun createMatch() {
        val currentState = _state.value
        val selectedRequest = currentState.selectedRequest
        val selectedCaregiver = currentState.selectedCaregiver

        if (selectedRequest == null || selectedCaregiver == null) {
            _state.update { it.copy(errorMessage = "선택 정보가 올바르지 않습니다") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            // 매칭 일련번호 생성
            val matchSerialNumber =
                    matchRepository.generateSerialNumber().getOrElse {
                        _state.update {
                            it.copy(
                                    isLoading = false,
                                    errorMessage = "매칭 일련번호 생성 실패: ${it.errorMessage}"
                            )
                        }
                        return@launch
                    }

            // 매칭 생성
            val match =
                    Match(
                            serialNumber = matchSerialNumber,
                            careRequestSerialNumber = selectedRequest.serialNumber,
                            caregiverSerialNumber = selectedCaregiver.serialNumber,
                            status = "pending",
                            notes = currentState.notes
                    )

            matchRepository
                    .createMatch(match)
                    .onSuccess { matchId ->
                        // 간병 신청 상태를 "matched"로 업데이트
                        careRequestRepository
                                .updateCareRequestStatus(selectedRequest.id, "matched")
                                .onSuccess {
                                    _state.update {
                                        it.copy(
                                                isLoading = false,
                                                isMatchCreated = true,
                                                createdMatchSerialNumber = matchSerialNumber
                                        )
                                    }
                                }
                                .onFailure { error ->
                                    _state.update {
                                        it.copy(
                                                isLoading = false,
                                                errorMessage = "상태 업데이트 실패: ${error.message}"
                                        )
                                    }
                                }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(isLoading = false, errorMessage = "매칭 생성 실패: ${error.message}")
                        }
                    }
        }
    }

    /** 지역별로 간병사를 필터링합니다. 일치하는 간병사를 우선 표시합니다. */
    private fun filterCaregiversByRegion(
            caregivers: List<Caregiver>,
            location: String
    ): List<Caregiver> {
        val matching = caregivers.filter { it.availableRegions.contains(location) }
        val others = caregivers.filter { !it.availableRegions.contains(location) }
        return matching + others
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }

    /** 처음부터 다시 시작합니다. */
    fun reset() {
        _state.update {
            MatchManagementState(
                    careRequests = it.careRequests,
                    caregivers = it.caregivers,
                    filteredCaregivers = it.caregivers
            )
        }
    }
}
