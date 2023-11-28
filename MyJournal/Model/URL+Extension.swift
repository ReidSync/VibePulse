//
//  URL+Extension.swift
//  MyJournal
//
//  Created by Reid on 2023/11/28.
//

import Foundation



extension URL {
  static let journalDataUrl = Self.documentsDirectory.appending(component: "my-journals.json")
}
