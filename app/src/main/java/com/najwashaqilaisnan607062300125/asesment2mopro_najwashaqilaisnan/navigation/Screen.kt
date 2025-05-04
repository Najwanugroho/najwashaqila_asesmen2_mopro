package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.navigation

import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.KEY_ID_CATATAN

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_CATATAN}"){
        fun withId(id:Long)="detailScreen/$id"
    }

}
