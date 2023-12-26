//
//  Journal+Extensions.swift
//  iosApp
//
//  Created by Reid on 2023/12/19.
//

import Foundation

extension Journal: Identifiable {}

extension Journal {
  func copy(
    id: UUID? = nil,
    title: String? = nil,
    //date: Date? = nil, // Kotlinx.datetimeLocalDateTime
    contents: String? = nil
  )-> Journal {    
    return doCopy(
      id: id ?? self.id,
      title: title ?? self.title,
      date: self.date,
      contents: contents ?? self.contents)
  }
}
