package com.reidsync.vibepulse.android.ui.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.reidsync.vibepulse.android.VibePulseApplication
import com.reidsync.vibepulse.android.data.repositories.LocationRepository
import com.reidsync.vibepulse.android.data.repositories.NotebookRepository
import com.reidsync.vibepulse.network.weather.WeatherInfoService
import com.reidsync.vibepulse.notebook.journal.Feelings
import com.reidsync.vibepulse.notebook.journal.GettingWeatherState
import com.reidsync.vibepulse.notebook.journal.Journal
import com.reidsync.vibepulse.notebook.journal.JournalLocation
import com.reidsync.vibepulse.notebook.journal.JournalMetaViewType
import com.reidsync.vibepulse.notebook.journal.MoodFactors
import com.reidsync.vibepulse.notebook.journal.asJournalWeather
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
			JournalMetaViewType.Add -> "Add"
			JournalMetaViewType.Edit -> "Done"
		}
	val title: String
		get() = when (type) {
			JournalMetaViewType.Add -> "New Journal"
			JournalMetaViewType.Edit -> "Edit my journal"
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
			JournalMetaViewType.Add -> {
				viewModelScope.launch {
					notebookRepository.add(uiState.value.journal)
				}
			}

			JournalMetaViewType.Edit -> {
				viewModelScope.launch {
					notebookRepository.update(uiState.value.journal)
				}
			}
		}
	}

	private fun updateJournal(journal: Journal) {
		_uiState.update {
			it.copy(journal = journal)
		}
	}

	fun updateTitle(title: String) {
		updateJournal(_uiState.value.journal.copy(title = title))
	}

	fun updateFeeling(feeling: Feelings) {
		updateJournal(_uiState.value.journal.copy(feeling = feeling))
	}

	fun updateMoodFactors(moodFactors: MoodFactors, selected: Boolean) {
		val moods = _uiState.value.journal.moodFactors.toMutableSet()
		if (selected) {
			moods.add(moodFactors)
		}
		else {
			moods.remove(moodFactors)
		}
		updateJournal(_uiState.value.journal.copy(moodFactors = moods))
	}

	fun updateLocationAndWeather(latitude: Double, longitude: Double) {
		viewModelScope.launch {
			val cityName = getCityName(latitude, longitude)
			val location = JournalLocation(latitude, longitude, cityName)
			 WeatherInfoService().getWeatherResponse(latitude, longitude)
				.onSuccess { weatherResponse ->
					val weather = weatherResponse.asJournalWeather()
					updateJournal(_uiState.value.journal.copy(
						location = location,
						weather = weather
					))
					_uiState.update {
						it.copy(
							requestLocationPermissions = false
						)
					}
					updateWeatherState(GettingWeatherState.Success(weather))
				}
				.onFailure { error ->
					updateJournal(_uiState.value.journal.copy(
						location = location
					))
					_uiState.update {
						it.copy(
							requestLocationPermissions = false
						)
					}
					updateWeatherState(GettingWeatherState.Error(error))
				}
		}
	}

	fun updateWeatherState(weatherState: GettingWeatherState) {
		_uiState.update {
			it.copy(
				weatherState = weatherState
			)
		}
	}

	fun locationPermissions(on: Boolean) {
		_uiState.update {
			it.copy(
				requestLocationPermissions = on,
				weatherState = if (on) GettingWeatherState.Loading else it.weatherState
			)
		}
	}

	private suspend fun getCityName(latitude: Double, longitude: Double): String {
		val location = locationRepository.getAddress(latitude, longitude).getOrNull()
		return location ?: "None"
	}

}

data class JournalMetaUIState(
	val journal: Journal = Journal(),
	val requestLocationPermissions: Boolean = false,
	val weatherState: GettingWeatherState = GettingWeatherState.Done
) {
	val title = journal.title
}