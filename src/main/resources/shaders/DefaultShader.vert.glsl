#version 400 core

layout (location = 0) in vec3 aVertexPosition;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 vColor;

void main() {

    vColor = vec4(1, 0, 1, 1);

    gl_Position = uProjection * uView * vec4(aVertexPosition, 1.0);

}