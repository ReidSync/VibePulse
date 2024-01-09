//
//  WeatherInfoClient.swift
//  iosApp
//
//  Created by Reid on 2024/01/08.
//

import Foundation
import Dependencies

struct WeatherInfoClient: Sendable {
  var getWeatherInfo: @Sendable (Double, Double) async throws -> JournalWeather
}

extension WeatherInfoClient: DependencyKey {
  static var liveValue: Self {
    let weatherInfoService = WeatherInfoService()
    return Self(
      // I had to make `getWeatherInfo` run in MainActor
      // because suspend functions should be called in Main thread...
      // I think this is a big ristriction and a problem.
      getWeatherInfo: { @MainActor latitude, longitude in
        try await weatherInfoService.getWeatherResponse(latitude: latitude, longitude: longitude).asJournalWeather()
      }
    )
  }
  
  static let testValue = Self(
    getWeatherInfo: unimplemented("WeatherInfoClient.getWeatherInfo")
  )
}

extension DependencyValues {
  var weatherInfoClient: WeatherInfoClient {
    get { self[WeatherInfoClient.self] }
    set { self[WeatherInfoClient.self] = newValue }
  }
}
