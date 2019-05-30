
#-keep class io.ktor.server.netty.EngineMain { *; }
#-keep class kotlin.reflect.jvm.internal.** { *; }
#-keep class kotlin.text.RegexOption { *; }
#-keep class io.ktor.locations.** { *; }
#-keep class com.fantasticthing.github.AppKt { *; }
#-keep class com.fantasticthing.github.router.** {*;}
#-keep class com.fantasticthing.github.location.** {*;}
#
## Jackson
#-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
#-keep class com.fasterxml.** { *; }
#-keep class org.codehaus.** { *; }
#-keepnames class com.fasterxml.jackson.** { *; }
#-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
#    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
#}