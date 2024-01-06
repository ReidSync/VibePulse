//
//  JournalMeta.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import Foundation
import ComposableArchitecture
import CoreLocation

@Reducer
struct JournalMeta {
  struct State: Equatable {
    var journal: Journal
    @BindingState var focus: Field? = nil
    @BindingState var title: String
    var feeling: Feelings
    var moodFactors: Set<MoodFactors>
    var titlePlaceHolder: String = ""
    var weather: String = ""
    @BindingState var keyboardPadding: CGFloat = 0
    
    
    init(journal: Journal) {
      self.journal = journal
      self.title = journal.title
      self.titlePlaceHolder = journal.titleWithPlaceHolder
      self.feeling = journal.feeling
      self.moodFactors = journal.moodFactors
    }
    
    enum Field: Hashable {
      case title
    }
  }
  
  enum Action: BindableAction {
    case binding(BindingAction<State>)
    case updateJournal(Journal)
    case setFeeling(Feelings)
    case setMoodFactor(moodFactor: MoodFactors, selected: Bool)
    case getWeatherToday
    case getWeatherTodayFinish(Result<CLLocationCoordinate2D, Error>)
    case task
  }
  
  @Dependency(\.keyboardResponder) var keyboardResponder
  @Dependency(\.locationClient) var locationClient
  
  var body: some Reducer<State, Action> {
    BindingReducer()
    Reduce { state, action in
      switch action {
      case .task:
        return .run { send in
          await try withThrowingTaskGroup(of: Void.self) { group in
            group.addTask {
              for await _ in await self.keyboardResponder.willShow() {
                await send(.binding(.set(\.$keyboardPadding, keyboardResponder.height())))
              }
            }
            
            group.addTask {
              for await _ in await self.keyboardResponder.willHide() {
                await send(.binding(.set(\.$keyboardPadding, 0)))
              }
            }
            
            for try await _ in group {}
          }
        }
      case .binding(\.$title):
        state.journal = state.journal.copy(title: state.title)
        //return .send(.updateJournal(state.journal.copy(title: state.title)))
        return .none
      case .binding(\.$keyboardPadding):
        return .none
      case .updateJournal(let journal):
        state.focus = nil
        state.journal = journal
        return .none
      case .setFeeling(let feeling):
        state.feeling = feeling
        return .send(.updateJournal(state.journal.copy(feeling: state.feeling)))
      case let .setMoodFactor(moodFactor, selected):
        if selected {
          state.moodFactors.insert(moodFactor)
        }
        else {
          state.moodFactors.remove(moodFactor)
        }
        return .send(.updateJournal(state.journal.copy(moodFactors: state.moodFactors)))
      case .binding:
        return .none
      case .getWeatherTodayFinish(let result):
        switch result {
        case .success(let location):                    
          state.weather = String(format: "%.2f, %.2f", location.latitude, location.longitude)
          return .none
        case .failure(let error):
          print(error)
          return .none
        }
      case .getWeatherToday:
        return .run { send in
          async let getWeather: Void = send(
            .getWeatherTodayFinish (
              Result { try await locationClient.getCurrentLocation() }
            )
          )
          await getWeather
        }
      }
    }
  }
}
