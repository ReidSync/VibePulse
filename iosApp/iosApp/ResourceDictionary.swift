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

// (WeatherCodeInfo.)\b([A-Z])(\w+)([A-Z]) -> $1\l$2$3$4
let WeatherIcons = [
  WeatherCodeInfo.unknown : "weather",
  
  WeatherCodeInfo.clearSkyDay : "day",
  WeatherCodeInfo.clearSkyNight : "night",
  
  WeatherCodeInfo.mainlyClearDay : "cloudy_day_1",
  WeatherCodeInfo.partlyCloudyDay : "cloudy_day_2",
  WeatherCodeInfo.overcastDay : "cloudy_day_3",
  
  WeatherCodeInfo.mainlyClearNight : "cloudy_night_1",
  WeatherCodeInfo.partlyCloudyNight : "cloudy_night_2",
  WeatherCodeInfo.overcastNight : "cloudy_night_3",
  
  WeatherCodeInfo.fogAndDepositingRimeFogDay : "cloudy_day_3",
  WeatherCodeInfo.fogAndDepositingRimeFogNight : "cloudy_night_3",
  
  WeatherCodeInfo.drizzleLightDay : "rainy_1",
  WeatherCodeInfo.drizzleModerateDay : "rainy_2",
  WeatherCodeInfo.drizzleDenseDay : "rainy_3",
  WeatherCodeInfo.drizzleLightNight : "rainy_4",
  WeatherCodeInfo.drizzleModerateNight : "rainy_5",
  WeatherCodeInfo.drizzleDenseNight : "rainy_6",
  
  WeatherCodeInfo.freezingDrizzleLightDay : "snowy_1",
  WeatherCodeInfo.freezingDrizzleDenseDay : "snowy_3",
  WeatherCodeInfo.freezingDrizzleLightNight : "snowy_4",
  WeatherCodeInfo.freezingDrizzleDenseNight : "snowy_6",
  
  WeatherCodeInfo.rainSlightDay : "rainy_1",
  WeatherCodeInfo.rainModerateDay : "rainy_2",
  WeatherCodeInfo.rainHeavyDay : "rainy_3",
  WeatherCodeInfo.rainSlightNight : "rainy_4",
  WeatherCodeInfo.rainModerateNight : "rainy_5",
  WeatherCodeInfo.rainHeavyNight : "rainy_6",
  
  WeatherCodeInfo.freezingRainLightDay : "snowy_1",
  WeatherCodeInfo.freezingRainHeavyDay : "snowy_3",
  WeatherCodeInfo.freezingRainLightNight : "snowy_4",
  WeatherCodeInfo.freezingRainHeavyNight : "snowy_6",
  
  WeatherCodeInfo.snowFallSlightDay : "snowy_1",
  WeatherCodeInfo.snowFallSlightNight : "snowy_1"
]
