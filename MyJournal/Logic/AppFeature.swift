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
