package com.artrivera.buildlogic.convention

import org.gradle.kotlin.dsl.DependencyHandlerScope

enum class DependencyType() {
    IMPLEMENTATION {
        override val tag: String
            get() = "implementation"
    },
    DEBUG_IMPLEMENTATION {
        override val tag: String
            get() = "debugImplementation"

    },
    ANDROID_TEST_IMPLEMENTATION {
        override val tag: String
            get() = "androidTestImplementation"
    },
    KSP {
        override val tag: String
            get() = "ksp"
    },
    TEST_IMPLEMENTATION {
        override val tag: String
            get() = "testImplementation"
    };

    abstract val tag: String
}

/**
 * Adds a dependency to the given configuration.
 * @param [DependencyType] of dependency to add
 * @param dependencyNotation name of dependency to add at specific configuration
 * @author Arturo Rivera
 */
internal fun DependencyHandlerScope.addDependency(type: DependencyType, dependencyNotation: Any) {
    add(type.tag, dependencyNotation)
}