package com.extopy.extensions

import kotlinx.cinterop.memScoped
import kotlinx.datetime.Instant
import platform.Foundation.*

actual val Instant.timeAgo: String
    get() = memScoped {
        val date = NSDate.dateWithTimeIntervalSince1970(secs = epochSeconds.toDouble())
        val calendar = NSCalendar.currentCalendar
        val localeId =
            (NSBundle.mainBundle.preferredLocalizations.firstOrNull() as? String)
                ?: NSLocale.currentLocale.localeIdentifier
        val locale = NSLocale(localeIdentifier = localeId)
        calendar.locale = locale

        val formatter = NSDateComponentsFormatter()
        formatter.unitsStyle = NSDateComponentsFormatterUnitsStyleShort
        formatter.maximumUnitCount = 1
        formatter.calendar = calendar

        val now = NSDate()
        val components = calendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitWeekOfYear or NSCalendarUnitDay or
                    NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond,
            fromDate = date,
            toDate = now,
            options = 0u
        )

        when {
            components.year > 0 -> formatter.allowedUnits = NSCalendarUnitYear
            components.month > 0 -> formatter.allowedUnits = NSCalendarUnitMonth
            components.weekOfYear > 0 -> formatter.allowedUnits = NSCalendarUnitWeekOfMonth
            components.day > 0 -> formatter.allowedUnits = NSCalendarUnitDay
            components.hour > 0 -> formatter.allowedUnits = NSCalendarUnitHour
            components.minute > 0 -> formatter.allowedUnits = NSCalendarUnitMinute
            components.second > 0 -> formatter.allowedUnits = NSCalendarUnitSecond
            else -> {
                val dateFormatter = NSDateFormatter()
                dateFormatter.locale = locale
                dateFormatter.dateStyle = NSDateFormatterShortStyle
                dateFormatter.doesRelativeDateFormatting = true
                return@memScoped dateFormatter.stringFromDate(date)
            }
        }

        return@memScoped formatter.stringFromDate(date, now) ?: ""
    }
