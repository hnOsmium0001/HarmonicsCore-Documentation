# Adding HarmonicsCore as a Dependency

Begin by adding Harmonics Core as a dependency for your mod. Note that this tutorial applies to the **Forge version** of HarmonicsCore.

Add the following into your `build.gradle`:
```groovy
repositories
    maven {
        name = "CurseForge"
        url = "https://minecraft.curseforge.com/api/maven/"
    }
}

dependencies {
    compile fg.deobf("harmonics-core:HarmonicsCore:${mc_version}:${harmonics_core_version}")
}
```

And add the variables into your `gradle.properties`
```properties
# Just some examples, change depending on your need
mc_version=1.14.4
harmonics_core_version=1.0.0.4
```

Since HarmonicsCore is a runtime library mod, we want to ensure it is included at runtime -- otherwise Minecraft will crash immediately due to it unable to find the classes
```toml
[[dependencies.modid]]
    modId="harmonics"
    mandatory=true
    # Just an example, change depending on your need
    versionRange="[1.0,)"
    ordering="NONE"
    side="BOTH"
```

Note that Harmonics Core is versioned in the following manner: MCVersion-Major.Minor.Feature.Builds.
Where `Major` indicates a breaking change, `Minor` indates some big API/feature addition such as changing the lifecycle of widgets (which will be introduced later),
`Feature` indicates big feature additions such as adding a new widget, and `Build` for regular bug fixes and small feature additions to existing code entities.
