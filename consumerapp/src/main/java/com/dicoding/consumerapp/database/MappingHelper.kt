package com.dicoding.consumerapp.database

import android.database.Cursor
import com.dicoding.consumerapp.model.UserModel

object MappingHelper {
    fun mapCursorToArrayList(dataCursor: Cursor?) : ArrayList<UserModel> {
        val favList = ArrayList<UserModel>()

        dataCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DBContract.Column.USERNAME))
                val idUser = getInt(getColumnIndexOrThrow(DBContract.Column.ID))
                val avatar = getString(getColumnIndexOrThrow(DBContract.Column.AVATAR_URL))
                favList.add(UserModel(username, idUser, avatar))
            }
        }
        return favList
    }
}