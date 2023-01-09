#include "Application.h"

using namespace Paperclip;

Application::Application() {

}

Application::Application(const std::string& applicationName) {
    m_ApplicationName = applicationName;
}

Application::~Application() {

}

void Application::CreateDisplay() {

}

void Application::StartApplication() {

}

int main() {

    Application application = Application();
    application.CreateDisplay();

}