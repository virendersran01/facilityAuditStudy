package edu.aku.hassannaqvi.fas.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import edu.aku.hassannaqvi.fas.core.CONSTANTS;
import edu.aku.hassannaqvi.fas.data.DAO.FormsDAO;
import edu.aku.hassannaqvi.fas.data.DAO.GetFncDAO;
import edu.aku.hassannaqvi.fas.data.entities.Clusters;
import edu.aku.hassannaqvi.fas.data.entities.Districts;
import edu.aku.hassannaqvi.fas.data.entities.Forms;
import edu.aku.hassannaqvi.fas.data.entities.HFA;
import edu.aku.hassannaqvi.fas.data.entities.UCs;
import edu.aku.hassannaqvi.fas.data.entities.Users;

@Database(entities = {Forms.class, Clusters.class, Users.class, Districts.class, UCs.class, HFA.class}, version = CONSTANTS.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /*@VisibleForTesting
    public static final String DATABASE_NAME = "wfppishincr.db";
    // Alter table for Database Update
    static final Migration MIGRATION_v2_v3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE forms "
                    + " ADD COLUMN last_update TEXT");
        }
    };*/

    private static AppDatabase sInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, CONSTANTS.DATABASE_NAME)
//                            .addMigrations(MIGRATION_v1_v2, MIGRATION_v2_v3)
                            .setJournalMode(JournalMode.TRUNCATE)
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract FormsDAO formsDao();

    public abstract GetFncDAO getFncDao();

}
