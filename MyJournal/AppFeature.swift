//
//  AppFeature.swift
//  MyJournal
//
//  Created by Reid on 2023/11/22.
//

import Foundation
import ComposableArchitecture


@Reducer
struct AppFeature {
  struct State: Equatable {
    var journalHome = JournalHome.State()
  }
  
  enum Action {
    case journalHome(JournalHome.Action)
  }
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
      case .journalHome:
        return .none
      }
    }
    
    Scope(state: \.journalHome, action: \.journalHome) {
      JournalHome()
    }
  }
}
