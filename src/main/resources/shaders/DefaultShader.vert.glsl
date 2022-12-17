#version 400 core

layout (location = 0) in vec3 aVertexPosition;
layout (location = 1) in vec4 aColor;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 vColor;

void main() {

    vColor = aColor;

    gl_Position = uProjection * uView * vec4(aVertexPosition, 1.0);

}