//
//  JournalClient.swift
//  iosApp
//
//  Created by Reid on 2023/12/22.
//

import Foundation
import Dependencies

struct JournalClient {
  var editJournal: @Sendable (Journal, @escaping (JournalEditingContext)->()) -> Journal
}

private enum JournalClientKey: DependencyKey {
  static let liveValue = JournalClient.live
  static let testValue = JournalClient.mock()
}

extension DependencyValues {
  var journalClient: JournalClient {
    get { self[JournalClientKey.self] }
    set { self[JournalClientKey.self] = newValue }
  }
}
