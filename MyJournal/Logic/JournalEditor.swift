//
//  JournalEditor.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import Foundation
import ComposableArchitecture

@Reducer
struct JournalEditor {
  struct State: Equatable {
    var journal: Journal
  }
  
  enum Action {
    case done
  }
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
      case .done:
        return .none
      }      
    }
  }
}
