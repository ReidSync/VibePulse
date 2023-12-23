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
    
    init(destination: Destination.State? = nil) {
      self.destination = destination
      
      do {
        @Dependency(\.fileIOClient.load) var loadJournals
        let notebookString = try loadJournals(.journalDataUrl)
        let notebook = try Notebook.deserialize(data: notebookString)
        journals = IdentifiedArrayOf(uniqueElements: notebook.journals, id: \.id)
      }
      catch is DecodingError {
        NSLog("Failed to load data (decoding data failed)")
      }
      catch {
        NSLog("Failed to load data (Unknown)")
      }
    }
  }
  
  enum Action {
    case createButtonTapped
    case destination(PresentationAction<Destination.Action>)
    case addNewJournal
    case dismissAddingNewJournal
    case darkMode(Bool)
  }
  
  @Dependency(\.uuid) var uuid
  
  var body: some Reducer<State, Action> {
    Reduce { state, action in
      switch action {
      case .createButtonTapped:
        state.destination = .sheetToAdd(
          JournalMeta.State(
            journal: Journal.companion.makeInstance()
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
      case .darkMode(let on):
        if on == true {
          print("dark mode!!")
        }
        else {
          print("light mode!!")
        }
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

