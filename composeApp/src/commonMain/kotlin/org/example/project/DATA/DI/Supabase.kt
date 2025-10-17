package org.example.project.DATA.DI

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.example.project.SupabaseKey
import org.example.project.SupabaseUrl

object Supabase {
    val Client = createSupabaseClient(
        supabaseUrl = SupabaseUrl,
        supabaseKey = SupabaseKey
    ) {
        install(Auth)
        install(Postgrest)
        //defaultSerializer = KotlinxSerializer()
        //install other modules
    }
}