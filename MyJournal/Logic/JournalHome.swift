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
    @PresentationState var destination: Destination.State?
    var journals: IdentifiedArrayOf<Journal> = []
  }
  
  enum Action {
    case createButtonTapped
    case destination(PresentationAction<Destination.Action>)
    case addNewJournal
    case dismissAddingNewJournal
  }
  
  @Dependency(\.uuid) var uuid
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
      case .createButtonTapped:
        state.destination = .sheetToAdd(
          JournalMeta.State(
          journal: Journal(
            id: self.uuid(),
            title: "",
            date: Date.now,
            contents: "")
          ))
        return .none
        
      case .addNewJournal:
        guard case let .sheetToAdd(meta) = state.destination else {
          return .none
        }        
        
        state.journals.append(meta.journal)
        state.destination = nil
        return .none
      case .destination:
        return .none
      case .dismissAddingNewJournal:
        state.destination = nil
        return .none
      }
    }
    .ifLet(\.$destination, action: \.destination) {
      Destination()
    }
  }
  
  
}

extension JournalHome {
  @Reducer
  struct Destination {
    enum State: Equatable {
      case sheetToAdd(JournalMeta.State)
    }
    
    enum Action {
      case sheetToAdd(JournalMeta.Action)
    }
    
    var body: some ReducerOf<Self> {
      Scope(state: \.sheetToAdd, action: \.sheetToAdd) {
        JournalMeta()
      }
    }
  }
}
