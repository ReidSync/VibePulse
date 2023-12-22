//
//  JournalClient+mock.swift
//  iosApp
//
//  Created by Reid on 2023/12/22.
//

import Foundation
import ComposableArchitecture

extension JournalClient {
  public static func mock() -> Self {
    return .init(
      editJournal: { journal, actions in
        return journal.edit(actions: actions)
      }
    )
  }
}

