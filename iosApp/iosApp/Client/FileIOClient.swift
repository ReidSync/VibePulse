//
//  FileIOClient.swift
//  MyJournal
//
//  Created by Reid on 2023/11/28.
//

import Foundation
import Dependencies

struct FileIOClient: Sendable {
  var save: @Sendable (Data, URL) async throws -> Void
  var load: @Sendable (URL) throws -> Data
}

extension FileIOClient: DependencyKey {
  static let liveValue = Self(
    save: { data, url in
      try data.write(to: url)
    },
    load: { url in
      try Data(contentsOf: url)
    }
  )
  
  static let testValue = Self(
    save: unimplemented("FileIOClient.save"),
    load: unimplemented("FileIOClient.load")
  )
}

extension DependencyValues {
  var fileIOClient: FileIOClient {
    get { self[FileIOClient.self] }
    set { self[FileIOClient.self] = newValue }
  }
}
