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
    var path = StackState<Path.State>()
    var journalHome = JournalHome.State()
  }
  
  enum Action {
    case path(StackAction<Path.State, Path.Action>)
    case journalHome(JournalHome.Action)
  }
  
  @Dependency(\.continuousClock) var clock
  @Dependency(\.fileIOClient.save) var saveJournals
  
  private enum CancelID {
    case saveDebounce
  }
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
      case let .path(.element(_, .editor(.delegate(delegateAction)))):
        switch delegateAction {
        case .journalUpdated(let journal):
          state.journalHome.journals[id: journal.id] = journal
          return .none
        }
        
      case .path:
        return .none
      case .journalHome:
        return .none
      }
    }
    .forEach(\.path, action: \.path) {
      Path()
    }
    
    Scope(state: \.journalHome, action: \.journalHome) {
      JournalHome()
    }
    
    Reduce { state, action in
      return .run { [journals = state.journalHome.journals] _ in
        try await withTaskCancellation(id: CancelID.saveDebounce, cancelInFlight: true) {
          try await self.clock.sleep(for: .seconds(1))
          print("save")
          try await self.saveJournals(JSONEncoder().encode(journals), .journalDataUrl)
        }
      }
      catch: { _, _ in }
    }
  }
}

extension AppFeature {
  @Reducer
  struct Path {
    enum State: Equatable {
      case editor(JournalEditor.State)
    }
    
    enum Action {
      case editor(JournalEditor.Action)
    }
    
    var body: some Reducer<State, Action> {
      Scope(state: \.editor, action: \.editor) {
        JournalEditor()
      }
    }
  }
}
