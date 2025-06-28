import SwiftUI
import shared

@main
struct ExtopyApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    init() {
        KoinApplication.start()

        #if !DEBUG
        SentryKt.initializeSentry()
        #endif
    }

    var body: some Scene {
        WindowGroup {
            //RootView()
            ContentView()
        }
    }

}

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        // Register for notifications
        UNUserNotificationCenter.current().delegate = self
        UNUserNotificationCenter.current().requestAuthorization(
            options: [.alert, .badge, .sound],
            completionHandler: { _, _ in
                DispatchQueue.main.async {
                    application.registerForRemoteNotifications()
                }
            }
        )

        // Register notification categories
        UNUserNotificationCenter.current().setNotificationCategories(
            [
                // TODO
                UNNotificationCategory(identifier: "followers", actions: [], intentIdentifiers: [], options: []),
            ]
        )

        return true
    }

    func application(
        _ application: UIApplication,
        open uri: URL,
        options: [UIApplication.OpenURLOptionsKey: Any] = [:]
    ) -> Bool {
        print("application open uri: \(uri)")
        // Sends the full URI on to the singleton
        ExternalUriHandler.shared.onNewUri(uri: uri.absoluteString)
        return true
    }

    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        // Create the token from data
        let _ = deviceToken.map { data in
            String(format: "%02.2hhx", data)
        }
        .joined()
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([.banner, .list, .badge, .sound])
    }

}
