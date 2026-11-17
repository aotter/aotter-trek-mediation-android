-keep class com.aotter.trek.gam.mediation.** {*;}

# The Google Mobile Ads SDK pulls in WorkManager 2.7.0 and Room 2.2.5, whose rules keep RoomDatabase
# subclasses but not their constructors. Under R8 full mode Room then fails to create WorkDatabase
# and the app crashes at startup. Room 2.7.0 (WorkManager 2.11.0 and later) ships this rule itself.
-keep class * extends androidx.room.RoomDatabase { <init>(); }
