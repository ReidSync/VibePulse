//
//  Journal+Extensions.swift
//  iosApp
//
//  Created by Reid on 2023/12/19.
//

import Foundation

extension Journal: Identifiable {}

extension Journal {
  func copy(
    id: UUID? = nil,
    //date: Date? = nil, // Kotlinx.datetimeLocalDateTime
    title: String? = nil,
    feeling: Feelings? = nil,
    moodFactors: Set<MoodFactors>? = nil,
    location: JournalLocation? = nil,
    weather: JournalWeather? = nil,
    contents: String? = nil
  )-> Journal {    
    return doCopy(
      id: id ?? self.id,
      date: self.date,
      title: title ?? self.title,
      feeling: feeling ?? self.feeling,
      moodFactors: moodFactors ?? self.moodFactors,
      location: location ?? self.location,
      weather: weather ?? self.weather,
      contents: contents ?? self.contents)
  }
}
