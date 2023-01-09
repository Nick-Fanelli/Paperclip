workspace "PaperclipEngine"
    architecture "x86_64"
    startproject "PaperclipEngine"

    flags {
        "MultiProcessorCompile"
    }

    configurations {
        "Debug",
        "Release"
    }

outputdir = "%{cfg.buildcfg}-%{cfg.system}-%{cfg.architecture}"

include "PaperclipEngine/premake5.lua"