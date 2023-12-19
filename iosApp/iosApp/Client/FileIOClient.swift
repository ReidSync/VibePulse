//
//  FileIOClient.swift
//  MyJournal
//
//  Created by Reid on 2023/11/28.
//

import Foundation
import Dependencies

struct FileIOClient: Sendable {
  var save: @Sendable (String, URL) async throws -> Void
  var load: @Sendable (URL) throws -> String
}

extension FileIOClient: DependencyKey {
  static let liveValue = Self(
    save: { dataString, url in
      //dataString.data(using: .utf8)
      let data = Data(dataString.utf8)
      try data.write(to: url)
    },
    load: { url in
      String(decoding: try Data(contentsOf: url), as: UTF8.self)
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
