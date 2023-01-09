project "PaperclipEngine"
    kind "ConsoleApp"
    language "C++"
    cppdialect "C++17"

    targetdir ( "%{wks.location}/build/" .. outputdir .. "/%{prj.name}")
    objdir ( "%{wks.location}/build-int/" .. outputdir .. "/%{prj.name}")

    files {
        "src/**.h",
        "src/**.cpp"
    }

    filter "configurations:Debug"
		runtime "Debug"
		symbols "On"

        defines {
            "DEBUG"
        }

	filter "configurations:Release"
		runtime "Release"
        optimize "On"

        defines {
            "NDEBUG"
        }