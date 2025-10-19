package org.example.project.DATA.DI

import org.example.project.DOMAIN.CommonViewModel
import org.koin.dsl.module

val commonModule = module {
    single { CommonViewModel() }
}