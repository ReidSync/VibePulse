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
    @BindingState var contents: String = ""
    
    init(journal: Journal) {
      self.journal = journal
      self.contents = journal.contents
    }
  }
  
  enum Action {
    case done
    case updateContents(String)
    case view(Action.ViewAction)
    
    public enum ViewAction: BindableAction {
      case binding(BindingAction<State>)
    }
    
  }
  
  var body: some Reducer<State, Action> {
    BindingReducer(action: \.view)
    
    Reduce { state, action in
      switch action {
      case .done:
        return .none
      case .view(.binding):
        state.journal.contents = state.contents
        return .none
      case .updateContents(let text):
        state.journal.contents = text
        return .none
      case .delegate:
        return .none
      }
    }
    }
  }
}
