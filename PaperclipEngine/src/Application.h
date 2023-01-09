#pragma once

#include "paperclippch.h"

namespace Paperclip {

    class Application {

    public:
        Application();
        Application(const std::string& applicaitonName);

        Application(const Application&) = delete;

        ~Application();

        void CreateDisplay();
        void StartApplication();

    private:
        std::string m_ApplicationName = "Paperclip Project";

    };

}