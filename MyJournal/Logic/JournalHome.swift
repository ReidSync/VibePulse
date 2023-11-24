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
    var journals: IdentifiedArrayOf<Journal> = []
  }
  
  enum Action {
    case createButtonTapped
  }
  
  @Dependency(\.uuid) var uuid
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
      case .createButtonTapped:
        state.journals.append(
          Journal(id: self.uuid(), title: "Title...", date: Date(), contents: "test contents...!!!")
        )
        return .none
      }
    }
  } 
  
}

