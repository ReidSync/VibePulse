//
//  JournalHome.swift
//  MyJournal
//
//  Created by Reid on 2023/11/23.
//

import Foundation
import ComposableArchitecture

@Reducer
struct JournalHome {
  struct State: Equatable {
    
  }
  
  enum Action {
    case createButtonTapped
  }
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
      case .createButtonTapped:
        return .none
      }
    }
  }
  
  
}

