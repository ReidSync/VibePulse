//
//  JournalClient+live.swift
//  iosApp
//
//  Created by Reid on 2023/12/22.
//

import Foundation
import ComposableArchitecture

extension JournalClient {
  static var live: Self {
    return .init(
      editJournal: { journal, actions in
        return journal.edit(actions: actions)
      }
    )
  }
}
