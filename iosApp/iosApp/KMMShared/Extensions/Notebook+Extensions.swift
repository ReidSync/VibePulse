//
//  Notebook+Extensions.swift
//  iosApp
//
//  Created by Reid on 2023/12/19.
//

import Foundation
import shared

public extension Notebook {
  static func deserialize(data: String) throws -> Notebook {
    //let re = shared.GraphSerializationKt.deserializeGraph(data: data)
    do {
      return try shared.JournalSerializationKt.deserializeNotebookOrThrow(data: data)
    }
    catch let error as NSError {
      print("NSError: \(error)")
      throw JournalSerializationException(message: error.localizedDescription)
    }
  }

}
