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
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
        
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
