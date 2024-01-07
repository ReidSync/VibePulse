package com.reidsync.vibepulse.android.ui.meta

import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.reidsync.vibepulse.android.VibePulseApplication
import com.reidsync.vibepulse.android.data.repositories.LocationRepository
import com.reidsync.vibepulse.android.data.repositories.NotebookRepository
import com.reidsync.vibepulse.notebook.journal.Feelings
import com.reidsync.vibepulse.notebook.journal.Journal
import com.reidsync.vibepulse.notebook.journal.JournalLocation
import com.reidsync.vibepulse.notebook.journal.MoodFactors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Reid on 2023/12/13.
 * Copyright (c) 2023 Reid Byun. All rights reserved.
 */

class JournalMetaViewModel(
	val journal: Journal,
	val type: JournalMetaViewType,
	private val notebookRepository: NotebookRepository,
	private val locationRepository: LocationRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(JournalMetaUIState())
	val uiState: StateFlow<JournalMetaUIState> = _uiState.asStateFlow()
	val dismiss: String = "Dismiss"
	val submit: String
		get() = when (type) {
			is JournalMetaViewType.Add -> "Add"
			is JournalMetaViewType.Edit -> "Done"
		}
	val title: String
		get() = when (type) {
			is JournalMetaViewType.Add -> "New Journal"
			is JournalMetaViewType.Edit -> "Edit my journal"
		}

	val feelings = Feelings.entries.toTypedArray()
	val moodFactors = MoodFactors.entries.toTypedArray()

	companion object {
		fun Factory(
			journal: Journal,
			type: JournalMetaViewType
		): ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val application = (this[APPLICATION_KEY] as VibePulseApplication)
				JournalMetaViewModel(
					journal = journal,
					type = type,
					notebookRepository = application.container.notebookRepository,
					locationRepository = application.container.locationRepository
				)
			}
		}
	}

	init {
		_uiState.update {
			it.copy(journal = journal)
		}
	}

	fun submitAction() {
		when (type) {
			is JournalMetaViewType.Add -> {
				viewModelScope.launch {
					notebookRepository.add(uiState.value.journal)
				}
			}

			is JournalMetaViewType.Edit -> {
				viewModelScope.launch {
					notebookRepository.update(uiState.value.journal)
				}
			}
		}
	}

	fun updateScreen(journal: Journal) {
		_uiState.update {
			it.copy(journal = journal)
		}
	}

	fun updateTitle(title: String) {
		updateScreen(_uiState.value.journal.copy(title = title))
	}

	fun updateFeeling(feeling: Feelings) {
		updateScreen(_uiState.value.journal.copy(feeling = feeling))
	}

	fun updateMoodFactors(moodFactors: MoodFactors, selected: Boolean) {
		val moods = _uiState.value.journal.moodFactors.toMutableSet()
		if (selected) {
			moods.add(moodFactors)
		}
		else {
			moods.remove(moodFactors)
		}
		updateScreen(_uiState.value.journal.copy(moodFactors = moods))
	}

	fun updateLocation(latitude: Double, longitude: Double) {
		viewModelScope.launch {
			val cityName = getCityName(latitude, longitude)
			val location = JournalLocation(latitude, longitude, cityName)
			updateScreen(_uiState.value.journal.copy(
				location = location
			))
		}
	}

	fun locationPermissions(on: Boolean) {
		_uiState.update {
			it.copy(requestLocationPermissions = on)
		}
	}

	private suspend fun getCityName(latitude: Double, longitude: Double): String {
		val location = locationRepository.getAddress(latitude, longitude).getOrNull()
		return location ?: "None"

//		if (Build.VERSION.SDK_INT < 33) { // SDK 버전이 33보다 큰 경우에만 아래 함수를 씁니다.
//			val addresses = geocoder.getFromLocation(latitude, longitude, 1)
//			return if (addresses.isNotEmpty()) {
//				addresses[0].locality ?: "Unknown City"
//			} else {
//				"Unknown City"
//			}
//		}else { // SDK 버전이 33보다 크거나 같은 경우
//			val geocoder = Geocoder(this, Locale.getDefault())
//			var address: Address? = null
//			val geocodeListener = @RequiresApi(33) object : Geocoder.GeocodeListener {
//				override fun onGeocode(addresses: MutableList<Address>) {
//					// 주소 리스트를 가지고 할 것을 적어주면 됩니다.
//					address =  addresses[0];
//					address?.let {
//						binding.tvLocationTitle.text = "${it.thoroughfare}" // 예시: 역삼 1동
//						binding.tvLocationSubtitle.text =
//							"${it.countryName} ${it.adminArea}" // 예시 : 대한민국 서울특별시
//					}
//				}
//				override fun onError(errorMessage: String?) {
//					address = null
//					Toast.makeText(this@MainActivity, "주소가 발견되지 않았습니다.", Toast.LENGTH_LONG).show()
//				}
//			}
//			geocoder.getFromLocation(latitude, longitude, 7, geocodeListener)
//		}
	}

}

data class JournalMetaUIState(
	val journal: Journal = Journal(),
	val requestLocationPermissions: Boolean = false
) {
	val title = journal.title
}

sealed class JournalMetaViewType {
	data object Add : JournalMetaViewType()
	data object Edit : JournalMetaViewType()

}