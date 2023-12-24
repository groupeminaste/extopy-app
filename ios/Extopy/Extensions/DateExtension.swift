//
//  DateExtension.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension Kotlinx_datetimeInstant {
    
    var timeAgo: String {
        let date = Date(timeIntervalSince1970: TimeInterval(epochSeconds))
        
        var calendar = Calendar.current
        calendar.locale = Locale(identifier: Bundle.main.preferredLocalizations[0])
                
        let formatter = DateComponentsFormatter()
        formatter.unitsStyle = .short
        formatter.maximumUnitCount = 1
        formatter.calendar = calendar
                
        var dateString: String?
                
        let interval = calendar.dateComponents([.year, .month, .weekOfYear, .day, .hour, .minute, .second], from: date, to: Date())
                
        if let year = interval.year, year > 0 {
            formatter.allowedUnits = [.year]
        } else if let month = interval.month, month > 0 {
            formatter.allowedUnits = [.month]
        } else if let week = interval.weekOfYear, week > 0 {
            formatter.allowedUnits = [.weekOfMonth]
        } else if let day = interval.day, day > 0 {
            formatter.allowedUnits = [.day]
        } else if let hour = interval.hour, hour > 0 {
            formatter.allowedUnits = [.hour]
        } else if let minute = interval.minute, minute > 0 {
            formatter.allowedUnits = [.minute]
        } else if let second = interval.second, second > 0 {
            formatter.allowedUnits = [.second]
        } else {
            let dateFormatter = DateFormatter()
            dateFormatter.locale = Locale(identifier: Bundle.main.preferredLocalizations[0])
            dateFormatter.dateStyle = .short
            dateFormatter.doesRelativeDateFormatting = true
                    
            dateString = dateFormatter.string(from: date)
        }
                
        if dateString == nil {
            dateString = formatter.string(from: date, to: Date())
        }
        
        return dateString!
    }
    
}
