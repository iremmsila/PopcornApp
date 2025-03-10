import io.realm.Realm

object RealmManager {
    private val threadLocalRealm = ThreadLocal<Realm>()

    fun getRealmInstance(): Realm {
        var realm = threadLocalRealm.get()
        if (realm == null || realm.isClosed) {
            realm = Realm.getDefaultInstance()
            threadLocalRealm.set(realm)
        }
        return realm
    }

    fun closeRealm() {
        val realm = threadLocalRealm.get()
        if (realm != null && !realm.isClosed) {
            realm.close()
            threadLocalRealm.remove()
        }
    }
}
