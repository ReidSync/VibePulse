//
//  JournalMeta.swift
//  MyJournal
//
//  Created by Reid on 2023/11/24.
//

import Foundation

import ComposableArchitecture

@Reducer
struct JournalMeta {
  struct State: Equatable {
    var journal: Journal
    @BindingState var focus: Field? = .title
    @BindingState var title: String
    
    init(journal: Journal) {
      self.journal = journal
      self.title = journal.title
    }
    
    enum Field: Hashable {
      case title
    }
  }
  
  enum Action: BindableAction {
    case binding(BindingAction<State>)
  }
  
  var body: some Reducer<State, Action> {
    BindingReducer()
    Reduce { state, action in
      switch action {
      case .binding(\.$title):
        state.journal = state.journal.copy(title: state.title)
        return .none
      case .binding:
        return .none
      }
    }
  }
}

