//
//  WeatherInfoClient.swift
//  iosApp
//
//  Created by Reid on 2024/01/08.
//

import Foundation
import Dependencies

struct WeatherInfoClient: Sendable {
  var getWeatherInfo: @Sendable (Double, Double) async -> Result<JournalWeather, WeatherInfoServiceException>
}

extension WeatherInfoClient: DependencyKey {
  static var liveValue: Self {
    let weatherInfoService = WeatherInfoService()
    
    return Self(
      // I had to make `getWeatherInfo` run in MainActor
      // because suspend functions should be called in Main thread...
      // I think this is a big ristriction and a problem.
      // -----------
      //getWeatherInfo: { @MainActor latitude, longitude in
      
      // I resoloved a restriction by adding `Addingkotlin.native.binary.objcExportSuspendFunctionLaunchThreadRestriction=none`
      // to gradle.properties.
      // But it still causes a slight UI pausing.
      getWeatherInfo: { latitude, longitude in
        do {
          let result = try await weatherInfoService.getWeatherResponseOrThrow(latitude: latitude, longitude: longitude).asJournalWeather()
          
          return .success(result)
        }
        catch {
          guard let error = error as? WeatherInfoServiceException else {
            return .failure(WeatherInfoServiceException(message: "Unknow error"))
          }
          return .failure(error)
        }
        
        //try await weatherInfoService.getWeatherResponse(latitude: latitude, longitude: longitude)
//        weatherInfoService.getWeatherResponse(latitude: latitude, longitude: longitude) {r,e in
//          print(r)
//          print(e)
//        }
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
