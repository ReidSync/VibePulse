//
//  ResourceDictionary.swift
//  iosApp
//
//  Created by Reid on 2023/12/28.
//

import Foundation


let FeelingEmojis = [
  Feelings.sad : "sad",
  Feelings.angry : "angry",
  Feelings.neutral : "neutral",
  Feelings.happy : "happy",
  Feelings.superhappy : "super_happy"
]

// (WeatherCode.)\b([A-Z])(\w+)([A-Z]) -> $1\l$2$3$4
let WeatherIcons = [
  WeatherCode.unknown : "weather",
  
  WeatherCode.clearSkyDay : "day",
  WeatherCode.clearSkyNight : "night",
  
  WeatherCode.mainlyClearDay : "cloudy_day_1",
  WeatherCode.partlyCloudyDay : "cloudy_day_2",
  WeatherCode.overcastDay : "cloudy_day_3",
  
  WeatherCode.mainlyClearNight : "cloudy_night_1",
  WeatherCode.partlyCloudyNight : "cloudy_night_2",
  WeatherCode.overcastNight : "cloudy_night_3",
  
  WeatherCode.fogAndDepositingRimeFogDay : "cloudy_day_3",
  WeatherCode.fogAndDepositingRimeFogNight : "cloudy_night_3",
  
  WeatherCode.drizzleLightDay : "rainy_1",
  WeatherCode.drizzleModerateDay : "rainy_2",
  WeatherCode.drizzleDenseDay : "rainy_3",
  WeatherCode.drizzleLightNight : "rainy_4",
  WeatherCode.drizzleModerateNight : "rainy_5",
  WeatherCode.drizzleDenseNight : "rainy_6",
  
  WeatherCode.freezingDrizzleLightDay : "snowy_1",
  WeatherCode.freezingDrizzleDenseDay : "snowy_3",
  WeatherCode.freezingDrizzleLightNight : "snowy_4",
  WeatherCode.freezingDrizzleDenseNight : "snowy_6",
  
  WeatherCode.rainSlightDay : "rainy_1",
  WeatherCode.rainModerateDay : "rainy_2",
  WeatherCode.rainHeavyDay : "rainy_3",
  WeatherCode.rainSlightNight : "rainy_4",
  WeatherCode.rainModerateNight : "rainy_5",
  WeatherCode.rainHeavyNight : "rainy_6",
  
  WeatherCode.freezingRainLightDay : "snowy_1",
  WeatherCode.freezingRainHeavyDay : "snowy_3",
  WeatherCode.freezingRainLightNight : "snowy_4",
  WeatherCode.freezingRainHeavyNight : "snowy_6",  
  
  WeatherCode.snowFallSlightDay : "snowy_1",
  WeatherCode.snowFallSlightNight : "snowy_1"
]
