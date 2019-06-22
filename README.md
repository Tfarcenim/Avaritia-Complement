# AvaritiaTweaks
[![MCVersion](http://cf.way2muchnoise.eu/versions/Avaritia-Complement.svg)](https://minecraft.curseforge.com/projects/avaritia-complement)

[![GitHub issues](https://img.shields.io/github/issues/Tfarcenim/Avaritia-Complement.svg)](https://github.com/Tfarcenim/Avaritia-Complement/issues) [![GitHub pull requests](https://img.shields.io/github/issues-pr/Tfarcenim/Avaritia-Complement.svg)](https://github.com/Tfarcenim/Avaritia-Complement/pulls) [![license](https://img.shields.io/github/license/Tfarcenim/AvaritiaTweaks.svg)](../dev-1.12.2/LICENSE)

---

## About

This is a fork of the GitHub repo for the Avaritia Tweaks Minecraft mod, where the source code and issue tracker are in here as the old repo has been archived.

THis fork is also licensed under LGPL as the original mod is under LGPL.

Submit any bug reports / suggestions via [issue tracker](https://github.com/Tfarcenim/AvaritiaTweaks/issues).

[Pull requests](https://github.com/Tfarcenim/Avaritia-Complement/pulls) are welcome if you would like to add features / help with bug fixes or translations.

---

## Contact Me

- Discord DM - IItemstack#1381

---

## Link to original project 

https://github.com/JackyyTV/AvaritiaTweaks

## Setting up workspace / compile the mod yourself

If you would like to set up the workspace yourself to submit PRs of features additions or bug fixes, or compile the mod, here's how you do it.

1. Clone the mod.
    - HTTPS: `git clone https://github.com/Tfarcenim/AvaritiaTweaks.git`
    - SSH: `git clone git@github.com:Tfarcenim/AvaritiaTweaks.git`
    - Or, use the GitHub desktop app to clone the repo via GUI interface.

2. Setting up the workspace, depending on what you need.
    - Decompiled source: `gradlew setupDecompWorkspace`
    - Obfuscated source: `gradlew setupDevWorkspace`
    - CI server: `gradlew setupCIWorkspace`
    
3. Either use `gradlew build` to build the jar file (Output is in `build/libs`), or setup the IDE if you are going to modify any codes. Both IntelliJ IDEA and Eclipse are included below since they're more popular IDEs.
    - IntelliJ IDEA: Do `gradlew idea`, open the `.ipr` file and import the gradle file, then execute the `genIntellijRuns` task in the "Gradle" tab.
    - Eclipse: Do `gradlew eclipse` and open the directory as project.
