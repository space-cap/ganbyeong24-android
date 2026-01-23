package com.ezlevup.ganbyeong24.presentation.screens.admin.caregiver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.repository.CaregiverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 관리자용 간병사 목록 ViewModel
 *
 * 모든 간병사를 조회하고 지역/자격증/경력별 필터링을 제공합니다.
 *
 * @property caregiverRepository 간병사 Repository
 */
class AdminCaregiverListViewModel(private val caregiverRepository: CaregiverRepository) :
        ViewModel() {

    private val _state = MutableStateFlow(AdminCaregiverListState())
    val state: StateFlow<AdminCaregiverListState> = _state.asStateFlow()

    init {
        loadAllCaregivers()
    }

    /** 모든 간병사 목록을 조회합니다. */
    fun loadAllCaregivers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            caregiverRepository
                    .getAllCaregivers()
                    .onSuccess { caregivers ->
                        _state.update {
                            it.copy(
                                    isLoading = false,
                                    caregivers = caregivers,
                                    filteredCaregivers =
                                            filterCaregivers(
                                                    caregivers,
                                                    it.selectedRegion,
                                                    it.selectedCertificate,
                                                    it.selectedExperience
                                            )
                            )
                        }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                    isLoading = false,
                                    errorMessage = error.message ?: "목록을 불러오는데 실패했습니다"
                            )
                        }
                    }
        }
    }

    /** 지역 필터를 설정합니다. */
    fun setRegionFilter(region: String?) {
        _state.update {
            it.copy(
                    selectedRegion = region,
                    filteredCaregivers =
                            filterCaregivers(
                                    it.caregivers,
                                    region,
                                    it.selectedCertificate,
                                    it.selectedExperience
                            )
            )
        }
    }

    /** 자격증 필터를 설정합니다. */
    fun setCertificateFilter(certificate: String?) {
        _state.update {
            it.copy(
                    selectedCertificate = certificate,
                    filteredCaregivers =
                            filterCaregivers(
                                    it.caregivers,
                                    it.selectedRegion,
                                    certificate,
                                    it.selectedExperience
                            )
            )
        }
    }

    /** 경력 필터를 설정합니다. */
    fun setExperienceFilter(experience: String?) {
        _state.update {
            it.copy(
                    selectedExperience = experience,
                    filteredCaregivers =
                            filterCaregivers(
                                    it.caregivers,
                                    it.selectedRegion,
                                    it.selectedCertificate,
                                    experience
                            )
            )
        }
    }

    /** 모든 필터를 초기화합니다. */
    fun clearFilters() {
        _state.update {
            it.copy(
                    selectedRegion = null,
                    selectedCertificate = null,
                    selectedExperience = null,
                    filteredCaregivers = it.caregivers
            )
        }
    }

    /** 다중 조건으로 간병사를 필터링합니다. */
    private fun filterCaregivers(
            caregivers: List<com.ezlevup.ganbyeong24.data.model.Caregiver>,
            region: String?,
            certificate: String?,
            experience: String?
    ): List<com.ezlevup.ganbyeong24.data.model.Caregiver> {
        var filtered = caregivers

        // 지역 필터
        if (region != null) {
            filtered = filtered.filter { it.availableRegions.contains(region) }
        }

        // 자격증 필터
        if (certificate != null) {
            filtered = filtered.filter { it.certificates.contains(certificate) }
        }

        // 경력 필터
        if (experience != null) {
            filtered = filtered.filter { it.experience == experience }
        }

        return filtered
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
